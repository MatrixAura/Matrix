package me.matrixaura.matrix.event.decentraliized;

import me.matrixaura.matrix.core.event.decentralization.DecentralizedEvent;
import me.matrixaura.matrix.event.events.render.RenderOverlayEvent;

public class DecentralizedRenderTickEvent extends DecentralizedEvent<RenderOverlayEvent> {
    public static DecentralizedRenderTickEvent instance = new DecentralizedRenderTickEvent();
}
