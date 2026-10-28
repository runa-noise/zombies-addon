package kr.runanoise.zombiesaddon.mixins;

import kr.runanoise.zombiesaddon.modules.PlayerVisibility;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public class MixinLayerArmorBase {
    @Inject(method = "renderGlint", at = @At("RETURN"))
    private void reapplySemiPv(EntityLivingBase entitylivingbaseIn, ModelBase modelbaseIn, float p_177183_3_, float p_177183_4_, float partialTicks, float p_177183_6_, float p_177183_7_, float p_177183_8_, float scale, CallbackInfo ci) {
        if (entitylivingbaseIn instanceof EntityPlayer)
            PlayerVisibility.INSTANCE.applySemiPv((EntityPlayer) entitylivingbaseIn);
    }
}