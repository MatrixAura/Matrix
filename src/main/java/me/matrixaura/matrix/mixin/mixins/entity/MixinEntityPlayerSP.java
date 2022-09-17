package me.matrixaura.matrix.mixin.mixins.entity;

import me.matrixaura.matrix.Matrix;
import me.matrixaura.matrix.event.events.client.ChatEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Inject(method = "sendChatMessage", at = @At(value = "HEAD"), cancellable = true)
    public void sendChatPacket(String message, CallbackInfo ci) {
        ChatEvent event = new ChatEvent(message);
        Matrix.EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

}
