package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import net.minecraft.entity.LivingEntity;
import dev.mark.system.util.UnsafeFieldAccessor;

public class NoJumpDelayModule extends Module {
   private UnsafeFieldAccessor<Integer> VE;

   public NoJumpDelayModule() {
      super("NoJumpDelay", "Disables jump delay", Category.MOVEMENT);
   }

   @Override
   public void onEnable() {
      this.VE = new UnsafeFieldAccessor<Integer>(this.getPlayer(), LivingEntity.class, 91);
   }

   @Override
   public void onDisable() {
      this.VE = null;
   }

   @Override
   public void onEndTick() {
      if (this.getPlayer() != null && this.VE != null) {
         this.VE.setInt(this.getPlayer(), 0);
      }
   }
}
