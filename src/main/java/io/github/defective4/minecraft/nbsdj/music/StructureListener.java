package io.github.defective4.minecraft.nbsdj.music;

import java.io.IOException;

public interface StructureListener {

    void songEnded() throws IOException;

    void structureBuilt() throws IOException;
}
