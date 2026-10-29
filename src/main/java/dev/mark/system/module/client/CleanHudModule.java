package dev.mark.system.module.client;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;

public class CleanHudModule extends Module {
   public CleanHudModule() {
      super("CleanHud", "Disables HUD render elements", Category.CLIENT);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }
}
