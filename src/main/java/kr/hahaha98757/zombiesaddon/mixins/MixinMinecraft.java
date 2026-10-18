package kr.hahaha98757.zombiesaddon.mixins;

import kr.hahaha98757.zombiesaddon.data.CustomPlaySoundLoader;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "refreshResources", at = @At("HEAD"))
    private void addCpsReloading(CallbackInfo ci) {
        CustomPlaySoundLoader.INSTANCE.loadFile();
    }
}