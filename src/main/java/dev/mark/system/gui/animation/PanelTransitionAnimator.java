package dev.mark.system.gui.animation;

import dev.mark.system.gui.enums.SortMode;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltBlur;
import dev.mark.system.render.RadiusConfig;
import dev.mark.system.render.Size;

import dev.mark.system.render.builder.BlurBuilder;
public class PanelTransitionAnimator extends AbstractAnimator {
   private static final float qq = 0.2F;
   private static final float Co = 20.0F;
   private static final float vl = 12.0F;
   private static final float JC = 0.92F;
   private static final float NB = 0.07999998F;
   private static final float aku = 286.0F;
   private static final float Lz = 201.0F;
   private static final float abv = 143.0F;
   private static final float la = 100.5F;
   private static final float eX = 4.0F;
   private static final float In = 8.0F;
   private static final float ajN = 0.001F;
   private SortMode xR = SortMode.hu;
   private float Np = 0.0F;
   private float Lv = 1.0F;
   private float VU = 1.0F;
   private BuiltBlur rN;
   private Runnable yE;

   @Override
   public void a() {
      super.a();
      this.h();
   }

   public void b() {
      this.xR = SortMode.avp;
      this.Np = 0.0F;
      this.Lv = 0.92F;
      this.VU = 0.0F;
      this.wI = System.nanoTime();
      this.yE = null;
   }

   public void c(Runnable runnable) {
      this.xR = SortMode.LQ;
      this.Np = 0.0F;
      this.Lv = 1.0F;
      this.VU = 1.0F;
      this.wI = System.nanoTime();
      this.yE = runnable;
   }

   public void d() {
      if (this.xR != SortMode.hu) {
         boolean flag = this.xR == SortMode.LQ;
         this.xR = SortMode.hu;
         this.Np = 0.0F;
         this.Lv = flag ? 0.92F : 1.0F;
         this.VU = flag ? 0.0F : 1.0F;
         this.rN = null;
         if (this.yE != null) {
            Runnable runnable = this.yE;
            this.yE = null;
            runnable.run();
         }
      }
   }

   @Override
   public void f() {
      if (this.xR != SortMode.hu) {
         this.Np = this.Np + this.getDeltaTime();
         float f = Math.min(this.Np / 0.2F, 1.0F);
         float f1 = this.c(f);
         if (this.xR == SortMode.LQ) {
            this.VU = 1.0F - f1;
            this.Lv = 1.0F - f1 * 0.07999998F;
         } else {
            this.VU = f1;
            this.Lv = 0.92F + f1 * 0.07999998F;
         }

         if (f >= 1.0F) {
            this.e();
         }
      }
   }

   private void e() {
      boolean flag = this.xR == SortMode.LQ;
      this.xR = SortMode.hu;
      this.Np = 0.0F;
      this.Lv = flag ? 0.92F : 1.0F;
      this.VU = flag ? 0.0F : 1.0F;
      if (this.yE != null) {
         Runnable runnable = this.yE;
         this.yE = null;
         runnable.run();
      }
   }

   private BuiltBlur f(float f, float f1) {
      if (f1 < 0.001F) {
         return null;
      } else {
         float f2 = 294.0F * f;
         float f3 = 209.0F * f;
         float f4 = 12.0F * f;
         return new BlurBuilder().a(new Size(f2, f3)).b(new RadiusConfig(f4)).d(1.0F).e(f1).a();
      }
   }

   public void g(Matrix4f matrix4f, float f, float f1) {
      if (this.rN != null) {
         float f2 = 294.0F * this.Lv;
         float f3 = 209.0F * this.Lv;
         float f4 = f + 143.0F - f2 * 0.5F;
         float f5 = f1 + 100.5F - f3 * 0.5F;
         this.rN.render(matrix4f, f4, f5, 0.0F);
      }
   }

   @Override
   public boolean g() {
      return this.xR != SortMode.hu;
   }

   public void i() {
      this.VU = 1.0F;
      this.Lv = 1.0F;
   }

   @Override
   public void h() {
      this.xR = SortMode.hu;
      this.Np = 0.0F;
      this.Lv = 1.0F;
      this.VU = 1.0F;
      this.rN = null;
      this.yE = null;
      this.wI = System.nanoTime();
   }

   public float j() {
      return this.Lv;
   }

   public float k() {
      return this.VU;
   }
}
