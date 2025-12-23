package io.github.defective4.minecraft.nbsdj.protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.defective4.minecraft.nbsdj.NoteBlockBot;
import io.github.defective4.minecraft.nbsdj.protocol.packet.client.login.ClientLoginAcknowledgedPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginCompressionPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginSuccessPacket;

public class MinecraftPacketHandler {
    private final NoteBlockBot bot;
    private final MinecraftConnection connection;

    public MinecraftPacketHandler(NoteBlockBot bot, MinecraftConnection connection) {
        this.connection = connection;
        this.bot = bot;
    }

    public void handle(ClientboundPacket packet) {
        for (Method method : MinecraftPacketHandler.class.getDeclaredMethods())
            if (method.isAnnotationPresent(PacketHandler.class) && method.getParameterCount() == 1
                    && method.getParameterTypes()[0] == packet.getClass()) {
                        try {
                            method.invoke(this, packet);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new IllegalStateException(e);
                        }
                    }
    }

    @PacketHandler
    private void handleLoginCompression(ServerLoginCompressionPacket p) {
        connection.setCompressionThreshold(p.getThreshold());
    }

    @PacketHandler
    private void handleLoginSuccess(ServerLoginSuccessPacket p) throws IOException {
        bot.setGameProfile(p.getProfile());
        connection.sendPacket(new ClientLoginAcknowledgedPacket());
    }
}
