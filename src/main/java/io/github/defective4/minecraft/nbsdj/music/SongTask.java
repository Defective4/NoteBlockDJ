package io.github.defective4.minecraft.nbsdj.music;

import io.github.defective4.minecraft.nbsdj.protocol.model.BlockLocation;
import io.github.defective4.minecraft.nbsdj.protocol.model.ItemStack;

public record SongTask<T>(Type<T> type, T data, long delay) {
    public static class Type<T> {
        public static final Type<BlockLocation> DESTROY_BLOCK = new Type<>();

        public static final Type<ItemStack> GET_ITEM = new Type<>();
        public static final Type<BlockLocation> USE_ITEM = new Type<>();

        private Type() {

        }
    }

    public SongTask(Type<T> type, T data) {
        this(type, data, 250);
    }
}
