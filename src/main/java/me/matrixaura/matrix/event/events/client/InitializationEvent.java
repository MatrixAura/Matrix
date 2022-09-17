package me.matrixaura.matrix.event.events.client;

import me.matrixaura.matrix.event.MatrixEvent;

/**
 * We use this to launch our client
 */
public class InitializationEvent extends MatrixEvent {
    public static class PreInitialize extends InitializationEvent{
    }

    public static class Initialize extends InitializationEvent{
    }

    public static class PostInitialize extends InitializationEvent{
    }
}
