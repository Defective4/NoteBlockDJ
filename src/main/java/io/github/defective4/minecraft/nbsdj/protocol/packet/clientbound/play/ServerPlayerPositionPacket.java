package io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.play;

import io.github.defective4.minecraft.nbsdj.protocol.model.Vector3D;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;

public record ServerPlayerPositionPacket(int syncId, Vector3D newLocation, Vector3D motion, float yaw, float pitch)
        implements ClientboundPacket {
    public static final ClientboundPacketFactory<ServerPlayerPositionPacket> FACTORY = in -> new ServerPlayerPositionPacket(
            in.readVarInt(), Vector3D.CODEC.read(in), Vector3D.CODEC.read(in), in.readFloat(), in.readFloat());
}
