package dev.mark.system.gui.enums;

public enum SixValueEnum {
   tw,
   KK,
   Km,
   hU,
   ZG,
   uB;

   public static SixValueEnum a(String s) {
      return Enum.<SixValueEnum>valueOf(SixValueEnum.class, s);
   }

   private static SixValueEnum[] b() {
      return new SixValueEnum[]{tw, KK, Km, hU, ZG, uB};
   }
}
