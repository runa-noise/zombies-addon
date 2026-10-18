package kr.hahaha98757.zombiesaddon.mixins;

import kr.hahaha98757.zombiesaddon.modules.PlayerVisibility;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerCape.class)
public class MixinLayerCape {
    @Inject(method = "doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V", at = @At("HEAD"))
    private void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        PlayerVisibility.INSTANCE.applySemiPv(entitylivingbaseIn);
    }
}