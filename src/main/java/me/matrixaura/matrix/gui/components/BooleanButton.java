package me.matrixaura.matrix.gui.components;

import me.matrixaura.matrix.client.GUIManager;
import me.matrixaura.matrix.core.setting.Setting;
import me.matrixaura.matrix.gui.Component;
import me.matrixaura.matrix.gui.Panel;
import me.matrixaura.matrix.utils.SoundUtil;
import me.matrixaura.matrix.utils.graphics.RenderUtils2D;

public class BooleanButton extends Component {

    Setting<Boolean> setting;

    public BooleanButton(Setting<Boolean> setting, int width, int height, Panel father) {
        this.width = width;
        this.height = height;
        this.father = father;
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtils2D.drawRect(x, y, x + width, y + height, 0x85000000);
        font.drawString(setting.getName(), x + 5, (int) (y + height / 2 - font.getHeight() / 2f) + 2, getHoveredColor(mouseX, mouseY, setting.getValue() ? GUIManager.getColor3I() : fontColor));

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!setting.isVisible() || !isHovered(mouseX, mouseY))
            return false;
        if (mouseButton == 0) {
            this.setting.setValue(!setting.getValue());
            SoundUtil.playButtonClick();
        }
        return true;
    }

    @Override
    public boolean isVisible() {
        return setting.isVisible();
    }

    @Override
    public String getDescription() {
        return setting.getDescription();
    }

}
