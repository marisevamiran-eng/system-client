package dev.mark.system.gui.enums;

public enum LayoutAlignment {
   xu,
   Gb,
   adG;

   public static LayoutAlignment a(String s) {
      return Enum.<LayoutAlignment>valueOf(LayoutAlignment.class, s);
   }

   private static LayoutAlignment[] b() {
      return new LayoutAlignment[]{xu, Gb, adG};
   }
}
