package io.github.defective4.minecraft.nbsdj;

import java.io.IOException;
import java.util.zip.DataFormatException;

import io.github.defective4.minecraft.nbsdj.protocol.MinecraftConnection;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;

public class NoteBlockBot {
    private int entityId = -1;
    private GameProfile gameProfile;
    private final String host;

    private final int port;

    public NoteBlockBot(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }

    public void connect(String username) throws IOException, DataFormatException {
        try (MinecraftConnection connection = new MinecraftConnection(host, port, this)) {
            connection.open(username);
        }
    }

    public int getEntityId() {
        return entityId;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }
}
