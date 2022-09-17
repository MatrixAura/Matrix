package me.matrixaura.matrix.module.modules.client;

import me.matrixaura.matrix.Matrix;
import me.matrixaura.matrix.client.FontManager;
import me.matrixaura.matrix.client.GUIManager;
import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.core.setting.Setting;
import me.matrixaura.matrix.event.events.render.RenderOverlayEvent;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;
import me.matrixaura.matrix.utils.ChatUtil;

import java.awt.*;

@Parallel
@ModuleInfo(name = "WaterMark", category = Category.CLIENT, description = "Display the Matrix watermark")
public class WaterMark extends Module {

    private final Setting<Integer> x = setting("X", 0, 0, 3840);
    private final Setting<Integer> y = setting("Y", 0, 0, 2160);

    @Override
    public void onRender(RenderOverlayEvent event) {
        int color = GUIManager.isRainbow() ? rainbow(1) : GUIManager.getColor3I();
        FontManager.draw(Matrix.MOD_NAME + " " + ChatUtil.SECTIONSIGN + "f" + Matrix.MOD_VERSION, x.getValue() + 1, y.getValue() + 3, color);
    }

    public int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 1.0f, 1.0f).getRGB();
    }

}
