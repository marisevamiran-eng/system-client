package dev.mark.system.gui.layout;

import dev.mark.system.gui.component.CardBase;
import java.util.List;

import dev.mark.system.gui.enums.ToggleState;
public class CardLayoutManager {
   private final GridConfig WJ;
   private float[] gV;

   public CardLayoutManager(GridConfig GridConfig) {
      this.WJ = GridConfig;
      if (GridConfig.j() == ToggleState.wo) {
         this.gV = new float[GridConfig.f()];
      }
   }

   public void a(List<CardBase> list) {
      if (this.WJ.j() == ToggleState.wo) {
         this.c(list);
      } else {
         this.b(list);
      }
   }

   private void b(List<CardBase> list) {
      for (int i = 0; i < list.size(); i++) {
         float[] afloat = GridLayoutUtil.a(i, this.WJ.f(), this.WJ.d(), this.WJ.e(), this.WJ.g(), this.WJ.h());
         ((CardBase)list.get(i)).setPosition(afloat[0], afloat[1]);
      }
   }

   private void c(List<CardBase> list) {
      this.d();

      for (CardBase CardBase : list) {
         int i = this.e();
         float f = i * (this.WJ.d() + this.WJ.g());
         float f1 = this.gV[i];
         CardBase.setPosition(f, f1);
         this.gV[i] = this.gV[i] + (CardBase.getHeight() + this.WJ.h());
      }
   }

   private void d() {
      for (int i = 0; i < this.gV.length; i++) {
         this.gV[i] = 0.0F;
      }
   }

   private int e() {
      int i = 0;
      float f = this.gV[0];

      for (int j = 1; j < this.gV.length; j++) {
         if (this.gV[j] < f) {
            f = this.gV[j];
            i = j;
         }
      }

      return i;
   }

   public float f(float f) {
      if (this.gV != null && this.gV.length != 0) {
         float f1 = 0.0F;

         for (float f2 : this.gV) {
            if (f2 > f1) {
               f1 = f2;
            }
         }

         return Math.max(0.0F, f1 - f);
      } else {
         return 0.0F;
      }
   }

   public boolean g(float f, float f1, float f2, float f3) {
      float f4 = f + f1;
      float f5 = f2 + f3;
      return f4 >= f2 && f <= f5;
   }

   public ToggleState h() {
      return this.WJ.j();
   }

   public GridConfig i() {
      return this.WJ;
   }
}
