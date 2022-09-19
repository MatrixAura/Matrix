package me.matrixaura.matrix.module.modules.combat;

import me.matrixaura.matrix.client.FriendManager;
import me.matrixaura.matrix.client.GUIManager;
import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.core.setting.Setting;
import me.matrixaura.matrix.event.events.network.PacketEvent;
import me.matrixaura.matrix.event.events.render.RenderEvent;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;
import me.matrixaura.matrix.utils.BlockInteractionHelper;
import me.matrixaura.matrix.utils.CrystalUtil;
import me.matrixaura.matrix.utils.GeometryMasks;
import me.matrixaura.matrix.utils.Timer;
import me.matrixaura.matrix.utils.graphics.RenderUtils3D;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static me.matrixaura.matrix.utils.CrystalUtil.calculateDamage;
import static org.lwjgl.opengl.GL11.GL_QUADS;


@Parallel(runnable = true)
@ModuleInfo(name = "MatrixCrystal", category = Category.COMBAT)
public class MatrixCrystal extends Module {

    Setting<Page> nowPage = setting("Page", Page.General);

    //  General
    Setting<Boolean> lethalTry = setting("LethalTry", true).whenAtMode(nowPage, Page.General);
    Setting<Double> distance = setting("CalcRange", 7.0D, 0.0D, 16.0D).whenAtMode(nowPage, Page.General);
    Setting<Double> placeRange = setting("Range", 4.5D, 0.0D, 8.0D).whenAtMode(nowPage, Page.General);
    Setting<Double> stopHealth = setting("StopHealth", 5.0D, 0.0D, 20.0D).whenAtMode(nowPage, Page.General);

    //  Interact
    Setting<Boolean> ghostHand = setting("GhostHand", true).whenAtMode(nowPage, Page.General);
    Setting<Boolean> autoSwitch = setting("AutoSwitch", false).whenAtMode(nowPage, Page.Interact);
    Setting<Boolean> ver113Place = setting("1.13Place", false).whenAtMode(nowPage, Page.Interact);
    Setting<Integer> placeDelay = setting("PlaceDelay", 50, 0, 1000).whenAtMode(nowPage, Page.Interact);
    Setting<Integer> explodeDelay = setting("ExplodeDelay", 35, 0, 1000).whenAtMode(nowPage, Page.Interact);

    //  Render
    Setting<MatrixAura.RenderMode> renderMode = setting("RenderBlock", MatrixAura.RenderMode.Full).whenAtMode(nowPage, Page.Render);
    Setting<Boolean> syncGUI = setting("SyncGui", false).des("Synchronize color with GUI").whenAtMode(nowPage, Page.Render);
    Setting<Integer> red = setting("Red", 255, 0, 255).whenFalse(syncGUI).whenAtMode(nowPage, Page.Render);
    Setting<Integer> green = setting("Green", 0, 0, 255).whenFalse(syncGUI).whenAtMode(nowPage, Page.Render);
    Setting<Integer> blue = setting("Blue", 0, 0, 255).whenFalse(syncGUI).whenAtMode(nowPage, Page.Render);
    Setting<Integer> transparency = setting("Alpha", 26, 0, 255).whenAtMode(nowPage, Page.Render);
    Setting<Boolean> rainbow = setting("Rainbow", false).whenFalse(syncGUI).whenAtMode(nowPage, Page.Render);
    Setting<Float> rainbowSpeed = setting("RGB Speed", 1.0f, 0.0f, 10.0f).whenFalse(syncGUI).whenAtMode(nowPage, Page.Render);
    Setting<Float> saturation = setting("Saturation", 0.65f, 0.0f, 1.0f).whenFalse(syncGUI).whenAtMode(nowPage, Page.Render);
    Setting<Float> brightness = setting("Brightness", 1.0f, 0.0f, 1.0f).whenFalse(syncGUI).whenAtMode(nowPage, Page.Render);


    Timer placeTimer = new Timer();
    Timer breakTimer = new Timer();
    BlockPos customBlockPos;

    @Override
    public void onRenderWorld(RenderEvent event) {
        int color = syncGUI.getValue() ?
                new Color(GUIManager.getRed(), GUIManager.getGreen(), GUIManager.getBlue(), transparency.getValue()).getRGB() :
                getColor();
        if (customBlockPos != null) drawBlock(customBlockPos, color);
    }

    public int getColor() {
        float[] tick_color = {(System.currentTimeMillis() % 11520L) / 11520.0f * rainbowSpeed.getValue()};
        int color_rgb = Color.HSBtoRGB(tick_color[0], saturation.getValue(), brightness.getValue());
        return rainbow.getValue() ?
                new Color(color_rgb >> 16 & 0xFF, color_rgb >> 8 & 0xFF, color_rgb & 0xFF, transparency.getValue()).getRGB() :
                new Color(red.getValue(), green.getValue(), blue.getValue(), transparency.getValue()).getRGB();
    }

    public void onTick() {
        if (mc.world != null && mc.player != null) {
            if (placeTimer.passed(placeDelay.getValue())) {
                placeTimer.reset();
                customBlockPos = getPlaceTargetPos();
                placeCrystal(customBlockPos);
            }

            if (breakTimer.passed(explodeDelay.getValue()) && customBlockPos != null) {
                EntityEnderCrystal crystal = getHittableCrystal(customBlockPos);
                breakTimer.reset();
                if (crystal != null) {
                    this.explodeCrystal(crystal);
                }
            }

        }
    }

