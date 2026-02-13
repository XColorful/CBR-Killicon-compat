package xiao.cbrkc.api.compat.killicon;

public enum KillType {
    NORMAL(0),
    EXPLOSION(1),
    HEADSHOT(2),
    CRIT(3);

    private final int name;

    KillType(final int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }
}
