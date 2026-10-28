package kr.runanoise.zombiesaddon.mixins;

import com.seosean.showspawntime.config.MainConfiguration;
import com.seosean.showspawntime.features.frcooldown.FastReviveCoolDown.RenderType;
import kr.runanoise.zombiesaddon.config.ZAConfig;
import kr.runanoise.zombiesaddon.utils.ToolsKt;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.seosean.showspawntime.config.MainConfiguration.FastReviveCoolDownRenderType;
import static com.seosean.showspawntime.config.MainConfiguration.config;

@Pseudo
@Mixin(value = MainConfiguration.class, remap = false)
public class MixinMainConfiguration {

    @Inject(method = "ConfigLoad", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/config/Configuration;save()V"))
    private void beforeSave(CallbackInfo ci) {
        try {
            if (ZAConfig.INSTANCE.getGeneral().getSpeedrunMode()) {
                Property propertyPowerupAlertToggle = config.get(Configuration.CATEGORY_GENERAL, "Powerup Alert", true, "Remind you when this is a powerup-round. Start counting down when a powerup spawns");
                propertyPowerupAlertToggle.set(false);
                MainConfiguration.PowerupAlertToggle = false;
                MainConfiguration.powerupRelated.put(propertyPowerupAlertToggle.getName(), new ConfigElement(propertyPowerupAlertToggle));

                Property propertyPowerupCountDown = config.get(Configuration.CATEGORY_GENERAL, "Powerup Count Down", true, "Show the remaining time of powerups on expiration.");
                propertyPowerupCountDown.set(false);
                MainConfiguration.PowerupCountDown = false;
                MainConfiguration.powerupRelated.put(propertyPowerupCountDown.getName(), new ConfigElement(propertyPowerupCountDown));

                Property propertyPlayerHealthNotice = config.get(Configuration.CATEGORY_GENERAL, "Player Health Notice", true, "Enhance the Sidebar which shows the health of players.");
                propertyPlayerHealthNotice.set(false);
                MainConfiguration.PlayerHealthNotice = false;
                MainConfiguration.qolRelated.put(propertyPlayerHealthNotice.getName(), new ConfigElement(propertyPlayerHealthNotice));

                Property propertyFastReviveCoolDown = config.get(Configuration.CATEGORY_GENERAL, "Fast Revive Cool Down", "BEHIND", "Enhance the Sidebar which shows the cool down of fast revive for each player.", FastReviveCoolDownRenderType);
                propertyFastReviveCoolDown.set("OFF");
                MainConfiguration.FastReviveCoolDown = RenderType.OFF;
                MainConfiguration.qolRelated.put(propertyFastReviveCoolDown.getName(), new ConfigElement(propertyFastReviveCoolDown));
            }
        } catch (Throwable ignored) {}

        try {
            if (ZAConfig.INSTANCE.getModules().getEnableBetterZombiesLeft()) {
                Property propertyWave3LeftNotice = config.get(Configuration.CATEGORY_GENERAL, "Wave 3rd Left Notice", true, "Enhance the Sidebar which shows the amount of zombies in wave 3rd in DE/BB.");
                propertyWave3LeftNotice.set(false);
                MainConfiguration.Wave3LeftNotice = false;
                MainConfiguration.qolRelated.put(propertyWave3LeftNotice.getName(), new ConfigElement(propertyWave3LeftNotice));
            }
        } catch (Throwable ignored) {}
    }

    @Inject(method = "ConfigLoad", at = @At("TAIL"))
    private void afterSave(CallbackInfo ci) {
        try {
            if (ZAConfig.INSTANCE.getGeneral().getEnableMod())
                ToolsKt.addTranslatedChat("zombiesaddon.messages.disableUnlegitSst");
        } catch (Throwable ignored) {}

        try {
            if (ZAConfig.INSTANCE.getModules().getEnableBetterZombiesLeft())
                ToolsKt.addTranslatedChat("zombiesaddon.messages.disableWave3LeftNotice");
        } catch (Throwable ignored) {}
    }
}