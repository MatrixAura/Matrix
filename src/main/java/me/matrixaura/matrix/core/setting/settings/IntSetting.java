package me.matrixaura.matrix.core.setting.settings;

import me.matrixaura.matrix.core.setting.NumberSetting;

public class IntSetting extends NumberSetting<Integer> {
    public IntSetting(String name, int defaultValue, int min, int max) {
        super(name, defaultValue, min, max);
    }
}
