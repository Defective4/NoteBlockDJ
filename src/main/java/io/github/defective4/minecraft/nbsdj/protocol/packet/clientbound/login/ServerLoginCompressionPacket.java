package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login;

import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public record ServerLoginCompressionPacket(int threshold) implements ClientboundPacket {
    public static final ClientboundPacketFactory<ServerLoginCompressionPacket> FACTORY = input -> new ServerLoginCompressionPacket(
            input.readVarInt());

}
