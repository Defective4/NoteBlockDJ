package io.github.defective4.minecraft.nbsdj.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public abstract class ListCodec<T> implements Codec<T> {
    public List<T> readList(PacketDataInput in) {
        List<T> list = new ArrayList<>();
        int len = in.readVarInt();
        for (int i = 0; i < len; i++) {
            list.add(read(in));
        }
        return Collections.unmodifiableList(list);
    }

    public void writeList(ServerboundPacket packet, List<T> list) {
        packet.writeVarInt(list.size());
        for (T t : list) write(t, packet);
    }
}
