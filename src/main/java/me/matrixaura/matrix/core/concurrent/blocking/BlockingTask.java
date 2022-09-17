package me.matrixaura.matrix.core.concurrent.blocking;

import me.matrixaura.matrix.core.concurrent.task.Task;

public interface BlockingTask extends Task<BlockingContent> {
    @Override
    void invoke(BlockingContent unit);
}
