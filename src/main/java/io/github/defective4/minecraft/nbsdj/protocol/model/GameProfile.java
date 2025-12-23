package io.github.defective4.minecraft.nbsdj.protocol.model;

import java.util.UUID;

import io.github.defective4.minecraft.nbsdj.protocol.Codec;

public record GameProfile(UUID id, String name) {

    public static final Codec<GameProfile> CODEC = in -> {
        UUID id = in.readUUID();
        String name = in.readUTF();
        int len = in.readVarInt();
        for (int i = 0; i < len; i++) {
            in.readUTF();
            in.readUTF();
            if (in.readBoolean()) in.readUTF();
        }
        return new GameProfile(id, name);
    };

}
