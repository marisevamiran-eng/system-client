package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.setting.ActionKeySetting;
import dev.mark.system.setting.SliderSetting;

public class EmergencyStopModule extends Module {
   private ActionKeySetting stopKeySetting = new ActionKeySetting("Stop Key", "Key to stop", 90, this::a);
   private SliderSetting changeYSetting = new SliderSetting("Change Y", "Change Y (Better not to change)", 0.0, -5.0, 5.0, 1.0);

   public EmergencyStopModule() {
      super("EmergencyStop", "Stops you on keybind.", Category.MOVEMENT);
      this.addSettings(this.stopKeySetting, this.changeYSetting);
   }

   private void a() {
      if (!this.isNotInWorld()) {
         this.getPlayer().setVelocity(0.0, this.changeYSetting.getValue(), 0.0);
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }
}
