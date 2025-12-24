package io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.handshake;

public enum ClientIntention {
    LOGIN(2);

    private final int id;

    ClientIntention(int i) {
        id = i;
    }

    public int getId() {
        return id;
    }

}
