package dev.mark.system.gui.enums;

public enum TwoValueEnum {
   sZ,
   Ij;

   public static TwoValueEnum a(String s) {
      return Enum.<TwoValueEnum>valueOf(TwoValueEnum.class, s);
   }

   private static TwoValueEnum[] b() {
      return new TwoValueEnum[]{sZ, Ij};
   }
}
