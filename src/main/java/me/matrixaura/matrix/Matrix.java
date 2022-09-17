package me.matrixaura.matrix;

import me.matrixaura.matrix.client.*;
import me.matrixaura.matrix.core.event.EventManager;
import me.matrixaura.matrix.core.event.Listener;
import me.matrixaura.matrix.core.event.Priority;
import me.matrixaura.matrix.event.events.client.InitializationEvent;
import me.matrixaura.matrix.module.modules.client.ClickGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import static me.matrixaura.matrix.core.concurrent.ConcurrentTaskManager.runBlocking;
import static me.matrixaura.matrix.core.concurrent.ConcurrentTaskManager.runTiming;

/**
 * Author B_312
 * Since 05/01/2021
 * Last update on 09/21/2021
 */
public class Matrix {

    public static final String MOD_NAME = "Matrix";
    public static final String MOD_VERSION = "220917";
    public static final String MOD_VERSION_TYPE = "Dev";

    public static final String AUTHOR = "B_312 & MatrixAura";
    public static final String GITHUB = "https://github.com/MatrixAura/Matrix";

    public static String CHAT_SUFFIX = "\u1d0d\u1d00\u1d1b\u0280\u026ax";

    public static final Logger log = LogManager.getLogger(MOD_NAME);
    private static Thread mainThread;

    @Listener(priority = Priority.HIGHEST)
    public void preInitialize(InitializationEvent.PreInitialize event) {
        mainThread = Thread.currentThread();
    }

    @Listener(priority = Priority.HIGHEST)
    public void initialize(InitializationEvent.Initialize event) {
        long tookTime = runTiming(() -> {
            Display.setTitle(MOD_NAME + " " + MOD_VERSION);

            //Parallel load managers
            runBlocking(it -> {
                Matrix.log.info("Loading Font Manager");
                FontManager.init();

                Matrix.log.info("Loading Module Manager");
                ModuleManager.init();

                Matrix.log.info("Loading GUI Manager");
                it.launch(GUIManager::init);

                Matrix.log.info("Loading Command Manager");
                it.launch(CommandManager::init);

                Matrix.log.info("Loading Friend Manager");
                it.launch(FriendManager::init);

                Matrix.log.info("Loading Config Manager");
                it.launch(ConfigManager::init);
            });
        });

        log.info("Took " + tookTime + "ms to launch Matrix!");
    }

    @Listener(priority = Priority.HIGHEST)
    public void postInitialize(InitializationEvent.PostInitialize event) {
        ClickGUI.instance.disable();
    }

    public static boolean isMainThread(Thread thread) {
        return thread == mainThread;
    }

    public static EventManager EVENT_BUS = new EventManager();
    public static ModuleBus MODULE_BUS = new ModuleBus();

    public static final Matrix instance = new Matrix();

}