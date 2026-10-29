package dev.mark.system.gui.enums;

public enum VisibilityMode {
   VI,
   l;

   public static VisibilityMode a(String s) {
      return Enum.<VisibilityMode>valueOf(VisibilityMode.class, s);
   }

   private static VisibilityMode[] b() {
      return new VisibilityMode[]{VI, l};
   }
}
