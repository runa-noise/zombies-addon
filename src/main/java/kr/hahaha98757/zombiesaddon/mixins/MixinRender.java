package kr.hahaha98757.zombiesaddon.mixins;

import kr.hahaha98757.zombiesaddon.modules.PlayerVisibility;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public class MixinRender {
    @Inject(method = "doRenderShadowAndFire", at = @At("HEAD"), cancellable = true)
    private void cancelRendering(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, CallbackInfo ci) {
        if (!(entityIn instanceof EntityPlayer)) return;
        PlayerVisibility pv = PlayerVisibility.INSTANCE;
        if (pv.modeOf((EntityPlayer) entityIn) == PlayerVisibility.Mode.SEMI_PV && !pv.getInLast()) ci.cancel();
    }

    @Inject(method = "renderEntityOnFire", at = @At("HEAD"), cancellable = true)
    private void applySemiPv(Entity entity, double x, double y, double z, float partialTicks, CallbackInfo ci) {
        if (!(entity instanceof EntityPlayer)) return;
        PlayerVisibility pv = PlayerVisibility.INSTANCE;
        PlayerVisibility.Mode mode = pv.modeOf((EntityPlayer) entity);

        if (mode == PlayerVisibility.Mode.PV) ci.cancel();
        else if (mode == PlayerVisibility.Mode.SEMI_PV) {
            GlStateManager.enableBlend();
            GL14.glBlendColor(0f, 0f, 0f, pv.getAlpha((EntityPlayer) entity));
            GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
        }
    }

    @Inject(method = "renderEntityOnFire", at = @At("RETURN"))
    private void restoreSemiPv(Entity entity, double x, double y, double z, float partialTicks, CallbackInfo ci) {
        if (!(entity instanceof EntityPlayer)) return;
        PlayerVisibility pv = PlayerVisibility.INSTANCE;
        if (pv.modeOf((EntityPlayer) entity) == PlayerVisibility.Mode.SEMI_PV) {
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
        }
    }
}