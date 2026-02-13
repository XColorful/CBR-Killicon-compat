package xiao.cbrkc.api.compat.killicon;

public enum KillCategory {
    ICON("kill_icon"),
    SUBTITLE("subtitle");

    private final String name;

    KillCategory(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
