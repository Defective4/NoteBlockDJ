package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.model.Hand;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientSwingArmPacket extends ServerboundPacket {
    public ClientSwingArmPacket(Hand hand) {
        super(60);
        writeVarInt(hand.getInteractionId());
    }
}
