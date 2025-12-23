package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login;

import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public record ServerLoginSuccessPacket(GameProfile profile) implements ClientboundPacket {
    public static final ClientboundPacketFactory<ServerLoginSuccessPacket> FACTORY = input -> new ServerLoginSuccessPacket(
            GameProfile.CODEC.read(input));
}
