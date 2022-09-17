package me.matrixaura.matrix.core.config;

import me.matrixaura.matrix.core.concurrent.task.Task;
import me.matrixaura.matrix.core.event.decentralization.DecentralizedEvent;
import me.matrixaura.matrix.core.event.decentralization.EventData;
import me.matrixaura.matrix.core.event.decentralization.Listenable;
import me.matrixaura.matrix.core.event.decentralization.ListenableImpl;
import me.matrixaura.matrix.core.setting.Setting;

import java.util.concurrent.ConcurrentHashMap;

public class ListenableContainer extends ConfigContainer implements Listenable {

    private final ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap = new ConcurrentHashMap<>();

    @Override
    public ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap() {
        return listenerMap;
    }

    @Override
    public void subscribe() {
        listenerMap.forEach(DecentralizedEvent::register);
    }

    @Override
    public void unsubscribe() {
        listenerMap.forEach(DecentralizedEvent::unregister);
    }

    public <T extends EventData> void listener(Class<? extends DecentralizedEvent<T>> eventClass, Task<T> action) {
        ListenableImpl.listener(this, eventClass, action);
    }

}
