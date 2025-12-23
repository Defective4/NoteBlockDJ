package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.configuration;

import java.util.List;

import io.github.defective4.minecraft.nbsdj.protocol.model.KnownPack;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public record ServerConfigKnownPacksPacket(List<KnownPack> packs) implements ClientboundPacket {
    public static final ClientboundPacketFactory<ServerConfigKnownPacksPacket> FACTORY = input -> new ServerConfigKnownPacksPacket(
            KnownPack.CODEC.asListCodec().readList(input));
}
