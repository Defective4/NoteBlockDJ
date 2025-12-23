package io.github.defective4.minecraft.nbsdj.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DataTypes {
    private static final int CONTINUE_BIT = 0x80;

    private static final int SEGMENT_BITS = 0x7F;

    private DataTypes() {}

    public static int readVarInt(DataInput in) throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = in.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public static void writeVarInt(DataOutput out, int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                out.writeByte(value);
                return;
            }

            out.writeByte(value & SEGMENT_BITS | CONTINUE_BIT);

            value >>>= 7;
        }
    }
}
