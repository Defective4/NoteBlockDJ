package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public record ServerGameJoinPacket(int playerId) implements ClientboundPacket {
    public static final ClientboundPacketFactory<ServerGameJoinPacket> FACTORY = in -> new ServerGameJoinPacket(
            in.readInt());
}
