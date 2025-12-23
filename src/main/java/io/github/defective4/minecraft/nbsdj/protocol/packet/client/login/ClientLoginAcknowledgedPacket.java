package io.github.defective4.minecraft.nbsdj.protocol.packet.client.login;

import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientLoginAcknowledgedPacket extends ServerboundPacket {
    public ClientLoginAcknowledgedPacket() {
        super(3);
    }
}
