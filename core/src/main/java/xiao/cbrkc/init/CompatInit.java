package xiao.cbrkc.init;

import xiao.battleroyale.api.init.ICompatInit;
import xiao.cbrkc.compat.killicon.Killicon;

public class CompatInit implements ICompatInit {

    private static final CompatInit INSTANCE = new CompatInit();

    public static CompatInit get() {
        return INSTANCE;
    }

    private CompatInit() {}

    @Override
    public void onLoadComplete() {
        Killicon.get().checkLoaded();
    }
}
