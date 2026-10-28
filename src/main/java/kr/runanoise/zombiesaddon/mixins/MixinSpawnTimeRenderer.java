package kr.runanoise.zombiesaddon.mixins;

import com.seosean.showspawntime.features.spawntimes.SpawnTimeRenderer;
import kr.runanoise.zombiesaddon.config.ZAConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = SpawnTimeRenderer.class, remap = false)
public class MixinSpawnTimeRenderer {
    @Inject(method = "onRender", at = @At("HEAD"), cancellable = true)
    private void disableSpawnTime(CallbackInfo ci) {
        if (ZAConfig.INSTANCE.getGeneral().getEnableMod() && ZAConfig.INSTANCE.getOtherMods().getDisableSpawnTimeOfSst()) ci.cancel();
    }
}
