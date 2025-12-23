package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.configuration;

import java.util.Collections;
import java.util.List;

import io.github.defective4.minecraft.nbsdj.protocol.model.KnownPack;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public class ServerConfigKnownPacksPacket extends ClientboundPacket {

    public static final ClientboundPacketFactory<ServerConfigKnownPacksPacket> FACTORY = input -> new ServerConfigKnownPacksPacket(
            KnownPack.CODEC.asListCodec().readList(input));

    private final List<KnownPack> packs;

    public ServerConfigKnownPacksPacket(List<KnownPack> packs) {
        this.packs = Collections.unmodifiableList(packs);
    }

    public List<KnownPack> getPacks() {
        return packs;
    }

}
