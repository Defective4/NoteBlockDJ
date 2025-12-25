package io.github.defective4.minecraft.nbsdj.protocol.model;

import io.github.defective4.minecraft.nbsdj.protocol.Codec;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public record ItemStack(int count, ItemType type) {
    public static final Codec<ItemStack> CODEC = new Codec<>() {

        @Override
        public ItemStack read(PacketDataInput in) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void write(ItemStack t, ServerboundPacket packet) {
            int count = t == null ? 0 : t.count;
            packet.writeVarInt(count);
            if (count > 0 && t != null) {
                packet.writeVarInt(t.type.getId());
                packet.writeVarInt(0);
                packet.writeVarInt(0);
            }
        }
    };
}
