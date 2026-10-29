package dev.mark.system.module.visual;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.SliderSetting;

public class PlayerScalerModule extends Module {
   private BooleanSetting proportionalSetting = new BooleanSetting("Proportional", "Scale equally on all axes", true);
   private SliderSetting sizeSetting = new SliderSetting("Size", "Proportional player size", 0.5, 0.1, 3.0, 0.05);
   private SliderSetting sizeXSetting = new SliderSetting("Size X", "X axis scale", 0.5, 0.1, 3.0, 0.05);
   private SliderSetting sizeYSetting = new SliderSetting("Size Y", "Y axis scale", 0.5, 0.1, 3.0, 0.05);
   private SliderSetting sizeZSetting = new SliderSetting("Size Z", "Z axis scale", 0.5, 0.1, 3.0, 0.05);

   public PlayerScalerModule() {
      super("PlayerScaler", "Changes other players' size", Category.RENDER);
      this.sizeSetting.setVisibilitySupplier(this.proportionalSetting::getValue);
      this.sizeXSetting.setVisibilitySupplier(() -> {
         return !this.proportionalSetting.getValue();
      });
      this.sizeYSetting.setVisibilitySupplier(() -> {
         return !this.proportionalSetting.getValue();
      });
      this.sizeZSetting.setVisibilitySupplier(() -> {
         return !this.proportionalSetting.getValue();
      });
      this.addSettings(this.proportionalSetting, this.sizeSetting, this.sizeXSetting, this.sizeYSetting, this.sizeZSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public BooleanSetting a() {
      return this.proportionalSetting;
   }

   public SliderSetting b() {
      return this.sizeSetting;
   }

   public SliderSetting c() {
      return this.sizeXSetting;
   }

   public SliderSetting d() {
      return this.sizeYSetting;
   }

   public SliderSetting e() {
      return this.sizeZSetting;
   }
}
