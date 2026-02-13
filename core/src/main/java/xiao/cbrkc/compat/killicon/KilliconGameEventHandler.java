package xiao.cbrkc.compat.killicon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.*;
import xiao.battleroyale.api.event.game.game.GamePlayerDamageFinishEvent;
import xiao.battleroyale.api.event.game.game.GamePlayerDeathFinishEvent;
import xiao.battleroyale.api.event.game.game.GamePlayerDownFinishEvent;
import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.util.GameUtils;
import xiao.cbrkc.CbrKilliconCompat;
import xiao.cbrkc.api.compat.killicon.*;

public class KilliconGameEventHandler implements ICustomEventHandler {

    private static class KilliconKillHandlerHolder {
        private static final KilliconGameEventHandler INSTANCE = new KilliconGameEventHandler();
    }

    public static KilliconGameEventHandler get() {
        return KilliconKillHandlerHolder.INSTANCE;
    }

    public void registerGameEventHandler() {
        ICustomEventRegister customEventRegister = BattleRoyale.getEventRegister();
        customEventRegister.register(get(), CustomEventType.GAME_PLAYER_DAMAGE_FINISH_EVENT);
        customEventRegister.register(get(), CustomEventType.GAME_PLAYER_DOWN_FINISH_EVENT);
        customEventRegister.register(get(), CustomEventType.GAME_PLAYER_DEATH_FINISH_EVENT);
    }
    public void unregisterGameEventHandler() {
        ICustomEventRegister customEventRegister = BattleRoyale.getEventRegister();
        customEventRegister.unregister(get(), CustomEventType.GAME_PLAYER_DAMAGE_FINISH_EVENT);
        customEventRegister.unregister(get(), CustomEventType.GAME_PLAYER_DOWN_FINISH_EVENT);
        customEventRegister.unregister(get(), CustomEventType.GAME_PLAYER_DEATH_FINISH_EVENT);
    }

    @Override
    public String getEventHandlerName() {
        return String.format("%s:KilliconKillHandler", CbrKilliconCompat.MOD_ID);
    }
    @Override
    public void handleEvent(CustomEventType customEventType, ICustomEvent event) {
        switch (customEventType) {
            case GAME_PLAYER_DAMAGE_FINISH_EVENT -> onHitPlayer((GamePlayerDamageFinishEvent) event);
            case GAME_PLAYER_DOWN_FINISH_EVENT -> onKnockPlayer((GamePlayerDownFinishEvent) event);
            case GAME_PLAYER_DEATH_FINISH_EVENT -> onEliminatePlayer((GamePlayerDeathFinishEvent) event);
            default -> onReceiveWrongEvent(customEventType);
        }
    }

    /**
     * {@link xiao.battleroyale.common.game.stats.StatsEventHandler onGamePlayerDamage}
     */
    public void onHitPlayer(GamePlayerDamageFinishEvent event) {
        IGameManager gameManager = event.getGameManager();
        @Nullable ServerLevel serverLevel = gameManager.getServerLevel();
        @Nullable ILivingDamageEvent livingDamageEvent = event.getLivingDamageEvent();
        if (serverLevel == null) {
            CbrKilliconCompat.LOGGER.warn("KilliconGameEventHandler: serverLevel is null, skipped onHitPlayer");
            return;
        }
        if (livingDamageEvent == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: livingDamageEvent is null, skipped onHitPlayer");
            return;
        }

        // 获取 ServerPlayer
        DamageSource damageSource = livingDamageEvent.getSource();
        @Nullable Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: attackerEntity is null, skipped onHitPlayer");
            return;
        }
        ServerPlayer attackerPlayer = GameUtils.getServerPlayerOrNull(serverLevel, attackerEntity.getUUID());
        if (attackerPlayer == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: attackerPlayer is null, skipped onHitPlayer");
            return;
        }

