package kr.runanoise.zombiesaddon.mixins;

import kr.runanoise.zombiesaddon.config.ZAConfig;
import kr.runanoise.zombiesaddon.modules.PlayerVisibility;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer {
    @Shadow
    public abstract void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks);

    @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("HEAD"), cancellable = true)
    private void applyPv(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        PlayerVisibility pv = PlayerVisibility.INSTANCE;
        if (pv.getPass() != 0) return;

        PlayerVisibility.Mode mode = pv.modeOf(entity);
        if (mode == PlayerVisibility.Mode.NONE) return;

        if (mode == PlayerVisibility.Mode.PV || !pv.getInLast()) {
            ci.cancel();
            return;
        }

        try {
            if (!ZAConfig.INSTANCE.getPv().getFastSemiPv()) {
                pv.setPass(1);
                GlStateManager.colorMask(false, false, false, false);
                doRender(entity, x, y, z, entityYaw, partialTicks);
                GlStateManager.colorMask(true, true, true, true);
            }

            pv.setPass(2);
            doRender(entity, x, y, z, entityYaw, partialTicks);
        } finally {
            pv.setPass(0);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(GL11.GL_LEQUAL);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        ci.cancel();
    }
}