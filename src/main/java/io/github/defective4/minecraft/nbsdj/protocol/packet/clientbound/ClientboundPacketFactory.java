package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound;

public interface ClientboundPacketFactory<T extends ClientboundPacket> {
    T createPacket(PacketDataInput input);
}
