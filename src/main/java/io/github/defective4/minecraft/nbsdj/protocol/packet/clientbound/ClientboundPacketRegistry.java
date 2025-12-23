package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound;

import java.util.HashMap;
import java.util.Map;

import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginCompressionPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginSuccessPacket;

public class ClientboundPacketRegistry {
    private static final Map<Integer, ClientboundPacketFactory<?>> PACKETS = new HashMap<>();

    static {
        PACKETS.put(2, ServerLoginSuccessPacket.FACTORY);
        PACKETS.put(3, ServerLoginCompressionPacket.FACTORY);
    }

    public static ClientboundPacketFactory<?> getPacketForId(int id) {
        return PACKETS.get(id);
    }
}
