package io.github.defective4.minecraft.nbsdj;

import java.util.Timer;

import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;
import io.github.defective4.minecraft.nbsdj.protocol.model.Hand;
import io.github.defective4.minecraft.nbsdj.protocol.model.Vector3D;

public class Main {
    public static void main(String[] args) {
        try {

            Timer timer = new Timer(true);

            NoteBlockBot bot = new NoteBlockBot("127.0.0.1", 25565);
            bot.addListener(new ClientAdapter() {

                private boolean firstTeleport = true;

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

                @Override
                public void playerTeleported(Vector3D newLocation) {
                    System.out.println("Player teleported  to " + newLocation);
                    if (firstTeleport) {
                        firstTeleport = false;
                        try {
                            bot.sendCommand("gamemode creative");
                            for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) for (int y = -2; y <= -1; y++) {
                                bot.swingArm(Hand.RIGHT);
                                bot.destroyBlock(newLocation.add(x, y, z).toBlockLocation());
                                Thread.sleep(100);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            bot.connect("NoteBlockDJ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
