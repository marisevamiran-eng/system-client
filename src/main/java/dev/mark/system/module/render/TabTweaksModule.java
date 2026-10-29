package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.SliderSetting;

public class TabTweaksModule extends Module {
   private BooleanSetting highlightFriendSetting = new BooleanSetting("Highlight Friend", "Highlights friends in tab with different color", true);
   private BooleanSetting optimizationSetting = new BooleanSetting("Optimization", "Hides extra server info in tab for optimization", false);
   private BooleanSetting tabAnimationSetting = new BooleanSetting("Tab Animation", "Enables tab appear and update animation", true);
   private SliderSetting animationSpeedSetting = new SliderSetting("Animation Speed", "Tab animation playback speed", 2.2F, 1.0, 10.0, 0.5);
   private BooleanSetting showPingSetting = new BooleanSetting("Show Ping", "Shows numeric ping instead of icons", false);
   private BooleanSetting customColumnsSetting = new BooleanSetting("Custom Columns", "Use custom column count", false);
   private SliderSetting columnCountSetting = new SliderSetting("Column Count", "Number of columns in tab", 4.0, 1.0, 10.0, 1.0);

   public TabTweaksModule() {
      super("TabTweaks", "Tab utility", Category.VISUAL);
      this.animationSpeedSetting.setVisibilitySupplier(this.tabAnimationSetting::getValue);
      this.columnCountSetting.setVisibilitySupplier(this.customColumnsSetting::getValue);
      this.addSettings(
         this.highlightFriendSetting,
         this.tabAnimationSetting,
         this.animationSpeedSetting,
         this.optimizationSetting,
         this.showPingSetting,
         this.customColumnsSetting,
         this.columnCountSetting
      );
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public BooleanSetting a() {
      return this.highlightFriendSetting;
   }

   public BooleanSetting b() {
      return this.optimizationSetting;
   }

   public BooleanSetting c() {
      return this.tabAnimationSetting;
   }

   public SliderSetting d() {
      return this.animationSpeedSetting;
   }

   public BooleanSetting e() {
      return this.showPingSetting;
   }

   public BooleanSetting f() {
      return this.customColumnsSetting;
   }

   public SliderSetting g() {
      return this.columnCountSetting;
   }
}
