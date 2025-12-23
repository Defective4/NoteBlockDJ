package io.github.defective4.minecraft.nbsdj.protocol.model;

public enum ChatVisibility {
    FULL(0), HIDDEN(2), SYSTEM(1);

    private final int id;

    ChatVisibility(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
