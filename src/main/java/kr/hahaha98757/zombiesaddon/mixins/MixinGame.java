package kr.hahaha98757.zombiesaddon.mixins;

import kr.hahaha98757.zombiesaddon.config.ZAConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.stachel.zombiesutils.timer.Game;

@Pseudo
@Mixin(value = Game.class, remap = false)
public class MixinGame {
    @Inject(method = "record", at = @At("HEAD"), cancellable = true)
    private void disableRecorder(CallbackInfo ci) {
        if (ZAConfig.INSTANCE.getRecorder().getEnable()) ci.cancel();
    }

}