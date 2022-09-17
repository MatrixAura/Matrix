package me.matrixaura.matrix.client;

import me.matrixaura.matrix.Matrix;
import me.matrixaura.matrix.core.event.Listener;
import me.matrixaura.matrix.core.event.Priority;
import me.matrixaura.matrix.core.event.decentralization.ListenableImpl;
import me.matrixaura.matrix.event.decentraliized.DecentralizedClientTickEvent;
import me.matrixaura.matrix.event.decentraliized.DecentralizedPacketEvent;
import me.matrixaura.matrix.event.decentraliized.DecentralizedRenderTickEvent;
import me.matrixaura.matrix.event.decentraliized.DecentralizedRenderWorldEvent;
import me.matrixaura.matrix.event.events.client.InputUpdateEvent;
import me.matrixaura.matrix.event.events.client.SettingUpdateEvent;
import me.matrixaura.matrix.event.events.network.PacketEvent;
import me.matrixaura.matrix.event.events.render.RenderOverlayEvent;
import me.matrixaura.matrix.event.events.render.RenderWorldEvent;
import me.matrixaura.matrix.module.Module;
import me.matrixaura.matrix.notification.NotificationManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static me.matrixaura.matrix.core.concurrent.ConcurrentTaskManager.runBlocking;

public class ModuleBus extends ListenableImpl {

    public ModuleBus() {
        Matrix.EVENT_BUS.register(this);
        listener(DecentralizedClientTickEvent.class, it -> onTick());
        listener(DecentralizedRenderTickEvent.class, this::onRenderTick);
        listener(DecentralizedRenderWorldEvent.class, this::onRenderWorld);
        listener(DecentralizedPacketEvent.Send.class, this::onPacketSend);
        listener(DecentralizedPacketEvent.Receive.class, this::onPacketReceive);
        subscribe();
    }

    private final List<Module> modules = new CopyOnWriteArrayList<>();

    public synchronized void register(Module module) {
        modules.add(module);
        Matrix.EVENT_BUS.register(module);
    }

    public void unregister(Module module) {
        modules.remove(module);
        Matrix.EVENT_BUS.unregister(module);
    }

    public boolean isRegistered(Module module) {
        return modules.contains(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    @Listener(priority = Priority.HIGHEST)
    public void onKey(InputUpdateEvent event) {
        modules.forEach(mod -> mod.onInputUpdate(event));
    }

    public void onTick() {
        runBlocking(it -> modules.forEach(module -> {
            if (module.parallelRunnable) {
                it.launch(() -> {
                    try {
                        module.onTick();
                    } catch (Exception exception) {
                        NotificationManager.fatal("Error while running Parallel Tick!");
                        exception.printStackTrace();
                    }
                });
            } else {
                try {
                    module.onTick();
                } catch (Exception exception) {
                    NotificationManager.fatal("Error while running Tick!");
                    exception.printStackTrace();
                }
            }
        }));
    }

    @Listener(priority = Priority.HIGHEST)
    public void onRenderTick(RenderOverlayEvent event) {
        runBlocking(it -> modules.forEach(module -> {
            try {
                module.onRender(event);
            } catch (Exception exception) {
                NotificationManager.fatal("Error while running onRender!");
                exception.printStackTrace();
            }
            if (module.parallelRunnable) {
                it.launch(() -> {
                    try {
                        module.onRenderTick();
                    } catch (Exception exception) {
                        NotificationManager.fatal("Error while running Parallel Render Tick!");
                        exception.printStackTrace();
                    }
                });
            } else {
                try {
                    module.onRenderTick();
                } catch (Exception exception) {
                    NotificationManager.fatal("Error while running Render Tick!");
                    exception.printStackTrace();
                }
            }
        }));
    }

    @Listener(priority = Priority.HIGHEST)
    public void onRenderWorld(RenderWorldEvent event) {
        WorldRenderPatcher.INSTANCE.patch(event);
    }

    @Listener(priority = Priority.HIGHEST)
    public void onPacketSend(PacketEvent.Send event) {
        modules.forEach(module -> {
            try {
                module.onPacketSend(event);
            } catch (Exception exception) {
                NotificationManager.fatal("Error while running PacketSend!");
                exception.printStackTrace();
            }
        });
    }

    @Listener(priority = Priority.HIGHEST)
    public void onPacketReceive(PacketEvent.Receive event) {
        modules.forEach(module -> {
            try {
                module.onPacketReceive(event);
            } catch (Exception exception) {
                NotificationManager.fatal("Error while running PacketReceive!");
                exception.printStackTrace();
            }
        });
    }

    @Listener(priority = Priority.HIGHEST)
    public void onSettingChange(SettingUpdateEvent event) {
        modules.forEach(it -> {
            try {
                it.onSettingChange(event.getSetting());
            } catch (Exception exception) {
                NotificationManager.fatal("Error while running onSettingChange!");
                exception.printStackTrace();
            }
        });
    }

}
