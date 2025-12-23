package io.github.defective4.minecraft.nbsdj.protocol.model;

public enum Hand {
    LEFT(0), RIGHT(1);

    private final int id;

    Hand(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
