package me.matrixaura.matrix.module.modules.client;

import me.matrixaura.matrix.client.ConfigManager;
import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.gui.MatrixHUDEditor;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;
import org.lwjgl.input.Keyboard;

@Parallel
@ModuleInfo(name = "HUDEditor", category = Category.CLIENT, keyCode = Keyboard.KEY_GRAVE, description = "HUDEditor of Matrix")
public class HUDEditor extends Module {

    public static HUDEditor instance;

    public HUDEditor() {
        instance = this;
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            if (!(mc.currentScreen instanceof MatrixHUDEditor)) {
                mc.displayGuiScreen(new MatrixHUDEditor());
            }
        }
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen != null && mc.currentScreen instanceof MatrixHUDEditor) {
            mc.displayGuiScreen(null);
        }
        ConfigManager.saveAll();
    }

}
