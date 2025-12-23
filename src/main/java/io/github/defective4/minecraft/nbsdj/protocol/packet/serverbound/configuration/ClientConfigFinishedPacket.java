package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.configuration;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientConfigFinishedPacket extends ServerboundPacket {
    public ClientConfigFinishedPacket() {
        super(3);
    }
}
