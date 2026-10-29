package dev.mark.system.gui.enums;

public enum FourStateEnum2 {
   atV,
   TI,
   se,
   Eu;

   public static FourStateEnum2 a(String s) {
      return Enum.<FourStateEnum2>valueOf(FourStateEnum2.class, s);
   }

   private static FourStateEnum2[] b() {
      return new FourStateEnum2[]{atV, TI, se, Eu};
   }
}
