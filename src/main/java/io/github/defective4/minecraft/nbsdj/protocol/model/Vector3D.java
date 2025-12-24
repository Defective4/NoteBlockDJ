package io.github.defective4.minecraft.nbsdj.protocol.model;

import io.github.defective4.minecraft.nbsdj.protocol.Codec;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public record Vector3D(double x, double y, double z) {
    public static final Codec<Vector3D> CODEC = new Codec<>() {

        @Override
        public Vector3D read(PacketDataInput in) {
            return new Vector3D(in.readDouble(), in.readDouble(), in.readDouble());
        }

        @Override
        public void write(Vector3D t, ServerboundPacket packet) {
            packet.writeDouble(t.x);
            packet.writeDouble(t.y);
            packet.writeDouble(t.z);
        }
    };

    public BlockLocation toBlockLocation() {
        return new BlockLocation((int) x, (int) y, (int) z);
    }

    public Vector3D add(double x, double y, double z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }
}
