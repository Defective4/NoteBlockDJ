package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.configuration;

import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public class ServerConfigFinishedPacket extends ClientboundPacket {
    public static final ClientboundPacketFactory<ServerConfigFinishedPacket> FACTORY = input -> new ServerConfigFinishedPacket();

    public ServerConfigFinishedPacket() {}
}
