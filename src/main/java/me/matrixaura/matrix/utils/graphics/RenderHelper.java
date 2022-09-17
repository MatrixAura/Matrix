package me.matrixaura.matrix.utils.graphics;

import me.matrixaura.matrix.module.modules.client.ActiveModuleList;
import me.matrixaura.matrix.utils.math.Vec2I;
import net.minecraft.client.gui.ScaledResolution;

public class RenderHelper {

    public static Vec2I getStart(ScaledResolution scaledResolution, ActiveModuleList.ListPos caseIn) {
        switch (caseIn) {
            case RightDown: {
                return new Vec2I(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
            case LeftTop: {
                return new Vec2I(0, 0);
            }
            case LeftDown: {
                return new Vec2I(0, scaledResolution.getScaledHeight());
            }
            default: {
                return new Vec2I(scaledResolution.getScaledWidth(), 0);
            }
        }
    }

}
