package io.github.defective4.minecraft.nbsdj.music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import io.github.defective4.minecraft.nbs.NBSTrack;
import io.github.defective4.minecraft.nbs.model.Layer;
import io.github.defective4.minecraft.nbs.model.Note;
import io.github.defective4.minecraft.nbs.model.instrument.VanillaInstrument;
import io.github.defective4.minecraft.nbsdj.NoteBlockBot;
import io.github.defective4.minecraft.nbsdj.music.SongTask.Type;
import io.github.defective4.minecraft.nbsdj.protocol.model.BlockLocation;
import io.github.defective4.minecraft.nbsdj.protocol.model.ItemStack;
import io.github.defective4.minecraft.nbsdj.protocol.model.ItemType;

public class SongStructure {

    public static final int MAX_LENGTH = 3;
    public static final int MAX_WIDTH = 3;

    private final NoteBlockBot bot;

    private final List<StructureListener> listeners = new CopyOnWriteArrayList<>();
    private final Map<SimpleNote, BlockLocation> notes = new LinkedHashMap<>();
    private final List<SongTask<?>> tasks = new ArrayList<>();

    private Timer timer;
    private final NBSTrack track;

    public SongStructure(NBSTrack track, NoteBlockBot bot) {
        this.track = track;
        this.bot = bot;

        List<SimpleNote> notes = new ArrayList<>();

        for (Layer layer : track.getLayers()) for (Note note : layer.notes()) {
            if (note.isWithinOctaveLimit() && note.instrument() instanceof VanillaInstrument vanilla) {
                SimpleNote sn = new SimpleNote(note.key(), vanilla);
                if (!notes.contains(sn)) notes.add(sn);
            }
        }

        Iterator<SimpleNote> notesI = notes.iterator();

        for (int y = -1; y <= 4; y += 5) {
            if (!notesI.hasNext()) break;
            for (int x = -MAX_WIDTH; x <= MAX_WIDTH; x++) {
                if (!notesI.hasNext()) break;
                for (int z = -MAX_LENGTH; z <= MAX_LENGTH; z++) {
                    if (!notesI.hasNext()) break;
                    this.notes.put(notesI.next(), new BlockLocation(x, y, z));
                }
            }
        }
    }

    public boolean addListener(StructureListener listener) {
        return listeners.add(listener);
    }

    public void build() throws IOException {
        tasks.clear();
        bot.sendCommand("gamemode creative");
        bot.sendCommand("clear");
        for (Entry<SimpleNote, BlockLocation> entry : notes.entrySet()) {
            BlockLocation base = bot.getLocation().toBlockLocation().add(entry.getValue());

            tasks.add(new SongTask<>(Type.DESTROY_BLOCK, base));
            tasks.add(new SongTask<>(Type.DESTROY_BLOCK, base.add(0, -1, 0)));

            SimpleNote note = entry.getKey();
            ItemType block = MusicBlockRelations.getBlockType(note.instrument());
            if (block == ItemType.SAND) {
                tasks.add(new SongTask<>(Type.DESTROY_BLOCK, base.add(0, -2, 0)));
                tasks.add(new SongTask<>(Type.GET_ITEM, new ItemStack(1, ItemType.GLASS)));
                tasks.add(new SongTask<>(Type.USE_ITEM, base.add(0, -2, 0)));
            }
            tasks.add(new SongTask<>(Type.GET_ITEM, new ItemStack(1, block)));

            tasks.add(new SongTask<>(Type.USE_ITEM, base.add(0, -1, 0)));
            tasks.add(new SongTask<>(Type.GET_ITEM, new ItemStack(1, ItemType.NOTE_BLOCK)));
            tasks.add(new SongTask<>(Type.USE_ITEM, base));

            tasks.add(new SongTask<>(Type.GET_ITEM, new ItemStack(0, null)));
            for (int i = 0; i < note.key() - 33; i++) {
                tasks.add(new SongTask<>(Type.USE_ITEM, base));
            }
        }
        startBuildTaskTimer();
    }

    public List<StructureListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    public void play() throws IOException {
        tasks.clear();
        bot.sendCommand("gamemode survival");
        bot.sendCommand("clear");
        startPlayerTask();
    }

    public boolean removeListener(StructureListener listener) {
        return listeners.remove(listener);
    }

    private void cleanUpTimer() {
        if (timer != null) timer.cancel();
        timer = new Timer(true);
    }

    private void startBuildTaskTimer() {
        cleanUpTimer();
        timer.scheduleAtFixedRate(new TimerTask() {
            Iterator<SongTask<?>> taskI = tasks.iterator();

            @Override
            public void run() {
                try {
                    if (taskI.hasNext()) {
                        SongTask<?> next = taskI.next();
                        Type<?> type = next.type();
                        if (type == Type.DESTROY_BLOCK) {
                            bot.destroyBlock((BlockLocation) next.data());
                        } else if (type == Type.USE_ITEM) {
                            bot.useItem((BlockLocation) next.data());
                        } else if (type == Type.GET_ITEM) {
                            bot.createItem(36, (ItemStack) next.data());
                        }
                    } else {
                        cancel();
                        timer.cancel();
                        timer = null;
                        listeners.forEach(t -> {
                            try {
                                t.structureBuilt();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    cancel();
                    timer.cancel();
                    timer = null;
                }
            }
        }, 0, 50);
    }

    private void startPlayerTask() {
        cleanUpTimer();
        Map<Integer, List<SimpleNote>> notes = new HashMap<>();
        for (Layer layer : track.getLayers()) for (Note note : layer.notes()) {
            if (note.isWithinOctaveLimit() && note.instrument() instanceof VanillaInstrument v) {
                notes.computeIfAbsent((int) note.tick(), e -> new ArrayList<>()).add(new SimpleNote(note.key(), v));
            }
        }
        timer.scheduleAtFixedRate(new TimerTask() {
            int tick = 0;

            @Override
            public void run() {
                try {
                    if (notes.containsKey(tick)) {
                        for (SimpleNote note : notes.get(tick)) {
                            if (SongStructure.this.notes.containsKey(note)) {
                                bot.destroyBlock(
                                        bot.getLocation().toBlockLocation().add(SongStructure.this.notes.get(note)));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    cancel();
                    cleanUpTimer();
                }
                tick++;
                if (tick >= track.songLengthTicks()) {
                    cancel();
                    cleanUpTimer();
                    listeners.forEach(t -> {
                        try {
                            t.songEnded();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }, 500, (int) (1000 / track.tempo()));
    }
}
