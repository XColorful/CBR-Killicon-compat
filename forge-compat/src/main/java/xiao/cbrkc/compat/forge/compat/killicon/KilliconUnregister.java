package xiao.cbrkc.compat.forge.compat.killicon;

import net.minecraftforge.common.MinecraftForge;
import xiao.cbrkc.CbrKilliconCompat;
import xiao.cbrkc.api.compat.killicon.IModLogicUnregister;

public class KilliconUnregister implements IModLogicUnregister {

    private static class KilliconUnregisterHolder {
        private static final KilliconUnregister INSTANCE = new KilliconUnregister();
    }

    public static KilliconUnregister get() {
        return KilliconUnregisterHolder.INSTANCE;
    }

    private KilliconUnregister() {}

    public void unregisterModLogic() throws Exception {
        try {
            Class<?> serverEventHandlerClass = Class.forName("org.mods.gd656killicon.server.event.ServerEventHandler");
            MinecraftForge.EVENT_BUS.unregister(serverEventHandlerClass);

            CbrKilliconCompat.LOGGER.debug("CbrKilliconCompat: Unregistered ServerEventHandler");
        } catch (Exception e) {
            CbrKilliconCompat.LOGGER.debug("CbrKilliconCompat: Failed to rnregistered ServerEventHandler, {}", e.getMessage());
        }
    }
}
