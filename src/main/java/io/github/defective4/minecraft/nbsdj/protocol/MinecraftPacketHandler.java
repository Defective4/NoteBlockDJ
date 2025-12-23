package io.github.defective4.minecraft.nbsdj.protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.defective4.minecraft.nbsdj.NoteBlockBot;
import io.github.defective4.minecraft.nbsdj.protocol.model.ChatVisibility;
import io.github.defective4.minecraft.nbsdj.protocol.model.ClientInformation;
import io.github.defective4.minecraft.nbsdj.protocol.model.GameState;
import io.github.defective4.minecraft.nbsdj.protocol.model.Hand;
import io.github.defective4.minecraft.nbsdj.protocol.model.ParticlesSettings;
import io.github.defective4.minecraft.nbsdj.protocol.packet.client.login.ClientLoginAcknowledgedPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.ClientboundPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.configuration.ServerConfigFinishedPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.configuration.ServerConfigKnownPacksPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginCompressionPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.login.ServerLoginSuccessPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.clientbound.play.ServerGameJoinPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.configuration.ClientConfigFinishedPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.configuration.ClientConfigInformationPacket;
import io.github.defective4.minecraft.nbsdj.protocol.packet.serverbound.configuration.ClientConfigKnownPacksPacket;

public class MinecraftPacketHandler {
    private final NoteBlockBot bot;
    private final MinecraftConnection connection;

    public MinecraftPacketHandler(NoteBlockBot bot, MinecraftConnection connection) {
        this.connection = connection;
        this.bot = bot;
    }

    @PacketHandler
    public void ackConfigurationFinished(ServerConfigFinishedPacket e) throws IOException {
        connection.sendPacket(new ClientConfigFinishedPacket());
        connection.setState(GameState.PLAY);
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
    public void handleGameJoin(ServerGameJoinPacket e) {
        bot.setEntityId(e.playerId());
        bot.getListeners().forEach(ls -> ls.gameJoined(e.playerId()));
    }

    @PacketHandler
    public void respondToServerKnownPacks(ServerConfigKnownPacksPacket e) throws IOException {
        connection.sendPacket(new ClientConfigKnownPacksPacket(e.packs()));
        connection.sendPacket(new ClientConfigInformationPacket(new ClientInformation("en_US", (byte) 4,
                ChatVisibility.FULL, true, Byte.MAX_VALUE, Hand.RIGHT, false, true, ParticlesSettings.MINIMAL)));
    }

    @PacketHandler
    private void ackLoginSuccess(ServerLoginSuccessPacket e) throws IOException {
        bot.setGameProfile(e.profile());
        bot.getListeners().forEach(l -> l.loggedIn(e.profile()));
        connection.setState(GameState.CONFIGURATION);
        connection.sendPacket(new ClientLoginAcknowledgedPacket());
    }

    @PacketHandler
    private void handleLoginCompression(ServerLoginCompressionPacket e) {
        connection.setCompressionThreshold(e.threshold());
    }
}
