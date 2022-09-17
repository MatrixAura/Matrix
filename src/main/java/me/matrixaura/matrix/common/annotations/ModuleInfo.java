package me.matrixaura.matrix.common.annotations;

import me.matrixaura.matrix.module.Category;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();

    int keyCode() default Keyboard.KEY_NONE;

    Category category();

    String description() default "";

    boolean visible() default true;
}
