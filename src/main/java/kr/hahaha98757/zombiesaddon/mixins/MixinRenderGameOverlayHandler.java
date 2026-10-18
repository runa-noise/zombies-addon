package kr.hahaha98757.zombiesaddon.mixins;

import kr.hahaha98757.zombiesaddon.config.ZAConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.stachel.zombiesutils.handlers.RenderGameOverlayHandler;

@Pseudo
@Mixin(value = RenderGameOverlayHandler.class, remap = false)
public class MixinRenderGameOverlayHandler {
    @Inject(method = "renderTime", at = @At("HEAD"), cancellable = true)
    private void disableTimer(CallbackInfo ci) {
        if (ZAConfig.INSTANCE.getEnableMod() && ZAConfig.INSTANCE.getDisableTimerOfZombiesUtils()) ci.cancel();
    }
}