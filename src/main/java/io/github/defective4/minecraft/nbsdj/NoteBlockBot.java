package io.github.defective4.minecraft.nbsdj;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.DataFormatException;

import io.github.defective4.minecraft.nbsdj.protocol.MinecraftConnection;
import io.github.defective4.minecraft.nbsdj.protocol.model.BlockLocation;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameProfile;
import io.github.defective4.minecraft.nbsdj.protocol.model.Hand;
import io.github.defective4.minecraft.nbsdj.protocol.model.ItemStack;
import io.github.defective4.minecraft.nbsdj.protocol.model.Vector3D;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play.ClientChatCommandPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play.ClientCreativeItemPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play.ClientDestroyBlockPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play.ClientRotatePlayerPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play.ClientSwingArmPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.play.ClientUseItemOnPacket;

public class NoteBlockBot {
    private MinecraftConnection connection;
    private int entityId = -1;
    private GameProfile gameProfile;

    private final String host;

    private final List<ClientListener> listeners = new CopyOnWriteArrayList<>();
    private Vector3D location = new Vector3D(0, 0, 0);

    private final int port;
    private final Random random = new Random();

    public NoteBlockBot(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }

    public boolean addListener(ClientListener listener) {
        return listeners.add(listener);
    }

    public void connect(String username) throws IOException, DataFormatException {
        try (MinecraftConnection connection = new MinecraftConnection(host, port, this)) {
            this.connection = connection;
            connection.open(username);
        }
    }

    public void createItem(int slot, ItemStack item) throws IOException {
        connection.sendPacket(new ClientCreativeItemPacket(slot, item));
    }

    public void destroyBlock(BlockLocation location) throws IOException {
        lookAt(location.toVector3D());
        connection.sendPacket(new ClientDestroyBlockPacket(location, Math.abs(random.nextInt())));
        swingArm(Hand.RIGHT);
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

    public List<ClientListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    public Vector3D getLocation() {
        return location;
    }

    public int getPort() {
        return port;
    }

    public void lookAt(Vector3D position) throws IOException {
        double dx = location.x() - position.x();
        double dy = location.y() - position.y();
        double dz = location.z() - position.z();
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) + 90;
        double r = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (yaw < 0) yaw = 360 + yaw;
        float pitch = (float) (Math.sin(dy / r) / Math.PI * 180);
        rotatePlayer(yaw, pitch);
    }

    public boolean removeListener(ClientListener listener) {
        return listeners.remove(listener);
    }

    public void rotatePlayer(float yaw, float pitch) throws IOException {
        connection.sendPacket(new ClientRotatePlayerPacket(yaw, pitch));
    }

    public void sendCommand(String command) throws IOException {
        connection.sendPacket(new ClientChatCommandPacket(command));
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }

    public void setLocation(Vector3D newLocation) {
        location = Objects.requireNonNull(newLocation);

    }

    public void swingArm(Hand hand) throws IOException {
        connection.sendPacket(new ClientSwingArmPacket(hand));
    }

    public void useItem(BlockLocation location) throws IOException {
        lookAt(location.toVector3D());
        connection.sendPacket(new ClientUseItemOnPacket(random.nextInt(0, Integer.MAX_VALUE), location));
        swingArm(Hand.RIGHT);
    }
}
