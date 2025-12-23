package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login;

import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public class ServerLoginCompressionPacket extends ClientboundPacket {

    public static final ClientboundPacketFactory<ServerLoginCompressionPacket> FACTORY = input -> new ServerLoginCompressionPacket(
            input.readVarInt());

    private final int threshold;

    public ServerLoginCompressionPacket(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

}
