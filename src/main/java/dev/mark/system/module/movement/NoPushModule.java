package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.core.ClientMain;
import dev.mark.system.enums.Category;

public class NoPushModule extends Module {
   public NoPushModule() {
      super("NoPush", "Disables collision", Category.PLAYER);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onPush() {
      if (this.getClientPlayer() != null && ClientMain.getInstance().getModuleManager().<NoPushModule>getModule(NoPushModule.class).isEnabled()) {
         this.getClientPlayer().noClip = true;
      }
   }
}
