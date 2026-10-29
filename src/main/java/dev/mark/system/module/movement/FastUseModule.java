package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import net.minecraft.client.MinecraftClient;
import dev.mark.system.util.UnsafeFieldAccessor;

public class FastUseModule extends Module {
   private UnsafeFieldAccessor<Integer> aI;

   public FastUseModule() {
      super("FastUse", "Disables right-click delay", Category.PLAYER);
   }

   @Override
   public void onEnable() {
      this.aI = new UnsafeFieldAccessor<Integer>(this.getClient(), MinecraftClient.class, 90);
   }

   @Override
   public void onDisable() {
      this.aI = null;
   }

   @Override
   public void onEndTick() {
      if (this.getPlayer() != null && this.aI != null) {
         this.aI.setInt(0);
      }
   }
}