    private void drawBlock(BlockPos blockPos, int color) {
        RenderUtils3D.prepare(GL_QUADS);
        if (!renderMode.getValue().equals(MatrixAura.RenderMode.OFF)) {
            if (renderMode.getValue().equals(MatrixAura.RenderMode.Solid) || renderMode.getValue().equals(MatrixAura.RenderMode.Up)) {
                if (renderMode.getValue().equals(MatrixAura.RenderMode.Up)) {
                    RenderUtils3D.drawBox(blockPos, color, GeometryMasks.Quad.UP);
                } else {
                    RenderUtils3D.drawBox(blockPos, color, GeometryMasks.Quad.ALL);
                }
            } else {
                if (renderMode.getValue().equals(MatrixAura.RenderMode.Full)) {
                    RenderUtils3D.drawFullBox(blockPos, 1f, color);
                } else if (renderMode.getValue().equals(MatrixAura.RenderMode.Outline)) {
                    RenderUtils3D.drawBoundingBox(blockPos, 2f, color);
                } else {
                    RenderUtils3D.drawBoundingBox(blockPos.add(0, 1, 0), 2f, color);
                }
            }
        }
        RenderUtils3D.release();
    }

    public void onEnable() {
        this.placeTimer.reset();
        this.breakTimer.reset();
    }

    @Override
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.packet instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.packet;
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal) {
                        if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                            e.setDead();
                        }
                    }
                }
            }
        }
    }

    public EntityEnderCrystal getHittableCrystal(BlockPos blockPos) {
        try {
            return mc.world.loadedEntityList.stream()
                    .filter(it -> !it.isEntityAlive())
                    .filter(it -> it instanceof EntityEnderCrystal)
                    .filter(it -> mc.player.getDistance(it) <= placeRange.getValue())
                    .filter(it -> it.posX == blockPos.getX() + 0.5D)
                    .filter(it -> it.posY == blockPos.getY() + 1.0D)
                    .filter(it -> it.posZ == blockPos.getZ() + 0.5D)
                    .map(e -> (EntityEnderCrystal) e)
                    .min(Comparator.comparing(e -> e.posX))
                    .orElse(null);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void explodeCrystal(EntityEnderCrystal crystal) {
        mc.playerController.attackEntity(mc.player, crystal);
        mc.player.swingArm(mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        mc.player.resetCooldown();
    }

    public BlockPos getPlaceTargetPos() {
        BlockPos targetPos;
        List<BlockPos> blockPoses = findCrystalBlocks();
        List<EntityPlayer> players = mc.world.playerEntities.stream()
                .filter(e -> mc.player.getDistance(e) <= distance.getValue())
                .filter(e -> mc.player != e)
                .filter(e -> !FriendManager.isFriend(e))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());
        targetPos = calculateTargetPlace(blockPoses, players);
        return targetPos;
    }

    public BlockPos calculateTargetPlace(List<BlockPos> crystalBlocks, List<EntityPlayer> players) {
        double minDamage = 256.0D;
        double maxDamage = 0.0D;
        BlockPos targetBlock = null;

        for (EntityLivingBase it : players) {
            if (it != mc.player && it.isEntityAlive()) {
                for (BlockPos blockPos : crystalBlocks) {
                    if (it.getDistanceSq(blockPos) >= distance.getValue() * distance.getValue()) continue;
                    if (mc.player.getDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ()) > placeRange.getValue())
                        continue;
                    double targetDamage = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, it);
                    double selfDamage = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, mc.player);

                    float healthTarget = it.getHealth() + it.getAbsorptionAmount();
                    float healthSelf = mc.player.getHealth() + mc.player.getAbsorptionAmount();

                    if (healthSelf - selfDamage <= stopHealth.getValue()) {
                        continue;
                    }
                    if (lethalTry.getValue() && healthTarget - targetDamage <= 0.0F && selfDamage < minDamage) {
                        targetBlock = blockPos;
                        maxDamage = 256.0D;
                        minDamage = selfDamage;
                    }
                    if (selfDamage >= targetDamage) {
                        continue;
                    }
                    if (targetDamage - selfDamage > maxDamage) {
                        maxDamage = targetDamage - selfDamage;
                        targetBlock = blockPos;
                    }
                }
                if (targetBlock != null) break;
            }
        }
        return targetBlock;
    }

    public boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN)
            return false;
        if (mc.world.getBlockState(boost).getBlock() != Blocks.AIR)
            return false;

        if (!ver113Place.getValue() && mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) return false;
        return mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    private List<BlockPos> findCrystalBlocks() {
        double range = distance.getValue();
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(BlockInteractionHelper.getSphere(CrystalUtil.getPlayerPos(), (float) range, (int) range, false, true, 0)
                .stream()
                .filter(this::canPlaceCrystal)
                .sorted(Comparator.comparing(it -> mc.player.getDistance(it.getX(), it.getY(), it.getZ())))
                .collect(Collectors.toList()));
        return positions;
    }

    public void placeCrystal(BlockPos blockPos) {
        boolean isOffhand = mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
        EnumHand enumHand = isOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        if (autoSwitch.getValue() && !isOffhand && mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
            int crystalSlot = -1;
            for (int l = 0; l < 9; ++l) {
                if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
            if (crystalSlot != -1) mc.player.inventory.currentItem = crystalSlot;
            return;
        }
        if (isOffhand || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos, EnumFacing.UP, enumHand, 0, 0, 0));
    }

    private enum Page {General, Interact, Render}

}
