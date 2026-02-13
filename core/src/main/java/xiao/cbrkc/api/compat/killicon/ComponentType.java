package xiao.cbrkc.api.compat.killicon;

public enum ComponentType {
    SCROLLING("scrolling"),
    COMBO("combo"),
    CARD("card"),
    CARD_BAR("card_bar"),
    BATTLEFIELD1("battlefield1"),
    KILL_FEED("kill_feed");

    private final String name;

    ComponentType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
