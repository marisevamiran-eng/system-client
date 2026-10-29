package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.setting.SliderSetting;

public class AspectRatioModule extends Module {
   public static SliderSetting stretchStrengthSetting = new SliderSetting("Stretch Strength", "", 1.0, 0.1, 10.0, 0.1);

   public AspectRatioModule() {
      super("AspectRatio", "Allows stretching the screen", Category.VISUAL);
      this.addSetting(stretchStrengthSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public static SliderSetting a() {
      return stretchStrengthSetting;
   }
}
