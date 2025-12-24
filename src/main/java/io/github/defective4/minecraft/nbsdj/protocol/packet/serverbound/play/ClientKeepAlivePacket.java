package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientKeepAlivePacket extends ServerboundPacket {
    public ClientKeepAlivePacket(long id) {
        super(27);
        writeLong(id);
    }
}
