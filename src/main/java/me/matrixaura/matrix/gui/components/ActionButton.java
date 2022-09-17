package me.matrixaura.matrix.gui.components;

import me.matrixaura.matrix.client.GUIManager;
import me.matrixaura.matrix.core.concurrent.task.VoidTask;
import me.matrixaura.matrix.core.setting.Setting;
import me.matrixaura.matrix.gui.Component;
import me.matrixaura.matrix.gui.Panel;
import me.matrixaura.matrix.utils.graphics.RenderUtils2D;

public class ActionButton extends Component {

    Setting<VoidTask> setting;

    public ActionButton(Setting<VoidTask> setting, int width, int height, Panel father) {
        this.setting = setting;
        this.width = width;
        this.height = height;
        this.father = father;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtils2D.drawRect(x, y, x + width, y + height, 0x85000000);
        font.drawString(setting.getName(), x + 5, (int) (y + height / 2 - font.getHeight() / 2f) + 2, getHoveredColor(mouseX, mouseY, GUIManager.getColor3I()));
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY))
            return false;

        if (mouseButton == 0) {
            setting.getValue().invoke();
        }
        return true;

    }

    @Override
    public String getDescription() {
        return setting.getDescription();
    }

}
