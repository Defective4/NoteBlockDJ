package io.github.defective4.minecraft.nbsdj.protocol.model;

import io.github.defective4.minecraft.nbsdj.protocol.Codec;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public record ClientInformation(String language, byte viewDistance, ChatVisibility chatVisibility, boolean chatColors,
        byte modelMask, Hand mainHand, boolean textFiltering, boolean allowListings,
        ParticlesSettings particlesSettings) {

    public static final Codec<ClientInformation> CODEC = new Codec<>() {

        @Override
        public ClientInformation read(PacketDataInput in) {
            throw new IllegalStateException();
        }

        @Override
        public void write(ClientInformation t, ServerboundPacket packet) {
            packet.writeUTF(t.language);
            packet.writeByte(t.viewDistance);
            packet.writeVarInt(t.chatVisibility.getId());
            packet.writeBoolean(t.chatColors);
            packet.writeByte(t.modelMask);
            packet.writeVarInt(t.mainHand.getId());
            packet.writeBoolean(t.textFiltering);
            packet.writeBoolean(t.allowListings);
            packet.writeVarInt(t.particlesSettings.getId());
        }
    };
}
