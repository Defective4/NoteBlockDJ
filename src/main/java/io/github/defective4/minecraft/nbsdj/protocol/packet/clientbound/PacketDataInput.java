package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import io.github.defective4.minecraft.nbsdj.protocol.DataTypes;

public class PacketDataInput implements DataInput {
    private final ByteBuffer buffer;

    public PacketDataInput(byte[] data) {
        buffer = ByteBuffer.wrap(data);
    }

    @Override
    public boolean readBoolean() {
        return buffer.get() > 0;
    }

    @Override
    public byte readByte() {
        return buffer.get();
    }

    @Override
    public char readChar() {
        return buffer.getChar();
    }

    @Override
    public double readDouble() {
        return buffer.getDouble();
    }

    @Override
    public float readFloat() {
        return buffer.getFloat();
    }

    public byte[] readFully() {
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return data;
    }

    @Override
    public void readFully(byte[] b) {
        buffer.get(b);
    }

    @Override
    public void readFully(byte[] b, int off, int len) {
        buffer.get(b, off, len);
    }

    @Override
    public int readInt() {
        return buffer.getInt();
    }

    @Override
    public String readLine() {
        return null;
    }

    @Override
    public long readLong() {
        return buffer.getLong();
    }

    @Override
    public short readShort() {
        return buffer.getShort();
    }

    @Override
    public int readUnsignedByte() {
        return buffer.get() + Byte.MIN_VALUE * -1;
    }

    @Override
    public int readUnsignedShort() {
        return buffer.getShort() + Short.MIN_VALUE * -1;
    }

    @Override
    public String readUTF() {
        byte[] data = new byte[readVarInt()];
        readFully(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    public UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    public int readVarInt() {
        try {
            return DataTypes.readVarInt(this);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int skipBytes(int n) {
        buffer.position(buffer.position() + n);
        return n;
    }

}
