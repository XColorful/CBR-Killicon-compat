package xiao.cbrkc.compat.forge.init;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import xiao.cbrkc.CbrKilliconCompat;
import xiao.cbrkc.init.CompatInit;

@Mod.EventBusSubscriber(modid = CbrKilliconCompat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeCompatInit {

    private static final CompatInit COMPAT_INIT = CompatInit.get();

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        COMPAT_INIT.onLoadComplete();
    }
}
