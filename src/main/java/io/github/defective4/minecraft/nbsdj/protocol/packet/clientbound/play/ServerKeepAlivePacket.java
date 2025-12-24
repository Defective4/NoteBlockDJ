package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public record ServerKeepAlivePacket(long id) implements ClientboundPacket {
    public static final ClientboundPacketFactory<ServerKeepAlivePacket> FACTORY = input -> new ServerKeepAlivePacket(
            input.readLong());
}
