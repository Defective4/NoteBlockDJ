package io.github.defective4.minecraft.nbsdj.protocol;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import io.github.defective4.minecraft.nbsdj.ClientListener;
import io.github.defective4.minecraft.nbsdj.NoteBlockBot;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketRegistry;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.handshake.ClientHandshakePacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.handshake.ClientIntention;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.login.ClientLoginPacket;

public class MinecraftConnection implements AutoCloseable {

    public static final int PROTOCOL = 773;
    private final NoteBlockBot bot;
    private int compressionThreshold = -1;
    private final MinecraftPacketHandler handler;

    private final String host;
    private final Inflater inflater = new Inflater(false);
    private DataInputStream input;

    private OutputStream output;

    private final int port;
    private final Socket socket = new Socket();

    private GameState state = GameState.HANDSHAKE;

    public MinecraftConnection(String host, int port, NoteBlockBot bot) {
        this.host = host;
        this.port = port;
        this.bot = bot;
        handler = new MinecraftPacketHandler(bot, this);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    public int getCompressionThreshold() {
        return compressionThreshold;
    }

    public GameState getState() {
        return state;
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public boolean isCompressionEnabled() {
        return compressionThreshold >= 0;
    }

    public void open(String username) throws IOException, DataFormatException {
        socket.connect(new InetSocketAddress(host, port));
        output = socket.getOutputStream();
        input = new DataInputStream(socket.getInputStream());

        sendPacket(new ClientHandshakePacket(PROTOCOL, host, port, ClientIntention.LOGIN));
        setState(GameState.LOGIN);
        sendPacket(new ClientLoginPacket(username,
                UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8))));
        while (!isClosed()) {
            int totalLength = DataTypes.readVarInt(input);
            byte packetId;
            byte[] data;

            if (isCompressionEnabled()) {
                byte[] raw = new byte[totalLength];
                input.readFully(raw);
                PacketDataInput tmpIn = new PacketDataInput(raw);

                int dataLen = tmpIn.readVarInt();

                if (dataLen > 0) {
                    raw = new byte[dataLen];
                    inflater.reset();
                    inflater.setInput(tmpIn.readFully());
                    inflater.inflate(raw);
                    packetId = raw[0];
                    data = new byte[raw.length - 1];
                    System.arraycopy(raw, 1, data, 0, data.length);
                } else {
                    packetId = tmpIn.readByte();
                    data = tmpIn.readFully();
                }

            } else {
                packetId = input.readByte();
                data = new byte[totalLength - 1];
                input.readFully(data);
            }

            Optional<ClientboundPacketFactory<?>> factory = ClientboundPacketRegistry.getPacketForId(state, packetId);
            if (factory.isPresent()) {
                ClientboundPacket packet = factory.get().createPacket(new PacketDataInput(data));
                handler.handle(packet);
            }
        }
    }

    public void sendPacket(ServerboundPacket packet) throws IOException {
        output.write(packet.getData(isCompressionEnabled()));
    }

    protected void setCompressionThreshold(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }

    protected void setState(GameState state) {
        Objects.requireNonNull(state);
        for (ClientListener ls : bot.getListeners()) ls.gameStateChanged(this.state, state);
        this.state = state;
    }

}
