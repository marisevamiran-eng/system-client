package dev.mark.system.gui.enums;

public enum BinaryMode {
   LL,
   pk;

   public static BinaryMode a(String s) {
      return Enum.<BinaryMode>valueOf(BinaryMode.class, s);
   }

   private static BinaryMode[] b() {
      return new BinaryMode[]{LL, pk};
   }
}
