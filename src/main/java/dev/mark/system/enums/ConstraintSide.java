package dev.mark.system.enums;

public enum ConstraintSide {
   KC,
   Kq,
   tO,
   uN;

   public static ConstraintSide a(String s) {
      return Enum.<ConstraintSide>valueOf(ConstraintSide.class, s);
   }

   private static ConstraintSide[] b() {
      return new ConstraintSide[]{KC, Kq, tO, uN};
   }
}
