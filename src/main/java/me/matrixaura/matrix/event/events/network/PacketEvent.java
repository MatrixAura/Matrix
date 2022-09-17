package me.matrixaura.matrix.event.events.network;

import me.matrixaura.matrix.core.event.decentralization.EventData;
import me.matrixaura.matrix.event.MatrixEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends MatrixEvent implements EventData {

    public final Packet<?> packet;

    public PacketEvent(final Packet<?> packet) {
        this.packet = packet;
    }

    public static class Receive extends PacketEvent {
        public Receive(final Packet<?> packet) {
            super(packet);
        }
    }

    public static class Send extends PacketEvent {
        public Send(final Packet<?> packet) {
            super(packet);
        }
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
