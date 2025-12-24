package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.model.BlockLocation;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientDestroyBlockPacket extends ServerboundPacket {
    public ClientDestroyBlockPacket(BlockLocation block, int sequence) {
        super(40);
        writeVarInt(0);
        BlockLocation.CODEC.write(block, this);
        writeByte(1);
        writeVarInt(sequence);
    }
}
