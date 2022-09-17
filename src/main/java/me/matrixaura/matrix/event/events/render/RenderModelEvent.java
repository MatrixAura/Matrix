package me.matrixaura.matrix.event.events.render;

import me.matrixaura.matrix.event.MatrixEvent;

public class RenderModelEvent extends MatrixEvent {
    public boolean rotating = false;
    public float pitch = 0;

    public RenderModelEvent(){
        super();
    }
}
