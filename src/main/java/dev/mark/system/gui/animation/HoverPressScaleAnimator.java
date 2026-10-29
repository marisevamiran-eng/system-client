package dev.mark.system.gui.animation;

import dev.mark.system.gui.enums.FourValueEnum;
public class HoverPressScaleAnimator extends AbstractAnimator {
   private static final float acu = 0.3F;
   private static final float agR = 0.15F;
   private static final float LU = 1.08F;
   private static final float JZ = 0.95F;
   private static final float in = 1.0F;
   private FourValueEnum VR = FourValueEnum.Lt;
   private float xX = 0.0F;
   private float amb = 1.0F;
   private float rq = 1.0F;
   private boolean pz = false;
   private boolean KS = false;

   public void a(boolean flag) {
      if (this.pz != flag && !this.KS) {
         this.pz = flag;
         this.c(flag ? FourValueEnum.BQ : FourValueEnum.Lt);
      }
   }

   public void b(boolean flag) {
      if (this.KS != flag) {
         this.KS = flag;
         this.c(flag ? FourValueEnum.tX : FourValueEnum.jB);
      }
   }

   private void c(FourValueEnum FourValueEnum) {
      this.VR = FourValueEnum;
      this.amb = this.rq;
      this.xX = 0.0F;
      this.wI = System.nanoTime();
   }

   @Override
   public void f() {
      this.xX = this.xX + this.getDeltaTime();
      float f;
      float f1;
      switch (this.VR) {
         case Lt:
            f = 1.0F;
            f1 = 0.3F;
            break;
         case BQ:
            f = 1.08F;
            f1 = 0.3F;
            break;
         case tX:
            f = 0.95F;
            f1 = 0.15F;
            break;
         case jB:
            f = this.pz ? 1.08F : 1.0F;
            f1 = 0.15F;
            break;
         default:
            return;
      }

      float f2 = Math.min(this.xX / f1, 1.0F);
      float f3 = this.c(f2);
      this.rq = this.amb + (f - this.amb) * f3;
      if (f2 >= 1.0F) {
         this.rq = f;
         if (this.VR == FourValueEnum.jB) {
            this.VR = this.pz ? FourValueEnum.BQ : FourValueEnum.Lt;
         }
      }
   }

   @Override
   public boolean g() {
      return this.VR != FourValueEnum.Lt || Math.abs(this.rq - 1.0F) > 0.001F;
   }

   @Override
   public void h() {
      this.VR = FourValueEnum.Lt;
      this.xX = 0.0F;
      this.rq = 1.0F;
      this.amb = 1.0F;
      this.pz = false;
      this.KS = false;
      this.wI = System.nanoTime();
   }

   public float d() {
      return this.rq;
   }
}
