package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.model.BlockLocation;
import io.github.defective4.minecraft.nbsdj.protocol.model.Hand;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientUseItemOnPacket extends ServerboundPacket {
    public ClientUseItemOnPacket(int sequence, BlockLocation block) {
        super(63);
        writeVarInt(Hand.RIGHT.getInteractionId());

        BlockLocation.CODEC.write(block, this);

        writeVarInt(1);
        writeFloat(0.5f);
        writeFloat(1);
        writeFloat(0.5f);

        writeBoolean(false);
        writeBoolean(false);

        writeVarInt(sequence);
    }
}
