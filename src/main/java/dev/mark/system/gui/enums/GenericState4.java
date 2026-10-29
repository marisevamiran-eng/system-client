package dev.mark.system.gui.enums;

public enum GenericState4 {
   fy,
   fQ,
   oJ,
   Q;

   public static GenericState4 a(String s) {
      return Enum.<GenericState4>valueOf(GenericState4.class, s);
   }

   private static GenericState4[] b() {
      return new GenericState4[]{fy, fQ, oJ, Q};
   }
}
