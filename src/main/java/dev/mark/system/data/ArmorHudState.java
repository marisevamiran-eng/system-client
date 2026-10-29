package dev.mark.system.data;

import dev.mark.system.gui.animation.ProgressAnimation;
import net.minecraft.item.ItemStack;
import dev.mark.system.gui.animation.HoverStateAnimator;

public class ArmorHudState {
   public ProgressAnimation abN;
   public HoverStateAnimator[] anr;
   private boolean oS = false;

   public ArmorHudState() {
      this.abN = new ProgressAnimation();
      this.anr = new HoverStateAnimator[4];

      for (int i = 0; i < 4; i++) {
         this.anr[i] = new HoverStateAnimator();
         this.anr[i].a();
      }
   }

   public void a(ItemStack[] aitemstack, boolean flag) {
      if (!this.oS) {
         this.abN.a();
         this.oS = true;
      }

      for (int i = 0; i < 4; i++) {
         this.anr[i].e(!aitemstack[i].isEmpty());
         this.anr[i].b();
      }

      this.abN.f(flag);
      this.abN.b();
   }
}
