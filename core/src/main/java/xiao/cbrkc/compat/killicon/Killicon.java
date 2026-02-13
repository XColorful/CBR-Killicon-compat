package xiao.cbrkc.compat.killicon;

import net.minecraft.server.level.ServerPlayer;
import xiao.battleroyale.compat.AbstractCompatMod;
import xiao.cbrkc.CbrKilliconCompat;

public class Killicon extends AbstractCompatMod {

    @Override
    public String getModId() {
        return "gd656killicon";
    }

    private static class KilliconHolder {
        private static final Killicon INSTANCE = new Killicon();
    }

    public static Killicon get() {
        return KilliconHolder.INSTANCE;
    }

    private Killicon() {}

    @Override
    public void checkLoaded() {
        super.checkLoaded();
        if (isLoaded()) {
            KilliconMessageHandler.initialize(CbrKilliconCompat.getCompatApi().killiconNetworkHandler());
            registerGameEvent();
        }
    }

    /**
     * 抛出异常后父类会处理为未加载
     * 任一api变动即视为不兼容
     */
    @Override
    public void onModLoaded() throws Exception {
        // 1. 校验核心发包器类和方法 (NetworkHandler)
        Class<?> networkHandler = Class.forName("org.mods.gd656killicon.network.NetworkHandler");
        // 校验是否存在 sendToPlayer 方法，且参数类型正确
        // 注意：由于 KillIconPacket 也是外部类，反射时也需要通过 Class.forName 获取
        Class<?> killIconPacket = Class.forName("org.mods.gd656killicon.network.packet.KillIconPacket");
        Class<?> damageSoundPacket = Class.forName("org.mods.gd656killicon.network.packet.DamageSoundPacket");

        networkHandler.getMethod("sendToPlayer", Object.class, ServerPlayer.class);
        networkHandler.getMethod("sendToAll", Object.class);

        // 2. 校验 KillIconPacket 的构造函数
        // 校验最全的构造函数
        killIconPacket.getConstructor(
                String.class,  // category
                String.class,  // name
                int.class,     // killType
                int.class,     // comboCount
                int.class,     // victimId
                double.class,  // comboWindowSeconds
                boolean.class  // hasHelmet
        );

        // 3. 校验被 Mixin 拦截的目标类是否存在
        // 即使 Mixin 还没生效，反射校验能确保目标类在当前运行环境中确实属于这个包名
        Class.forName("org.mods.gd656killicon.server.event.ServerEventHandler");

        // 4. (可选) 校验连杀时间窗口的数据获取
        Class<?> serverData = Class.forName("org.mods.gd656killicon.server.data.ServerData");
        serverData.getMethod("get");
        serverData.getMethod("getComboWindowSeconds");
    }

    public static void registerGameEvent() {
        if (!get().isLoaded()) {
            return;
        }
        KilliconGameEventHandler.get().registerGameEventHandler();
    }

    public static void unregisterGameEvent() {
        if (!get().isLoaded()) {
            return;
        }
        KilliconGameEventHandler.get().unregisterGameEventHandler();
    }
}
