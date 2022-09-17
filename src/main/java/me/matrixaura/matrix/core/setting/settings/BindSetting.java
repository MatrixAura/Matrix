package me.matrixaura.matrix.core.setting.settings;

import me.matrixaura.matrix.core.common.KeyBind;
import me.matrixaura.matrix.core.setting.Setting;

public class BindSetting extends Setting<KeyBind> {
    public BindSetting(String name, KeyBind defaultValue) {
        super(name, defaultValue);
    }
}
