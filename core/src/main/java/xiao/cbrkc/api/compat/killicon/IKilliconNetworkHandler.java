package xiao.cbrkc.api.compat.killicon;

import net.minecraft.server.level.ServerPlayer;

public interface IKilliconNetworkHandler {

    void sendMessageToServer(KillMessage killMessage);

    void sendMessageToPlayer(ServerPlayer serverPlayer, HitMessage hitMessage);
    void sendMessageToPlayer(ServerPlayer serverPlayer, KillMessage killMessage);

    void sendMessageToAll(KillMessage killMessage);
}
