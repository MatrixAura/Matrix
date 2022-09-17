package me.matrixaura.matrix.module;

import me.matrixaura.matrix.core.concurrent.task.VoidTask;
import me.matrixaura.matrix.core.setting.Setting;

public class ListenerSetting extends Setting<VoidTask> {
    public ListenerSetting(String name, VoidTask defaultValue) {
        super(name, defaultValue);
    }
}
