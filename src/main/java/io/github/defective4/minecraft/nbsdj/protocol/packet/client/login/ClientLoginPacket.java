package io.github.defective4.minecraft.nbsdj.protocol.packet.client.login;

import java.util.UUID;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientLoginPacket extends ServerboundPacket {
    public ClientLoginPacket(String name, UUID uuid) {
        super(0);
        writeUTF(name);
        writeUUID(uuid);
    }
}
