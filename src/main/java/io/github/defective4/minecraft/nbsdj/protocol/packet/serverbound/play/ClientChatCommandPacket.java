package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientChatCommandPacket extends ServerboundPacket {
    public ClientChatCommandPacket(String comamnd) {
        super(6);
        writeUTF(comamnd);
    }
}
