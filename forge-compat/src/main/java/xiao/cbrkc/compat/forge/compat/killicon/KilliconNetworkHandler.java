package xiao.cbrkc.compat.forge.compat.killicon;

import net.minecraft.server.level.ServerPlayer;
import org.mods.gd656killicon.network.NetworkHandler;
import org.mods.gd656killicon.network.packet.DamageSoundPacket;
import org.mods.gd656killicon.network.packet.KillIconPacket;
import xiao.cbrkc.api.compat.killicon.HitMessage;
import xiao.cbrkc.api.compat.killicon.IKilliconNetworkHandler;
import xiao.cbrkc.api.compat.killicon.KillMessage;

public class KilliconNetworkHandler implements IKilliconNetworkHandler {

    private static class KilliconNetworkHandlerHolder {
        private static final KilliconNetworkHandler INSTANCE = new KilliconNetworkHandler();
    }

    public static KilliconNetworkHandler get() {
        return KilliconNetworkHandlerHolder.INSTANCE;
    }

    private KilliconNetworkHandler() {}

    @Override
    public void sendMessageToServer(KillMessage killMessage) {
        NetworkHandler.sendToServer(toPacket(killMessage));
    }

    @Override
    public void sendMessageToPlayer(ServerPlayer serverPlayer, HitMessage hitMessage) {
        NetworkHandler.sendToPlayer(toPacket(hitMessage), serverPlayer);
    }
    @Override
    public void sendMessageToPlayer(ServerPlayer serverPlayer, KillMessage killMessage) {
        NetworkHandler.sendToPlayer(toPacket(killMessage), serverPlayer);
    }

    @Override
    public void sendMessageToAll(KillMessage killMessage) {
        NetworkHandler.sendToAll(toPacket(killMessage));
    }

    public KillIconPacket toPacket(KillMessage killMessage) {
        return new KillIconPacket(killMessage.getCategory(),
                killMessage.getComponentType(),
                killMessage.getKillType(),
                killMessage.getComboCount(),
                killMessage.getVictimId(),
                killMessage.getComboWindow(),
                killMessage.hasHelmet()
        );
    }
    public DamageSoundPacket toPacket(HitMessage hitMessage) {
        return new DamageSoundPacket();
    }
}
