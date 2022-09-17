package me.matrixaura.matrix.core.setting.settings;

import me.matrixaura.matrix.core.setting.NumberSetting;

public class FloatSetting extends NumberSetting<Float> {
    public FloatSetting(String name, float defaultValue, float min, float max) {
        super(name, defaultValue, min, max);
    }
}
