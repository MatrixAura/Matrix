package me.matrixaura.matrix.event.decentraliized;

import me.matrixaura.matrix.core.event.decentralization.DecentralizedEvent;
import me.matrixaura.matrix.event.events.network.PacketEvent;

public class DecentralizedPacketEvent {
    public static class Send extends DecentralizedEvent<PacketEvent.Send> {
        public static Send instance = new Send();
    }

    public static class Receive extends DecentralizedEvent<PacketEvent.Receive> {
        public static Receive instance = new Receive();
    }
}
