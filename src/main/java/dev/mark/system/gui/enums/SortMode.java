package dev.mark.system.gui.enums;

public enum SortMode {
   hu,
   LQ,
   avp;

   public static SortMode a(String s) {
      return Enum.<SortMode>valueOf(SortMode.class, s);
   }

   private static SortMode[] b() {
      return new SortMode[]{hu, LQ, avp};
   }
}
