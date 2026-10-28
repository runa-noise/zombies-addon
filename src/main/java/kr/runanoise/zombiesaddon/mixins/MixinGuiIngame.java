package kr.runanoise.zombiesaddon.mixins;

import kr.runanoise.zombiesaddon.modules.BetterZombiesLeft;
import kr.runanoise.zombiesaddon.modules.LastWeapons;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {

    @Inject(method = "renderHotbarItem", at = @At("HEAD"), cancellable = true)
    private void cancelHotbarRendering(int index, int xPos, int yPos, float partialTicks, EntityPlayer player, CallbackInfo ci) {
        if (!LastWeapons.INSTANCE.isWork()) return;
        ci.cancel();
    }

    @Redirect(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/scoreboard/ScorePlayerTeam;formatPlayerName(Lnet/minecraft/scoreboard/Team;Ljava/lang/String;)Ljava/lang/String;"))
    private String modifyZombiesLeftText(Team team, String string) {
        return BetterZombiesLeft.INSTANCE.modifyFormattedName(ScorePlayerTeam.formatPlayerName(team, string));
    }
}