package dev.mark.system.gui.animation;

import dev.mark.system.gui.enums.ThreeStateEnum;
public class FadeAnimation extends AbstractAnimator {
   private static final float PL = 30.0F;
   private static final float agL = 0.2F;
   private ThreeStateEnum th = ThreeStateEnum.MT;
   private float aom = 0.0F;
   private float CG = 0.0F;
   private float HH = 0.0F;
   private Runnable aiF;

   @Override
   public void a() {
      super.a();
      this.h();
   }

   public void b() {
      this.th = ThreeStateEnum.IH;
      this.aom = 0.0F;
      this.CG = 0.0F;
      this.HH = 0.0F;
      this.wI = System.nanoTime();
      this.aiF = null;
   }

   public void c(Runnable runnable) {
      this.th = ThreeStateEnum.su;
      this.aom = 0.0F;
      this.wI = System.nanoTime();
      this.aiF = runnable;
   }

   @Override
   public void f() {
      if (this.th != ThreeStateEnum.MT) {
         this.aom = this.aom + this.getDeltaTime();
         float f = Math.min(this.aom / 0.2F, 1.0F);
         float f1 = this.c(f);
         if (this.th == ThreeStateEnum.IH) {
            this.CG = f1;
            this.HH = f1 * 30.0F;
         } else if (this.th == ThreeStateEnum.su) {
            this.CG = 1.0F - f1;
            this.HH = (1.0F - f1) * 30.0F;
         }

         if (f >= 1.0F) {
            this.d();
         }
      }
   }

   private void d() {
      boolean flag = this.th == ThreeStateEnum.su;
      this.th = ThreeStateEnum.MT;
      this.aom = 0.0F;
      this.CG = flag ? 0.0F : 1.0F;
      this.HH = flag ? 0.0F : 30.0F;
      if (this.aiF != null) {
         Runnable runnable = this.aiF;
         this.aiF = null;
         runnable.run();
      }
   }

   @Override
   public boolean g() {
      return this.th != ThreeStateEnum.MT;
   }

   @Override
   public void h() {
      this.th = ThreeStateEnum.MT;
      this.aom = 0.0F;
      this.CG = 0.0F;
      this.HH = 0.0F;
      this.aiF = null;
      this.wI = System.nanoTime();
   }

   public float e() {
      return this.CG;
   }

   public float f_2() {
      return this.HH;
   }
}
