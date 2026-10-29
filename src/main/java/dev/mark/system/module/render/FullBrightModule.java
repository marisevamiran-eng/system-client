package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FullBrightModule extends Module {
   public FullBrightModule() {
      super("FullBright", "Gives you night vision effect", Category.VISUAL);
   }

   @Override
   public void onEndTick() {
      try {
         if (this.getClient() != null && this.getClient().player != null) {
            this.getClient().player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
      try {
         if (this.getClient() != null && this.getClient().player != null) {
            this.getClient().player.removeStatusEffect(StatusEffects.NIGHT_VISION);
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }
}
