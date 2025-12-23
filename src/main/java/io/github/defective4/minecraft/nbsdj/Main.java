package io.github.defective4.minecraft.nbsdj;

public class Main {
    public static void main(String[] args) {
        try {
            NoteBlockBot bot = new NoteBlockBot("localhost", 25565);
            bot.connect("NoteBlockDJ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
