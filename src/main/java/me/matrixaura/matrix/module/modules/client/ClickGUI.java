package me.matrixaura.matrix.module.modules.client;

import me.matrixaura.matrix.client.ConfigManager;
import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.gui.MatrixClickGUI;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;
import org.lwjgl.input.Keyboard;

@Parallel
@ModuleInfo(name = "ClickGUI", category = Category.CLIENT, keyCode = Keyboard.KEY_O, description = "ClickGUI of Matrix")
public class ClickGUI extends Module {

    public static ClickGUI instance;

    public ClickGUI() {
        instance = this;
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            if (!(mc.currentScreen instanceof MatrixClickGUI)) {
                mc.displayGuiScreen(new MatrixClickGUI());
            }
        }
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen != null && mc.currentScreen instanceof MatrixClickGUI) {
            mc.displayGuiScreen(null);
        }
        ConfigManager.saveAll();
    }

}
