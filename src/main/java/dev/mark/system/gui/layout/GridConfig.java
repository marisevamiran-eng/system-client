package dev.mark.system.gui.layout;


import dev.mark.system.gui.enums.ToggleState;
public class GridConfig {
   private final float aya;
   private final float am;
   private final int fj;
   private final float gP;
   private final float yt;
   private final float Pb;
   private final ToggleState aeJ;

   public GridConfig(float f, float f1, int i, float f2, float f3, float f4, ToggleState ToggleState) {
      this.aya = f;
      this.am = f1;
      this.fj = i;
      this.gP = f2;
      this.yt = f3;
      this.Pb = f4;
      this.aeJ = ToggleState;
   }

   public static GridConfig a() {
      return new GridConfig(286.0F, 40.0F, 3, 10.0F, 11.0F, 150.0F, ToggleState.fY);
   }

   public static GridConfig b() {
      return new GridConfig(286.0F, 130.0F, 3, 10.0F, 11.0F, 150.0F, ToggleState.fY);
   }

   public static GridConfig c() {
      return new GridConfig(286.0F, 201.0F, 3, 10.0F, 11.0F, 150.0F, ToggleState.wo);
   }

   public float d() {
      return this.aya;
   }

   public float e() {
      return this.am;
   }

   public int f() {
      return this.fj;
   }

   public float g() {
      return this.gP;
   }

   public float h() {
      return this.yt;
   }

   public float i() {
      return this.Pb;
   }

   public ToggleState j() {
      return this.aeJ;
   }
}
