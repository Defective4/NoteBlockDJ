package io.github.defective4.minecraft.nbsdj.protocol.model;

public enum Hand {
    LEFT(0, 1), RIGHT(1, 0);

    private final int id, interactionId;

    Hand(int id, int interactionId) {
        this.id = id;
        this.interactionId = interactionId;
    }

    public int getId() {
        return id;
    }

    public int getInteractionId() {
        return interactionId;
    }

}
