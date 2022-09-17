package me.matrixaura.matrix.mixin.mixins.render;

import me.matrixaura.matrix.Matrix;
import me.matrixaura.matrix.event.decentraliized.DecentralizedRenderTickEvent;
import me.matrixaura.matrix.event.events.render.RenderOverlayEvent;
import net.minecraft.client.gui.GuiSubtitleOverlay;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSubtitleOverlay.class)
public class MixinGuiSubtitleOverlay {

    @Inject(method = "renderSubtitles", at = @At("HEAD"))
    public void onRender2D(ScaledResolution resolution, CallbackInfo ci) {
        RenderOverlayEvent event = new RenderOverlayEvent();
        DecentralizedRenderTickEvent.instance.post(event);
        Matrix.EVENT_BUS.post(event);
    }

}
