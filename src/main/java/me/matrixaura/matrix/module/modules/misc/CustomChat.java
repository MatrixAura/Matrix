package me.matrixaura.matrix.module.modules.misc;

import me.matrixaura.matrix.Matrix;
import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.core.setting.Setting;
import me.matrixaura.matrix.event.events.network.PacketEvent;
import me.matrixaura.matrix.mixin.mixins.accessor.AccessorCPacketChatMessage;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;

@Parallel
@ModuleInfo(name = "CustomChat", category = Category.MISC, description = "Append a suffix on chat message")
public class CustomChat extends Module {

    Setting<Boolean> commands = setting("Commands", false);

    @Override
    public void onPacketSend(PacketEvent.Send event) {
        if (event.packet instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage) event.getPacket()).getMessage();
            if (s.startsWith("/") && !commands.getValue()) return;
            s += Matrix.CHAT_SUFFIX;
            if (s.length() >= 256) s = s.substring(0, 256);
            ((AccessorCPacketChatMessage) event.getPacket()).setMessage(s);
        }
    }

}
