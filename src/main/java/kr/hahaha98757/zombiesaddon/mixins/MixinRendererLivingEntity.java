package kr.hahaha98757.zombiesaddon.mixins;

import kr.hahaha98757.zombiesaddon.modules.PlayerVisibility;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity {
    @Inject(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At("HEAD"), cancellable = true)
    private void canRenderName(EntityLivingBase entity, CallbackInfoReturnable<Boolean> cir) {
        int pass = PlayerVisibility.INSTANCE.getPass();

        if (pass == 1) cir.setReturnValue(false);
        else if (pass == 2) {
            GlStateManager.depthFunc(GL11.GL_LEQUAL);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
        }
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("HEAD"))
    private void doRenderPre(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity instanceof EntityPlayer) PlayerVisibility.INSTANCE.applySemiPv((EntityPlayer) entity);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("RETURN"))
    private void doRenderPost(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity instanceof EntityPlayer) PlayerVisibility.INSTANCE.restoreSemiPv();
    }
}