package net.fabricmc.example.mixin;


import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @ModifyVariable(method = "sendImmediately", at = @At("HEAD"), ordinal = 0)
    private Packet<?> modifyPacket(Packet<?> packet) {
        if (packet instanceof PlayerMoveC2SPacket) {
            System.out.println("Editing packet");

            PlayerMoveC2SPacket playerMoveC2SPacket = (PlayerMoveC2SPacket) packet;

            return new PlayerMoveC2SPacket.Full(
                    Math.round(playerMoveC2SPacket.getX(0) * 100.) / 100.,
                    Math.round(playerMoveC2SPacket.getY(0) * 100.) / 100.,
                    Math.round(playerMoveC2SPacket.getZ(0) * 100.) / 100.,
                    Math.round(playerMoveC2SPacket.getYaw(0) * 100f) / 100f,
                    Math.round(playerMoveC2SPacket.getPitch(0) * 100f) / 100f,
                    playerMoveC2SPacket.isOnGround()
            );
        }
        return packet;
    }
}
