package dev.mark.system.gui.animation;


import dev.mark.system.gui.enums.LayoutAlignment;
public class VisibilityAnimator extends AbstractAnimator {
   private static final float iI = 0.15F;
   private static final float RA = 0.95F;
   private static final float aiR = 0.050000012F;
   private LayoutAlignment rt = LayoutAlignment.xu;
   private float jv = 0.0F;
   private float vF = 1.0F;
   private float Ir = 1.0F;
   private float alc = 1.0F;
   private boolean aed = true;
   private float xE = 1.0F;
   private Runnable qR;

   @Override
   public void a() {
      super.a();
      this.h();
   }

   public void a_2(boolean flag, float f) {
      super.a();
      this.xE = f;
      if (flag) {
         this.vF = 1.0F;
         this.Ir = 1.0F;
         this.alc = f;
         this.aed = true;
      } else {
         this.vF = 0.0F;
         this.Ir = 0.95F;
         this.alc = 0.0F;
         this.aed = false;
      }

      this.rt = LayoutAlignment.xu;
      this.jv = 0.0F;
   }

   public void b(float f) {
      if (this.rt != LayoutAlignment.Gb) {
         this.rt = LayoutAlignment.Gb;
         this.jv = 0.0F;
         this.xE = f;
         this.aed = true;
         this.wI = System.nanoTime();
         this.qR = null;
      }
   }

   public void c(Runnable runnable) {
      if (this.rt != LayoutAlignment.adG) {
         this.rt = LayoutAlignment.adG;
         this.jv = 0.0F;
         this.wI = System.nanoTime();
         this.qR = runnable;
      }
   }

   public void d() {
      this.rt = LayoutAlignment.xu;
      this.jv = 0.0F;
      this.vF = 1.0F;
      this.Ir = 1.0F;
      this.alc = this.xE;
      this.aed = true;
   }

   public void e() {
      this.rt = LayoutAlignment.xu;
      this.jv = 0.0F;
      this.vF = 0.0F;
      this.Ir = 0.95F;
      this.alc = 0.0F;
      this.aed = false;
      if (this.qR != null) {
         Runnable runnable = this.qR;
         this.qR = null;
         runnable.run();
      }
   }

   @Override
   public void f() {
      if (this.rt != LayoutAlignment.xu) {
         this.jv = this.jv + this.getDeltaTime();
         float f = Math.min(this.jv / 0.15F, 1.0F);
         float f1 = this.c(f);
         if (this.rt == LayoutAlignment.Gb) {
            this.vF = f1;
            this.Ir = 0.95F + f1 * 0.050000012F;
            this.alc = f1 * this.xE;
         } else if (this.rt == LayoutAlignment.adG) {
            this.vF = 1.0F - f1;
            this.Ir = 1.0F - f1 * 0.050000012F;
            this.alc = (1.0F - f1) * this.xE;
         }

         if (f >= 1.0F) {
            this.completeAnimation();
         }
      }
   }

   private void completeAnimation() {
      boolean flag = this.rt == LayoutAlignment.adG;
      this.rt = LayoutAlignment.xu;
      this.jv = 0.0F;
      if (flag) {
         this.vF = 0.0F;
         this.Ir = 0.95F;
         this.alc = 0.0F;
         this.aed = false;
      } else {
         this.vF = 1.0F;
         this.Ir = 1.0F;
         this.alc = this.xE;
         this.aed = true;
      }

      if (this.qR != null) {
         Runnable runnable = this.qR;
         this.qR = null;
         runnable.run();
      }
   }

   @Override
   public boolean g() {
      return this.rt != LayoutAlignment.xu;
   }

   public boolean isShowing() {
      return this.rt == LayoutAlignment.Gb;
   }

   public boolean i() {
      return this.rt == LayoutAlignment.adG;
   }

   @Override
   public void h() {
      this.rt = LayoutAlignment.xu;
      this.jv = 0.0F;
      this.vF = 1.0F;
      this.Ir = 1.0F;
      this.alc = 1.0F;
      this.xE = 1.0F;
      this.aed = true;
      this.qR = null;
      this.wI = System.nanoTime();
   }

   public float j() {
      return this.vF;
   }

   public float k() {
      return this.Ir;
   }

   public float l() {
      return this.alc;
   }

   public boolean m() {
      return this.aed;
   }
}
