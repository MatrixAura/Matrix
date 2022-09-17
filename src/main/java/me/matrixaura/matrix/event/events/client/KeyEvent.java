package me.matrixaura.matrix.event.events.client;

import me.matrixaura.matrix.event.MatrixEvent;

public final class KeyEvent extends MatrixEvent {

    private final int key;
    private final char character;

    public KeyEvent(int key, char character) {
        this.key = key;
        this.character = character;
    }

    public final int getKey() {
        return this.key;
    }

    public final char getCharacter() {
        return this.character;
    }

}