package me.matrixaura.matrix.module.modules.movement;

import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;

@Parallel(runnable = true)
@ModuleInfo(name = "Sprint", category = Category.MOVEMENT, description = "Automatically sprint")
public class Sprint extends Module {

    @Override
    public void onRenderTick() {
        if (mc.player == null) return;
        mc.player.setSprinting(!mc.player.collidedHorizontally && mc.player.moveForward > 0);
    }

}
