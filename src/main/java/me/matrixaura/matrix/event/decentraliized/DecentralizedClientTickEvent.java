package me.matrixaura.matrix.event.decentraliized;

import me.matrixaura.matrix.core.event.decentralization.DecentralizedEvent;
import me.matrixaura.matrix.core.event.decentralization.EventData;

public class DecentralizedClientTickEvent extends DecentralizedEvent<EventData> {
    public static DecentralizedClientTickEvent instance = new DecentralizedClientTickEvent();
}
