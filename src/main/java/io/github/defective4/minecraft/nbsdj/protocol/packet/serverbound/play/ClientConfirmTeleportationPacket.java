package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientConfirmTeleportationPacket extends ServerboundPacket {
    public ClientConfirmTeleportationPacket(int id) {
        super(0);
        writeVarInt(id);
    }
}
