package io.github.defective4.minecraft.nbsdj.protocol.model;

import io.github.defective4.minecraft.nbsdj.protocol.Codec;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public record BlockLocation(int x, int y, int z) {
    public static final Codec<BlockLocation> CODEC = new Codec<>() {

        @Override
        public BlockLocation read(PacketDataInput in) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void write(BlockLocation t, ServerboundPacket packet) {
            packet.writeLong(t.asLong());
        }
    };

    private static final long PACKED_X_MASK = 67108863L;
    private static final long PACKED_Y_MASK = 4095L;
    private static final long PACKED_Z_MASK = 67108863L;
    private static final int Z_OFFSET = 12;
    private static final int X_OFFSET = 38;

    public long asLong() {
        long $$3 = 0L;
        $$3 |= (x & PACKED_X_MASK) << X_OFFSET;
        $$3 |= (y & PACKED_Y_MASK) << 0;
        return $$3 | (z & PACKED_Z_MASK) << Z_OFFSET;
    }
}
