package dev.mark.system.gui.enums;

public enum FourStateEnum {
   NR,
   azA,
   gS,
   go;

   public static FourStateEnum a(String s) {
      return Enum.<FourStateEnum>valueOf(FourStateEnum.class, s);
   }

   private static FourStateEnum[] b() {
      return new FourStateEnum[]{NR, azA, gS, go};
   }
}
