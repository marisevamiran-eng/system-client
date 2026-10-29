package dev.mark.system.gui.enums;

public enum FourValueEnum {
   Lt,
   BQ,
   tX,
   jB;

   public static FourValueEnum a(String s) {
      return Enum.<FourValueEnum>valueOf(FourValueEnum.class, s);
   }

   private static FourValueEnum[] b() {
      return new FourValueEnum[]{Lt, BQ, tX, jB};
   }
}
