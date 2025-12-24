package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.configuration.ServerConfigFinishedPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.configuration.ServerConfigKnownPacksPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginCompressionPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginSuccessPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.play.ServerGameJoinPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.play.ServerKeepAlivePacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.play.ServerPlayerPositionPacket;

public class ClientboundPacketRegistry {
    private static final Map<GameState, Map<Integer, ClientboundPacketFactory<?>>> PACKETS = new HashMap<>();

    static {
        {
            Map<Integer, ClientboundPacketFactory<?>> map = new HashMap<>();
            PACKETS.put(GameState.LOGIN, map);
            map.put(2, ServerLoginSuccessPacket.FACTORY);
            map.put(3, ServerLoginCompressionPacket.FACTORY);
        }
        {
            Map<Integer, ClientboundPacketFactory<?>> map = new HashMap<>();
            PACKETS.put(GameState.CONFIGURATION, map);
            map.put(3, ServerConfigFinishedPacket.FACTORY);
            map.put(14, ServerConfigKnownPacksPacket.FACTORY);
        }
        {
            Map<Integer, ClientboundPacketFactory<?>> map = new HashMap<>();
            PACKETS.put(GameState.PLAY, map);
            map.put(43, ServerKeepAlivePacket.FACTORY);
            map.put(48, ServerGameJoinPacket.FACTORY);
            map.put(70, ServerPlayerPositionPacket.FACTORY);
        }
    }

    public static Optional<ClientboundPacketFactory<?>> getPacketForId(GameState state, int id) {
        return Optional.ofNullable(PACKETS.computeIfAbsent(state, t -> new HashMap<>()).get(id));
    }
}
