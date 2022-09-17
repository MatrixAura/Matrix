package me.matrixaura.matrix.event.events.client;

import me.matrixaura.matrix.event.MatrixEvent;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenEvent extends MatrixEvent {

    private final GuiScreen screen;

    public GuiScreenEvent(GuiScreen screen) {
        this.screen = screen;
    }

    public GuiScreen getScreen() {
        return screen;
    }

    public static class Displayed extends GuiScreenEvent {
        public Displayed(GuiScreen screen) {
            super(screen);
        }
    }

    public static class Closed extends GuiScreenEvent {
        public Closed(GuiScreen screen) {
            super(screen);
        }
    }

}
