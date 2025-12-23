package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.configuration;

import io.github.defective4.minecraft.nbsdj.protocol.model.ClientInformation;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;

public class ClientConfigInformationPacket extends ServerboundPacket {
    public ClientConfigInformationPacket(ClientInformation info) {
        super(0);
        ClientInformation.CODEC.write(info, this);
    }
}
