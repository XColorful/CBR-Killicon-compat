package xiao.cbrkc.compat.forge.mixin.killicon;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import org.mods.gd656killicon.server.event.ServerEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerEventHandler.class)
public class ServerEventHandlerMixin {

    // 1. 掐掉伤害逻辑：禁掉音效和内部伤害记录
    @Inject(method = "onDamage", at = @At("HEAD"), cancellable = true)
    private static void cancelDamage(LivingDamageEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    // 2. 掐掉死亡逻辑：彻底禁掉击杀判断和发包
    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    private static void cancelDeath(LivingDeathEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    // 3. 掐掉 Tick：停止不必要的后台逻辑计算
    @Inject(method = "onTick", at = @At("HEAD"), cancellable = true)
    private static void cancelTick(TickEvent.ServerTickEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    // 4. 掐掉切换物品逻辑：快速切枪判断
    @Inject(method = "onItemSwitch", at = @At("HEAD"), cancellable = true)
    private static void cancelItemSwitch(LivingEquipmentChangeEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}