        KilliconMessageHandler messageHandler = KilliconMessageHandler.get();
        messageHandler.sendMessageToPlayer(attackerPlayer, new HitMessage());
    }

    /**
     * {@link xiao.battleroyale.common.game.stats.StatsEventHandler onGamePlayerDown}
     * 发包顺序 {@link org.mods.gd656killicon.server.event ServerEventHandler sendKillEffects}
     */
    public void onKnockPlayer(GamePlayerDownFinishEvent event) {
        // 击倒转淘汰的特殊判定
        GamePlayer gamePlayer = event.getGamePlayer();
        if (gamePlayer.isEliminated()) return;

        IGameManager gameManager = event.getGameManager();
        @Nullable ServerLevel serverLevel = gameManager.getServerLevel();
        if (serverLevel == null) {
            CbrKilliconCompat.LOGGER.warn("KilliconGameEventHandler: serverLevel is null, skipped onKnockPlayer");
            return;
        }
        @Nullable ILivingDeathEvent livingDeathEvent = event.getLivingDeathEvent();
        if (livingDeathEvent == null) {
            CbrKilliconCompat.LOGGER.warn("KilliconGameEventHandler: livingDeathEvent is null, skipped onKnockPlayer");
            return;
        }

        // 获取 ServerPlayer
        @Nullable ServerPlayer victim = GameUtils.getServerPlayerOrNull(serverLevel, gamePlayer.getPlayerUUID());
        if (victim == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: victim ServerPlayer is null, skipped onKnockPlayer");
            return;
        }
        DamageSource damageSource = livingDeathEvent.getSource();
        @Nullable Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: attackerEntity is null, skipped onKnockPlayer");
            return;
        }
        ServerPlayer attackerPlayer = GameUtils.getServerPlayerOrNull(serverLevel, attackerEntity.getUUID());
        if (attackerPlayer == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: attackerPlayer is null, skipped onKnockPlayer");
            return;
        }

        boolean hasHelmet = !attackerPlayer.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
        KilliconMessageHandler messageHandler = KilliconMessageHandler.get();
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.SCROLLING, KillType.NORMAL, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.CARD, KillType.NORMAL, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.CARD_BAR, KillType.NORMAL, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.BATTLEFIELD1, KillType.NORMAL, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.SUBTITLE, ComponentType.KILL_FEED, KillType.NORMAL, 1, victim, 5.0, hasHelmet));
    }

    /**
     * {@link xiao.battleroyale.common.game.stats.StatsEventHandler onGamePlayerDeath}
     */
    public void onEliminatePlayer(GamePlayerDeathFinishEvent event) {
        IGameManager gameManager = event.getGameManager();
        @Nullable ServerLevel serverLevel = gameManager.getServerLevel();
        if (serverLevel == null) {
            CbrKilliconCompat.LOGGER.warn("KilliconGameEventHandler: serverLevel is null, skipped onEliminatePlayer");
            return;
        }
        @Nullable ILivingDeathEvent livingDeathEvent = event.getLivingDeathEvent();
        if (livingDeathEvent == null) {
            CbrKilliconCompat.LOGGER.warn("KilliconGameEventHandler: livingDeathEvent is null, skipped onEliminatePlayer");
            return;
        }

        // 获取 ServerPlayer
        @Nullable ServerPlayer victim = GameUtils.getServerPlayerOrNull(serverLevel, event.getGamePlayer().getPlayerUUID());
        if (victim == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: victim ServerPlayer is null, skipped onEliminatePlayer");
            return;
        }
        DamageSource damageSource = livingDeathEvent.getSource();
        @Nullable Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: attackerEntity is null, skipped onEliminatePlayer");
            return;
        }
        ServerPlayer attackerPlayer = GameUtils.getServerPlayerOrNull(serverLevel, attackerEntity.getUUID());
        if (attackerPlayer == null) {
            CbrKilliconCompat.LOGGER.debug("KilliconGameEventHandler: attackerPlayer is null, skipped onEliminatePlayer");
            return;
        }

        boolean hasHelmet = !attackerPlayer.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
        KilliconMessageHandler messageHandler = KilliconMessageHandler.get();
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.SCROLLING, KillType.HEADSHOT, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.CARD, KillType.HEADSHOT, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.CARD_BAR, KillType.HEADSHOT, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.ICON, ComponentType.BATTLEFIELD1, KillType.HEADSHOT, 1, victim, 5.0, hasHelmet));
        messageHandler.sendMessageToPlayer(attackerPlayer, new KillMessage(KillCategory.SUBTITLE, ComponentType.KILL_FEED, KillType.HEADSHOT, 1, victim, 5.0, hasHelmet));
    }
}
