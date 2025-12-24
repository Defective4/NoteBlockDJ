package io.github.defective4.minecraft.nbsdj;

import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;

public interface ClientListener {
    void gameJoined(int entityId);

    void gameStateChanged(GameState oldState, GameState newState);

    void keepAliveReceived(long id);

    void loggedIn(GameProfile profile);
}
