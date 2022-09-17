package me.matrixaura.matrix.hud.huds;

import me.matrixaura.matrix.client.FontManager;
import me.matrixaura.matrix.client.GUIManager;
import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.engine.AsyncRenderer;
import me.matrixaura.matrix.hud.HUDModule;
import me.matrixaura.matrix.module.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@ModuleInfo(name = "Welcomer", category = Category.HUD)
public class Welcomer extends HUDModule {

    public Welcomer() {
        asyncRenderer = new AsyncRenderer() {
            @Override
            public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY) {
                String text = "Welcome " + Minecraft.getMinecraft().player.getName() + "!Have a nice day :)";
                drawAsyncString(text, x, y, GUIManager.getColor3I());
                width = FontManager.getWidth(text);
                height = FontManager.getHeight();
            }
        };
    }

    @Override
    public void onHUDRender(ScaledResolution resolution) {
        asyncRenderer.onRender();
    }

}
