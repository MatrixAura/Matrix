package me.matrixaura.matrix.module.modules.player;

import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;

@Parallel(runnable = true)
@ModuleInfo(name = "AutoJump", category = Category.PLAYER, description = "Automatically jump")
public class AutoJump extends Module {

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.isInWater() || mc.player.isInLava()) mc.player.motionY = 0.1;
        else if (mc.player.onGround) mc.player.jump();
    }

}
