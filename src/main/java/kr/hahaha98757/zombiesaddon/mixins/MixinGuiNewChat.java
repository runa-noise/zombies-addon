package kr.hahaha98757.zombiesaddon.mixins;

import kr.hahaha98757.zombiesaddon.events.ClientChatPrintedEvent;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {
    @Inject(method = "printChatMessage", at = @At("HEAD"), cancellable = true)
    public void postClientChatPrintedEvent(IChatComponent chatComponent, CallbackInfo ci) {
        final ClientChatPrintedEvent event = new ClientChatPrintedEvent(chatComponent);
        if (MinecraftForge.EVENT_BUS.post(event)) ci.cancel();
    }
}