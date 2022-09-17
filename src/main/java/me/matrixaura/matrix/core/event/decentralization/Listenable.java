package me.matrixaura.matrix.core.event.decentralization;

import me.matrixaura.matrix.core.concurrent.task.Task;

import java.util.concurrent.ConcurrentHashMap;

public interface Listenable {

    ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap();

    void subscribe();

    void unsubscribe();

}
