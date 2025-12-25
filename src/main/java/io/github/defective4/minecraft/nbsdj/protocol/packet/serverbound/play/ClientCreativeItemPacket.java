package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.model.ItemStack;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientCreativeItemPacket extends ServerboundPacket {
    public ClientCreativeItemPacket(int slot, ItemStack item) {
        super(55);
        writeShort(slot);
        ItemStack.CODEC.write(item, this);
    }
}
