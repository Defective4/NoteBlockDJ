package io.github.defective4.minecraft.nbsdj.protocol;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public interface Codec<T> {
    default ListCodec<T> asListCodec() {
        return new ListCodec<>() {
            @Override
            public T read(PacketDataInput in) {
                return Codec.this.read(in);
            }

            @Override
            public void write(T t, ServerboundPacket packet) {
                Codec.this.write(t, packet);
            }
        };
    }

    T read(PacketDataInput in);

    void write(T t, ServerboundPacket packet);
}
