package io.github.defective4.minecraft.nbsdj.protocol.model;

import io.github.defective4.minecraft.nbsdj.protocol.Codec;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public record KnownPack(String namespace, String id, String version) {
    public static final Codec<KnownPack> CODEC = new Codec<>() {

        @Override
        public KnownPack read(PacketDataInput in) {
            return new KnownPack(in.readUTF(), in.readUTF(), in.readUTF());
        }

        @Override
        public void write(KnownPack t, ServerboundPacket packet) {
            packet.writeUTF(t.namespace());
            packet.writeUTF(t.id());
            packet.writeUTF(t.version());
        }
    };
}
