package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.configuration;

import java.util.List;

import io.github.defective4.minecraft.nbsdj.protocol.model.KnownPack;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientConfigKnownPacksPacket extends ServerboundPacket {
    public ClientConfigKnownPacksPacket(List<KnownPack> packs) {
        super(7);
        KnownPack.CODEC.asListCodec().writeList(this, packs);
    }
}
