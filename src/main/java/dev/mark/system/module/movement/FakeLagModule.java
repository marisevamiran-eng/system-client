package dev.mark.system.module.movement;

import dev.mark.system.module.Module;
import dev.mark.system.module.player.PacketModule;

import dev.mark.system.enums.Category;

public class FakeLagModule extends PacketModule {
   public FakeLagModule() {
      super("FakeLag", "Delays packet sending", Category.MOVEMENT);
      this.addSettings(this.eu, this.showServerPosSetting);
   }

   @Override
   protected boolean shouldDelay() {
      return true;
   }
}
