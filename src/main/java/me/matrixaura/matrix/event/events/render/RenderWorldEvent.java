package me.matrixaura.matrix.event.events.render;

import me.matrixaura.matrix.core.event.decentralization.EventData;
import me.matrixaura.matrix.event.MatrixEvent;

public final class RenderWorldEvent extends MatrixEvent implements EventData {

    private final float partialTicks;
    private final Pass pass;

    public RenderWorldEvent(float partialTicks, int pass) {
        this.partialTicks = partialTicks;
        this.pass = Pass.values()[pass];
    }

    public final Pass getPass() {
        return this.pass;
    }

    public final float getPartialTicks() {
        return partialTicks;
    }

    public enum Pass {
        ANAGLYPH_CYAN, ANAGLYPH_RED, NORMAL
    }

}
