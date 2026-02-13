package xiao.cbrkc.compat.killicon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.common.game.team.GameTeam;
import xiao.battleroyale.util.GameUtils;
import xiao.cbrkc.api.compat.killicon.HitMessage;
import xiao.cbrkc.api.compat.killicon.IKilliconNetworkHandler;
import xiao.cbrkc.api.compat.killicon.KillMessage;

import java.util.List;

public class KilliconMessageHandler {

    private static KilliconMessageHandler INSTANCE;
    private final IKilliconNetworkHandler killiconNetworkHandler;

    private KilliconMessageHandler(IKilliconNetworkHandler killiconNetworkHandler) {
        this.killiconNetworkHandler = killiconNetworkHandler;
    }

    public static void initialize(IKilliconNetworkHandler killiconNetworkHandler) {
        if (INSTANCE != null) {
            throw new IllegalStateException("KilliconMessageHandler already initialized");
        }
        INSTANCE = new KilliconMessageHandler(killiconNetworkHandler);
    }

    public static KilliconMessageHandler get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("not initialized. Call initialize() first.");
        }
        return INSTANCE;
    }

    public void sendMessageToPlayer(@NotNull ServerPlayer serverPlayer, HitMessage message) {
        killiconNetworkHandler.sendMessageToPlayer(serverPlayer, message);
    }
    public void sendMessageToPlayer(@NotNull ServerPlayer serverPlayer, KillMessage message) {
        killiconNetworkHandler.sendMessageToPlayer(serverPlayer, message);
    }

    public void sendMessageToAllPlayers(@NotNull ServerLevel serverLevel, KillMessage message) {
        List<ServerPlayer> players = serverLevel.players();
        for (ServerPlayer player : players) {
            killiconNetworkHandler.sendMessageToPlayer(player, message);
        }
    }

    public void sendMessageToGamePlayers(List<GamePlayer> gamePlayers, KillMessage message, @NotNull ServerLevel serverLevel) {
        for (GamePlayer gamePlayer : gamePlayers) {
            @Nullable ServerPlayer player = GameUtils.getServerPlayerOrNull(serverLevel, gamePlayer.getPlayerUUID());
            if (player == null) {
                continue;
            }
            killiconNetworkHandler.sendMessageToPlayer(player, message);
        }
    }

    public void sendMessageToTeam(GameTeam gameTeam, KillMessage message, @NotNull ServerLevel serverLevel) {
        for (GamePlayer gamePlayer : gameTeam.getTeamMembers()) {
            @Nullable ServerPlayer player = GameUtils.getServerPlayerOrNull(serverLevel, gamePlayer.getPlayerUUID());
            if (player == null) {
                continue;
            }
            killiconNetworkHandler.sendMessageToPlayer(player, message);
        }
    }
}
