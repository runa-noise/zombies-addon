package kr.runanoise.zombiesaddon.mixins;

import kr.runanoise.zombiesaddon.modules.Patcher;
import kr.runanoise.zombiesaddon.utils.MixinToolsKt;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetHandlerPlayClient.class, priority = 900)
public class MixinNetHandlerPlayClient {
    @ModifyVariable(method = "handleSoundEffect", at = @At("HEAD"), argsOnly = true)
    private S29PacketSoundEffect fixRifleSound(S29PacketSoundEffect packet) {
        return Patcher.INSTANCE.fixRifleSound(packet);
    }

    @Inject(method = "handleSoundEffect", at = @At("HEAD"))
    private void onSound(S29PacketSoundEffect packetIn, CallbackInfo ci) {
        MixinToolsKt.onSound(packetIn);
    }

    @Inject(method = "handleTitle" , at = @At("HEAD"))
    private void onTitle(S45PacketTitle packetIn, CallbackInfo ci) {
        MixinToolsKt.onTitle(packetIn);
    }

    @Inject(method = "handleConfirmTransaction", at = @At("HEAD"))
    private void onInventoryPacket(S32PacketConfirmTransaction packetIn, CallbackInfo ci) {
        MixinToolsKt.onInventoryPacket(packetIn);
    }

    @Inject(method = "handleTimeUpdate", at = @At("HEAD"))
    private void onTimeUpdate(S03PacketTimeUpdate packetIn, CallbackInfo ci) {
        MixinToolsKt.onTimeUpdate(packetIn);
    }
}