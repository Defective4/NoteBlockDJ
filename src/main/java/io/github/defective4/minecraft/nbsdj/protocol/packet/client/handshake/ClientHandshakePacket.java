package io.github.defective4.minecraft.nbsdj.protocol.packet.client.handshake;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientHandshakePacket extends ServerboundPacket {

    public ClientHandshakePacket(int protocol, String host, int port, ClientIntention intention) {
        super(0);
        writeVarInt(protocol);
        writeUTF(host);
        writeShort(port);
        writeVarInt(intention.getId());
    }

}
