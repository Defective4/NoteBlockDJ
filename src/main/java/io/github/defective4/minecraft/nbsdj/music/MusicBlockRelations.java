package io.github.defective4.minecraft.nbsdj.music;

import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.BANJO;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.BASS;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.BIT;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.CHIME;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.CLICK;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.COWBELL;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.DIDIGERIDOO;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.DOUBLE_BASS;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.FLUTE;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.GUITAR;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.IRON_XYLOPHONE;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.PIANO;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.PLING;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.SNARE;
import static io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument.XYLOPHONE;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.BONE_BLOCK;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.CLAY;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.COBBLESTONE;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.DIRT;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.EMERALD_BLOCK;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.GLASS;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.GLOWSTONE;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.GOLD_BLOCK;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.HAY_BLOCK;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.IRON_BLOCK;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.OAK_PLANKS;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.PACKED_ICE;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.PUMPKIN;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.SAND;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.SOUL_SAND;
import static io.github.defective4.minecraft.nbsdj.protocol.model.ItemType.WHITE_WOOL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument;
import io.github.defective4.minecraft.nbsdj.protocol.model.ItemType;

public class MusicBlockRelations {

    private static final Map<VanillaInstrument, ItemType> BLOCKS;

    static {
        Map<VanillaInstrument, ItemType> blocks = new HashMap<>();
        blocks.putAll(Map.of(PIANO, DIRT, BASS, OAK_PLANKS, DOUBLE_BASS, COBBLESTONE, SNARE, SAND, CLICK, GLASS, GUITAR,
                WHITE_WOOL, FLUTE, CLAY, VanillaInstrument.BELL, GOLD_BLOCK, CHIME, PACKED_ICE));
        blocks.putAll(Map.of(XYLOPHONE, BONE_BLOCK, IRON_XYLOPHONE, IRON_BLOCK, COWBELL, SOUL_SAND, DIDIGERIDOO,
                PUMPKIN, BIT, EMERALD_BLOCK, BANJO, HAY_BLOCK, PLING, GLOWSTONE));
        BLOCKS = Collections.unmodifiableMap(blocks);
    }

    private MusicBlockRelations() {}

    public static ItemType getBlockType(VanillaInstrument instrument) {
        return BLOCKS.getOrDefault(instrument, ItemType.DIRT);
    }
}
