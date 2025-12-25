package io.github.defective4.minecraft.nbsdj;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.zip.DataFormatException;

import io.github.defective4.minecraft.nbs.NBSTrack;
import io.github.defective4.minecraft.nbs.io.NBSReader;
import io.github.defective4.minecraft.nbsdj.music.SongStructure;
import io.github.defective4.minecraft.nbsdj.music.StructureListener;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;
import io.github.defective4.minecraft.nbsdj.protocol.model.Vector3D;

public class NoteBlockDJ {

    private NoteBlockBot bot;
    private final Map<String, Consumer<String[]>> commands = new LinkedHashMap<>();
    private boolean ready;
    private SongStructure song;

    public NoteBlockDJ() {
        commands.put("connect <address> <username> - connects to a server", t -> {
            if (t.length < 2) {
                System.err.println("Usage: connect <address> <username>");
            } else if (bot != null) {
                System.err.println("The bot is already connected");
            } else {
                String host = t[0];
                int port = 25565;
                String[] split = host.split(":");
                if (split.length > 1) {
                    Integer.parseInt(split[1]);
                    host = split[0];
                }
                bot = new NoteBlockBot(host, port);
                ready = false;
                bot.addListener(new ClientListener() {

                    @Override
                    public void gameJoined(int entityId) {
                        System.err.println("Game joined");
                    }

                    @Override
                    public void gameStateChanged(GameState oldState, GameState newState) {}

                    @Override
                    public void keepAliveReceived(long id) {}

                    @Override
                    public void loggedIn(GameProfile profile) {
                        System.err.println("Logged in");
                    }

                    @Override
                    public void playerTeleported(Vector3D newLocation) {
                        System.err.println("Player ready!");
                        ready = true;
                    }
                });
                Thread.startVirtualThread(() -> {
                    try {
                        System.err.println("Connecting...");
                        bot.connect(t[1]);
                    } catch (IOException | DataFormatException e) {
                        e.printStackTrace();
                        try {
                            bot.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            bot = null;
                            if (song != null) song.cleanUpTimer();
                            song = null;
                        }
                    }
                });
            }
        });
        commands.put("disconnect - disconnects from a server", t -> {
            if (bot != null) {
                try {
                    bot.close();
                    song = null;
                    if (song != null) song.cleanUpTimer();
                    System.err.println("Disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                System.err.println("The bot it not connected");
        });
        commands.put("load <file> - loads a song from a NBS file", t -> {
            if (t.length == 0) {
                System.err.println("Missing file name");
            } else if (bot == null) {
                System.err.println("You have to connect to a server first");
            } else
                try {
                    System.err.println("Loading song...");
                    NBSTrack track = NBSReader.read(new File(String.join(" ", t)));
                    song = new SongStructure(track, bot);
                    song.addListener(new StructureListener() {

                        @Override
                        public void songEnded() throws IOException {
                            System.err.println("Song playing completed!");
                        }

                        @Override
                        public void structureBuilt() throws IOException {
                            System.err.println("Song building finished!");
                        }
                    });
                    System.err.println(String.format("Loaded song %s with %s notes", track.songName().orElse("Unnamed"),
                            track.getLayers().stream().mapToInt(e -> e.notes().size()).sum()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
        });
        commands.put("build - builds a loaded song where the player is standing", t -> {
            if (song == null) {
                System.err.println("There is no song loaded");
            }
            if (!ready) {
                System.err.println("Player connection is not ready");
            } else {
                try {
                    song.build();
                    System.err.println("Started building");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        commands.put("play - plays a loaded song where the player is standing", t -> {
            if (song == null) {
                System.err.println("There is no song loaded");
            }
            if (!ready) {
                System.err.println("Player connection is not ready");
            } else {
                try {
                    song.play();
                    System.err.println("Started playing");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        commands.put("stop - stops the current task", t -> {
            if (song == null) {
                System.err.println("There is no task to be stopped");
            } else {
                System.err.println("Stopped all tasks");
                song.cleanUpTimer();
            }
        });
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.err.print("> ");
                String line = scanner.nextLine();
                String[] cmdline = line.split(" ");
                String command = cmdline[0];
                String[] args;
                if (cmdline.length > 1) {
                    args = new String[cmdline.length - 1];
                    System.arraycopy(cmdline, 1, args, 0, args.length);
                } else {
                    args = new String[0];
                }

                boolean found = false;
                for (Entry<String, Consumer<String[]>> entry : commands.entrySet()) {
                    String key = entry.getKey();
                    if (key.contains(" ")) key = key.substring(0, key.indexOf(' '));
                    if (key.equalsIgnoreCase(command)) {
                        entry.getValue().accept(args);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.err.println("Unknown command \"" + command + "\"");
                    printHelp();
                }
            }
        }
    }

    private void printHelp() {
        System.err.println("Available commands:");
        for (String key : commands.keySet()) {
            System.err.println("  " + key);
        }
    }
}
