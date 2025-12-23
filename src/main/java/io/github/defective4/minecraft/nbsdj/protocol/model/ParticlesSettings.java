package io.github.defective4.minecraft.nbsdj.protocol.model;

public enum ParticlesSettings {
    ALL(0), DECREASED(1), MINIMAL(2);

    private int id;

    ParticlesSettings(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
