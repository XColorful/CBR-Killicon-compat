package xiao.cbrkc.api.compat.killicon;

import net.minecraft.world.entity.Entity;

public class KillMessage {

    public final KillCategory messageCategory;
    public final ComponentType componentType;
    public final KillType killType;
    public final int killTotal;
    public final Entity displayEntity; // 显示头像的实体
    public final double displayTimeSeconds;
    public final boolean hasHelmet;

    public KillMessage(KillCategory messageCategory, ComponentType componentType, KillType killType, Entity displayEntity) {
        this(messageCategory, componentType, killType, 0, displayEntity, -1.0, false);
    }

    public KillMessage(KillCategory messageCategory, ComponentType componentType, KillType killType, int killTotal, Entity displayEntity) {
        this(messageCategory, componentType, killType, killTotal, displayEntity, -1.0, false);
    }

    public KillMessage(KillCategory messageCategory, ComponentType componentType, KillType killType, int killTotal, Entity displayEntity, double displayTimeSeconds) {
        this(messageCategory, componentType, killType, killTotal, displayEntity, displayTimeSeconds, false);
    }

    public KillMessage(KillCategory messageCategory, ComponentType componentType, KillType killType, int killTotal, Entity displayEntity, double displayTimeSeconds, boolean hasHelmet) {
        this.messageCategory = messageCategory;
        this.componentType = componentType;
        this.killType = killType;
        this.killTotal = killTotal;
        this.displayEntity = displayEntity;
        this.displayTimeSeconds = displayTimeSeconds;
        this.hasHelmet = hasHelmet;
    }

    public String getCategory() {
        return this.messageCategory.getName();
    }

    @Deprecated public String getName() {
        return getComponentType();
    }
    public String getComponentType() {
        return this.componentType.getName();
    }

    public int getKillType() {
        return this.killType.getName();
    }

    public int getComboCount() {
        return this.killTotal;
    }

    public int getVictimId() {
        return this.displayEntity.getId();
    }

    public double getComboWindow() {
        return this.displayTimeSeconds;
    }

    public boolean hasHelmet() {
        return this.hasHelmet;
    }
}
