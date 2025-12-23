package io.github.defective4.minecraft.nbsdj.protocol;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import io.github.defective4.minecraft.nbsdj.NoteBlockBot;
import io.github.defective4.minecraft.nbsdj.protocol.packet.ServerboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.client.handshake.ClientHandshakePacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.client.handshake.ClientIntention;
import io.github.defective4.minecraft.nbsdj.protocol.packet.client.login.ClientLoginPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketFactory;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacketRegistry;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.PacketDataInput;

public class MinecraftConnection implements AutoCloseable {

    public static final int PROTOCOL = 773;
    private final NoteBlockBot bot;
    private int compressionThreshold = -1;
    private final MinecraftPacketHandler handler;

    private final String host;
    private DataInputStream input;
    private OutputStream output;

    private final int port;

    private final Socket socket = new Socket();

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

    public boolean isClosed() {
        return socket.isClosed();
    }

    public boolean isCompressionEnabled() {
        return compressionThreshold >= 0;
    }

    public void open(String username) throws IOException {
        socket.connect(new InetSocketAddress(host, port));
        output = socket.getOutputStream();
        input = new DataInputStream(socket.getInputStream());

        sendPacket(new ClientHandshakePacket(PROTOCOL, host, port, ClientIntention.LOGIN));
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
                    data = new byte[0];
                    packetId = -1;
                } else {
                    packetId = tmpIn.readByte();
                    data = tmpIn.readFully();
                }

            } else {
                packetId = input.readByte();
                data = new byte[totalLength - 1];
                input.readFully(data);
            }

            if(packetId == -1) {
                System.out.println(1);
            }

            ClientboundPacketFactory<?> factory = ClientboundPacketRegistry.getPacketForId(packetId);
            if (factory != null) {
                ClientboundPacket packet = factory.createPacket(new PacketDataInput(data));

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

}
