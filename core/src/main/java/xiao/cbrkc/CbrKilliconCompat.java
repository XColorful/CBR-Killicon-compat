package xiao.cbrkc;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import xiao.battleroyale.api.common.McSide;
import xiao.cbrkc.api.compat.killicon.IKilliconNetworkHandler;
import xiao.cbrkc.api.compat.killicon.IModLogicUnregister;

public class CbrKilliconCompat {
    public static final String MOD_ID = "cbrkc";
    public static final String MOD_NAME_SHORT = "cbrkc";
    public static final Logger LOGGER = LogUtils.getLogger();

    protected static boolean initialized;
    protected static McSide mcSide = McSide.CLIENT;
    public record CompatApi(IKilliconNetworkHandler killiconNetworkHandler, IModLogicUnregister modLogicUnregister) {}
    private static CompatApi compatApi;

    public static void init(McSide mcSide,
                            CompatApi compatApi) {
        if (initialized) return;

        CbrKilliconCompat.mcSide = mcSide;

        if (mcSide == McSide.CLIENT) {
            ;
        }

        CbrKilliconCompat.compatApi = compatApi;

        initialized = true;
    }

    public static McSide getMcSide() {
        return mcSide;
    }
    public static CompatApi getCompatApi() {
        if (compatApi == null) {
            throw new IllegalStateException("Compat api has not initialized. Call init() first.");
        }
        return compatApi;
    }
}
