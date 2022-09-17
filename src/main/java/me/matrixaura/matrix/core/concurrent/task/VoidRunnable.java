package me.matrixaura.matrix.core.concurrent.task;

import me.matrixaura.matrix.core.concurrent.utils.Syncer;

/**
 * Created by B_312 on 05/01/2021
 */
public class VoidRunnable extends Syncable {

    private final VoidTask task;
    private final Syncer syncer;

    public VoidRunnable(VoidTask task) {
        this.task = task;
        this.syncer = null;
    }

    public VoidRunnable(VoidTask task, Syncer syncer) {
        this.task = task;
        this.syncer = syncer;
    }

    @Override
    public void run() {
        try {
            task.invoke();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (syncer != null) syncer.countDown();
    }

}
