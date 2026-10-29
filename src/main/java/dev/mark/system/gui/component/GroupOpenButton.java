package dev.mark.system.gui.component;

import dev.mark.system.core.SoundManager;
import dev.mark.system.enums.SoundType;

public class GroupOpenButton extends IconButton {
   public GroupOpenButton(float f, float f1, Runnable runnable) {
      super(f, f1, 20.0F, "L", 15.0F, 0.0F, 4.0F, runnable);
   }

   @Override
   protected boolean b(double d0, double d1, int i) {
      if (i == 0 && this.sY != null) {
         this.eV.b(true);
         SoundManager.getInstance().c(SoundType.GROUP_OPEN);
         this.sY.run();
         return true;
      } else {
         return false;
      }
   }
}
