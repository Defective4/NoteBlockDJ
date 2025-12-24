package io.github.defective4.minecraft.nbsdj;

import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;

public abstract class ClientAdapter implements ClientListener {

    @Override
    public void gameJoined(int entityId) {}

    @Override
    public void gameStateChanged(GameState oldState, GameState newState) {}

    @Override
    public void keepAliveReceived(long id) {}

    @Override
    public void loggedIn(GameProfile profile) {}

}
