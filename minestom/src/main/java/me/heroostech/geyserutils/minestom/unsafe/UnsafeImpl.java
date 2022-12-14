package me.heroostech.geyserutils.minestom.unsafe;

import lombok.SneakyThrows;
import me.heroostech.geyserutils.unsafe.Unsafe;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class UnsafeImpl implements Unsafe {
    /**
     * Send a raw Bedrock packet to the given online Bedrock player.
     *
     * @param bedrockPlayer the uuid of the online Bedrock player
     * @param packetId      the id of the packet to send
     * @param packetData    the raw packet data
     */
    @SneakyThrows
    @Override
    public void sendPacket(UUID bedrockPlayer, int packetId, byte[] packetData) {
        Player player = MinecraftServer.getConnectionManager().getPlayer(bedrockPlayer);
        if(player == null) return;

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteStream);

        stream.writeInt(packetId);
        stream.writeObject(packetData);

        stream.close();

        player.sendPluginMessage("geyserutils:packet", byteStream.toByteArray());

        byteStream.close();
    }
}
