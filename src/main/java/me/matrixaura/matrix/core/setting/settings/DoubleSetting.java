package me.matrixaura.matrix.core.setting.settings;

import me.matrixaura.matrix.core.setting.NumberSetting;

public class DoubleSetting extends NumberSetting<Double> {
    public DoubleSetting(String name, double defaultValue, double min, double max) {
        super(name, defaultValue, min, max);
    }
}
