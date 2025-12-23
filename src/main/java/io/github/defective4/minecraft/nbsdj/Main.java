package io.github.defective4.minecraft.nbsdj;

import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;

public class Main {
    public static void main(String[] args) {
        try {
            NoteBlockBot bot = new NoteBlockBot("localhost", 25565);
            bot.addListener(new ClientAdapter() {

                @Override
                public void gameJoined(int entityId) {
                    System.out.println("Joined as " + entityId);
                }

                @Override
                public void gameStateChanged(GameState oldState, GameState newState) {
                    System.out.println("State changed from " + oldState + " to " + newState);
                }

                @Override
                public void loggedIn(GameProfile profile) {
                    System.out.println("Logged in as " + profile);
                }
            });
            bot.connect("NoteBlockDJ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
