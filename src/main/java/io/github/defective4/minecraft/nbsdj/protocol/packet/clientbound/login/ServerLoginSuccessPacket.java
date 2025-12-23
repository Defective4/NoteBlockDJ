package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login;

import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public class ServerLoginSuccessPacket extends ClientboundPacket {

    public static final ClientboundPacketFactory<ServerLoginSuccessPacket> FACTORY = input -> new ServerLoginSuccessPacket(
            GameProfile.CODEC.read(input));

    private final GameProfile profile;

    public ServerLoginSuccessPacket(GameProfile profile) {
        this.profile = profile;
    }

    public GameProfile getProfile() {
        return profile;
    }

}
