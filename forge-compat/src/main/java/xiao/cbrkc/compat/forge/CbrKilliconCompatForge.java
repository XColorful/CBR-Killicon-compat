package xiao.cbrkc.compat.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLLoader;
import xiao.cbrkc.CbrKilliconCompat;
import xiao.cbrkc.CbrKilliconCompat.CompatApi;
import net.minecraftforge.fml.common.Mod;
import xiao.battleroyale.api.common.McSide;
import xiao.cbrkc.compat.forge.compat.killicon.KilliconNetworkHandler;
import xiao.cbrkc.compat.forge.compat.killicon.KilliconUnregister;

@Mod(CbrKilliconCompat.MOD_ID)
public class CbrKilliconCompatForge {

    public static CompatApi compatApi;

    public CbrKilliconCompatForge() {
        CbrKilliconCompatForge.compatApi = new CompatApi(KilliconNetworkHandler.get(), KilliconUnregister.get());

        Dist dist = FMLLoader.getDist();
        McSide mcSide = dist.isClient() ? McSide.CLIENT : McSide.DEDICATED_SERVER;
        CbrKilliconCompat.init(mcSide,
                CbrKilliconCompatForge.compatApi);
    }
}
