package kr.runanoise.zombiesaddon.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.stachel.zombiesutils.commands.SlaCommand;

@Pseudo
@Mixin(value = SlaCommand.class, remap = false)
public class MixinSlaCommand {
    @Inject(method = "func_71517_b", at = @At("HEAD"), cancellable = true)
    private void modifyCommandName(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("sla_zombiesutils");
    }

    @Inject(method = "func_71518_a", at = @At("HEAD"), cancellable = true)
    private void modifyCommandUsage(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("/sla_zombiesutils off\n/sla_zombiesutils offset [x] [x] [x]\n/sla_zombiesutils rotate\n/sla_zombiesutils mirror\n/sla_zombiesutils map <de|bb|aa|p>\n/sla_zombiesutils quick");
    }
}