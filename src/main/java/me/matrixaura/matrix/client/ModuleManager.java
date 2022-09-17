package me.matrixaura.matrix.client;

import me.matrixaura.matrix.module.modules.combat.*;
import net.minecraft.client.Minecraft;
import me.matrixaura.matrix.Matrix;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.core.event.Listener;
import me.matrixaura.matrix.core.event.Priority;
import me.matrixaura.matrix.event.events.client.KeyEvent;
import me.matrixaura.matrix.event.events.render.RenderOverlayEvent;
import me.matrixaura.matrix.gui.MatrixHUDEditor;
import me.matrixaura.matrix.gui.renderers.HUDEditorRenderer;
import me.matrixaura.matrix.hud.HUDModule;
import me.matrixaura.matrix.hud.huds.CombatInfo;
import me.matrixaura.matrix.hud.huds.Welcomer;
import me.matrixaura.matrix.module.Module;
import me.matrixaura.matrix.module.modules.client.*;
import me.matrixaura.matrix.module.modules.misc.CustomChat;
import me.matrixaura.matrix.module.modules.misc.SkinFlicker;
import me.matrixaura.matrix.module.modules.misc.Spammer;
import me.matrixaura.matrix.module.modules.movement.Sprint;
import me.matrixaura.matrix.module.modules.movement.Velocity;
import me.matrixaura.matrix.module.modules.player.AntiContainer;
import me.matrixaura.matrix.module.modules.player.AutoJump;
import me.matrixaura.matrix.module.modules.player.FakePlayer;
import me.matrixaura.matrix.module.modules.render.AntiOverlay;
import me.matrixaura.matrix.module.modules.render.Brightness;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static me.matrixaura.matrix.core.concurrent.ConcurrentTaskManager.runBlocking;

public class ModuleManager {

    public final Map<Class<? extends Module>, Module> moduleMap = new ConcurrentHashMap<>();
    public final List<Module> moduleList = new ArrayList<>();
    private final Set<Class<? extends Module>> classes = new HashSet<>();

    private static ModuleManager instance;

    public static List<Module> getModules() {
        return getInstance().moduleList;
    }

    public static void init() {
        if (instance == null) instance = new ModuleManager();
        instance.moduleMap.clear();

        //Client
        registerNewModule(ActiveModuleList.class);
        registerNewModule(ClickGUI.class);
        registerNewModule(GUISetting.class);
        registerNewModule(HUDEditor.class);
        registerNewModule(WaterMark.class);

        //Combat
        registerNewModule(Anti32kTotem.class);
        registerNewModule(AutoTotem.class);
        registerNewModule(MatrixAura.class);
        registerNewModule(MatrixCrystal.class);
        registerNewModule(OffHandCrystal.class);

        //Misc
        registerNewModule(CustomChat.class);
        registerNewModule(SkinFlicker.class);
        registerNewModule(Spammer.class);

        //Movement
        registerNewModule(Sprint.class);
        registerNewModule(Velocity.class);

        //Player
        registerNewModule(AntiContainer.class);
        registerNewModule(AutoJump.class);
        registerNewModule(FakePlayer.class);

        //Render
        registerNewModule(AntiOverlay.class);
        registerNewModule(Brightness.class);

        //HUD
        registerNewModule(CombatInfo.class);
        registerNewModule(Welcomer.class);

        instance.loadModules();
        Matrix.EVENT_BUS.register(instance);
    }

    public static void registerNewModule(Class<? extends Module> clazz) {
        instance.classes.add(clazz);
    }

    @Listener(priority = Priority.HIGHEST)
    public void onKey(KeyEvent event) {
        moduleList.forEach(it -> it.keyBinds.forEach(binds -> binds.test(event.getKey())));
    }

    @Listener(priority = Priority.HIGHEST)
    public void onRenderHUD(RenderOverlayEvent event) {
        for (int i = HUDEditorRenderer.instance.hudModules.size() - 1; i >= 0; i--) {
            HUDModule hudModule = HUDEditorRenderer.instance.hudModules.get(i);

            if (!(Minecraft.getMinecraft().currentScreen instanceof MatrixHUDEditor) && hudModule.isEnabled())
                hudModule.onHUDRender(event.getScaledResolution());
        }
    }

    public static ModuleManager getInstance() {
        if (instance == null) instance = new ModuleManager();
        return instance;
    }

    public static Module getModule(Class<? extends Module> clazz) {
        return getInstance().moduleMap.get(clazz);
    }

    public static Module getModuleByName(String targetName) {
        for (Module module : getModules()) {
            if (module.name.equalsIgnoreCase(targetName)) {
                return module;
            }
        }
        Matrix.log.info("Module " + targetName + " is not exist.Please check twice!");
        return null;
    }

    private void loadModules() {
        Matrix.log.info("[ModuleManager]Loading modules.");
        runBlocking(unit -> classes.stream().sorted(Comparator.comparing(Class::getSimpleName)).forEach(clazz -> {
            if (clazz != HUDModule.class) {
                try {
                    if (clazz.isAnnotationPresent(Parallel.class) && clazz.getAnnotation(Parallel.class).loadable()) {
                        unit.launch(() -> {
                            try {
                                add(clazz.newInstance(), clazz);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.err.println("Couldn't initiate Module " + clazz.getSimpleName() + "! Error: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
                            }
                        });
                    } else {
                        add(clazz.newInstance(), clazz);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Couldn't initiate Module " + clazz.getSimpleName() + "! Error: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
                }
            }
        }));
        sort();
        Matrix.log.info("[ModuleManager]Loaded " + moduleList.size() + " modules");
    }

    private synchronized void add(Module module, Class<? extends Module> clazz) {
        moduleList.add(module);
        moduleMap.put(clazz, module);
    }

    private void sort() {
        moduleList.sort(Comparator.comparing(it -> it.name));
    }


}
