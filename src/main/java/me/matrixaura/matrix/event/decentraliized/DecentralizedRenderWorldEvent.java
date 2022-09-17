package me.matrixaura.matrix.event.decentraliized;

import me.matrixaura.matrix.core.event.decentralization.DecentralizedEvent;
import me.matrixaura.matrix.event.events.render.RenderWorldEvent;

public class DecentralizedRenderWorldEvent extends DecentralizedEvent<RenderWorldEvent> {
    public static DecentralizedRenderWorldEvent instance = new DecentralizedRenderWorldEvent();
}
