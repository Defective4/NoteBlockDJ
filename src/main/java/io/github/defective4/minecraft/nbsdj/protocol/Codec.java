package io.github.defective4.minecraft.nbsdj.protocol;

import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public interface Codec<T> {
    T read(PacketDataInput in);
}
