package me.matrixaura.matrix.core.concurrent.task;

import me.matrixaura.matrix.core.concurrent.utils.Syncer;

abstract class Syncable implements Runnable {
    protected Syncer syncer;
}
