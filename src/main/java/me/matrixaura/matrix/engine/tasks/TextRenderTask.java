package me.matrixaura.matrix.engine.tasks;

import me.matrixaura.matrix.client.FontManager;
import me.matrixaura.matrix.engine.RenderTask;
import me.matrixaura.matrix.utils.graphics.font.CFontRenderer;

public class TextRenderTask implements RenderTask {

    String text;
    float x, y;
    int color;
    boolean centered, shadow;
    CFontRenderer fontRenderer;

    public TextRenderTask(String text, float x, float y, int color, boolean centered, boolean shadow) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.centered = centered;
        this.shadow = shadow;
        this.fontRenderer = FontManager.fontRenderer;
    }

    public TextRenderTask(String text, float x, float y, int color, boolean centered, boolean shadow, CFontRenderer fontRenderer) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.centered = centered;
        this.shadow = shadow;
        this.fontRenderer = fontRenderer;
    }

    @Override
    public void onRender() {
        if (shadow) {
            if (centered) fontRenderer.drawCenteredStringWithShadow(text, x, y, color);
            else fontRenderer.drawStringWithShadow(text, x, y, color);
        } else {
            if (centered) fontRenderer.drawCenteredString(text, x, y, color);
            else fontRenderer.drawString(text, x, y, color);
        }
    }

}
