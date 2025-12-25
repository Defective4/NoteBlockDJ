package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientRotatePlayerPacket extends ServerboundPacket {
    public ClientRotatePlayerPacket(float yaw, float pitch) {
        super(31);
        writeFloat(yaw);
        writeFloat(pitch);
        writeByte(0x01);
    }
}
