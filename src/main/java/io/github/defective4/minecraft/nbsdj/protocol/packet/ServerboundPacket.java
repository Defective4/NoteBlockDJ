package io.github.defective4.minecraft.nbsdj.protocol.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import io.github.defective4.minecraft.nbsdj.protocol.DataTypes;

public class ServerboundPacket implements DataOutput {
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final DataOutput wrapper = new DataOutputStream(buffer);

    protected ServerboundPacket(int id) {
        writeVarInt(id);
    }

    public byte[] getData(boolean compressedFormat) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            DataOutputStream wrapper = new DataOutputStream(buffer);

            DataTypes.writeVarInt(wrapper, this.buffer.size() + (compressedFormat ? 1 : 0));
            if (compressedFormat) wrapper.writeByte(0);
            wrapper.write(this.buffer.toByteArray());
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void write(byte[] b) {
        try {
            wrapper.write(b);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) {
        try {
            wrapper.write(b, off, len);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void write(int b) {
        try {
            wrapper.write(b);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeBoolean(boolean v) {
        try {
            wrapper.writeBoolean(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeByte(int v) {
        try {
            wrapper.writeByte(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeBytes(String s) {
        try {
            wrapper.writeBytes(s);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeChar(int v) {
        try {
            wrapper.writeChar(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeChars(String s) {
        try {
            wrapper.writeChars(s);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeDouble(double v) {
        try {
            wrapper.writeDouble(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeFloat(float v) {
        try {
            wrapper.writeFloat(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeInt(int v) {
        try {
            wrapper.writeInt(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeLong(long v) {
        try {
            wrapper.writeLong(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeShort(int v) {
        try {
            wrapper.writeShort(v);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeUTF(String s) {
        byte[] data = s.getBytes(StandardCharsets.UTF_8);
        writeVarInt(data.length);
        write(data);
    }

    public void writeUUID(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public void writeVarInt(int value) {
        try {
            DataTypes.writeVarInt(this, value);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
