package dev.mark.system.module.movement;

import dev.mark.system.module.Module;
import dev.mark.system.module.player.PacketModule;

import dev.mark.system.enums.Category;

public class BlinkModule extends PacketModule {
   public BlinkModule() {
      super("Blink", "Freezes player model while module is enabled", Category.MOVEMENT);
      this.addSettings(this.showServerPosSetting);
   }

   @Override
   protected boolean shouldDelay() {
      return true;
   }

   @Override
   public void onEndTick() {
   }
}
