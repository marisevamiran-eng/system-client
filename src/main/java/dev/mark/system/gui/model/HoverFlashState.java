package dev.mark.system.gui.model;

public class HoverFlashState {
   public final int axf;
   public final int PS;
   public final float Oi;
   public final boolean Tf;

   public HoverFlashState(int i, int j, float f, boolean flag) {
      this.axf = i;
      this.PS = j;
      this.Oi = f;
      this.Tf = flag;
   }

   public int a() {
      return this.axf;
   }

   public int b() {
      return this.PS;
   }

   public float c() {
      return this.Oi;
   }

   public boolean d() {
      return this.Tf;
   }
}
