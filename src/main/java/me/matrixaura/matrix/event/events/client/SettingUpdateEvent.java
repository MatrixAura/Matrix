package me.matrixaura.matrix.event.events.client;

import me.matrixaura.matrix.core.setting.Setting;
import me.matrixaura.matrix.event.MatrixEvent;

public class SettingUpdateEvent extends MatrixEvent {

    private final Setting<?> setting;

    public SettingUpdateEvent(Setting<?> setting) {
        this.setting = setting;
    }

    public Setting<?> getSetting() {
        return setting;
    }
}
