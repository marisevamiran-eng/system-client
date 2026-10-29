package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.event.SpeedEvent;
import dev.mark.system.setting.BooleanSetting;

public class NoSlowModule extends Module {
   private final BooleanSetting onlyOnGroundSetting = new BooleanSetting("Only on Ground", "this", false);

   public NoSlowModule() {
      super("NoSlow", "Disables slowdown when using items", Category.MOVEMENT);
      this.addSettings(this.onlyOnGroundSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onSpeed(SpeedEvent speedevent) {
      if (this.getClient().player != null && !this.getClient().player.isGliding()) {
         if (!this.onlyOnGroundSetting.getValue() || this.getClient().player.isOnGround()) {
            if (this.getClient().player.getItemUseTime() % 2 == 0) {
               speedevent.setCancelled(true);
            }
         }
      }
   }
}
