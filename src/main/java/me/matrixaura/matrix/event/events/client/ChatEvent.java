package me.matrixaura.matrix.event.events.client;

import me.matrixaura.matrix.event.MatrixEvent;

public class ChatEvent extends MatrixEvent {

    protected String message;

    public ChatEvent(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }

}