package me.matrixaura.matrix.utils;

import me.matrixaura.matrix.core.setting.Setting;

import java.awt.*;
import java.util.Arrays;

public class Palette extends Setting<Integer> {

    Setting<Integer> r, g, b, a;

    public Palette(String name, Integer defaultValue,Setting<Integer> r, Setting<Integer> g, Setting<Integer> b, Setting<Integer> a) {
        super(name, defaultValue);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public Integer getValue() {
        return getColor().getRGB();
    }

    public Color getColor() {
        return new Color(r.getValue(), g.getValue(), b.getValue(), a.getValue());
    }
}
