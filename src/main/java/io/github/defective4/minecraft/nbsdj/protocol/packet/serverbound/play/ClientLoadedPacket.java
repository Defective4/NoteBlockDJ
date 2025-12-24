package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientLoadedPacket extends ServerboundPacket {
    public ClientLoadedPacket() {
        super(43);
    }
}
