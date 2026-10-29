package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import net.minecraft.entity.effect.StatusEffects;
import dev.mark.system.setting.BooleanSetting;

public class NoRenderModule extends Module {
   public BooleanSetting fireSetting = new BooleanSetting("Fire", "Disables fire overlay on screen", true);
   public BooleanSetting scoreboardSetting = new BooleanSetting("Scoreboard", "Disables scoreboard display on the right", true);
   public BooleanSetting hurtShakeSetting = new BooleanSetting("Hurt Shake", "Disables damage shake", true);
   public BooleanSetting runShakeSetting = new BooleanSetting("Run Shake", "Disables sprint shake", true);
   public BooleanSetting wardenDarknessSetting = new BooleanSetting("Warden Darkness", "Disables warden darkness effect", true);
   public BooleanSetting nauseaSetting = new BooleanSetting("Nausea", "Disables nausea effect", true);
   public BooleanSetting attackIndicatorSetting = new BooleanSetting("Attack Indicator", "Disables attack indicator", true);
   public BooleanSetting effectsSetting = new BooleanSetting("Effects", "Disables status effect bar display", true);
   public BooleanSetting bossBarSetting = new BooleanSetting("Boss Bar", "Disables boss bar display", true);
   public BooleanSetting bubblesSetting = new BooleanSetting("Bubbles", "Disables bubble display", true);
   public BooleanSetting glowSetting = new BooleanSetting("Glow", "Disables glow effect on players", false);
   public BooleanSetting rainSetting = new BooleanSetting("Rain", "Disables visual rain effect", false);
   public BooleanSetting vignetteSetting = new BooleanSetting("Vignette", "Disables visual vignette effect", false);
   public BooleanSetting particlesSetting = new BooleanSetting("Explosion Particles", "Disables all particles", false);
   public BooleanSetting witherHeartsSetting = new BooleanSetting("Wither Hearts", "Black hearts as normal", false);

   public NoRenderModule() {
      super("NoRender", "Disables unwanted render elements", Category.VISUAL);
      this.addSettings(
         this.fireSetting,
         this.scoreboardSetting,
         this.hurtShakeSetting,
         this.runShakeSetting,
         this.wardenDarknessSetting,
         this.nauseaSetting,
         this.attackIndicatorSetting,
         this.effectsSetting,
         this.bossBarSetting,
         this.bubblesSetting,
         this.glowSetting,
         this.rainSetting,
         this.vignetteSetting,
         this.particlesSetting,
         this.witherHeartsSetting
      );
   }

   @Override
   public void onRenderHand() {
      if (this.isEnabled() && this.getClientPlayer() != null) {
         if (this.fireSetting.getValue()) {
            this.getClientPlayer().setOnFire(false);
         }

         if (this.rainSetting.getValue() && this.getWorld() != null) {
            this.getWorld().setRainGradient(0.0F);
            this.getWorld().setThunderGradient(0.0F);
         }

         if (this.wardenDarknessSetting.getValue() && this.getClientPlayer().hasStatusEffect(StatusEffects.DARKNESS)) {
            this.getClientPlayer().removeStatusEffect(StatusEffects.DARKNESS);
         }
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }
}
