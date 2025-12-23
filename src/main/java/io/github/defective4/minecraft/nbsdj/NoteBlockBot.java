package io.github.defective4.minecraft.nbsdj;

import java.io.IOException;
import java.util.zip.DataFormatException;

import io.github.defective4.minecraft.nbsdj.protocol.MinecraftConnection;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;

public class NoteBlockBot {
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

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }
}
