package dev.mark.system.gui.enums;

public enum AnimationMode {
   apC,
   wx,
   Gt,
   nx;

   public static AnimationMode a(String s) {
      return Enum.<AnimationMode>valueOf(AnimationMode.class, s);
   }

   private static AnimationMode[] b() {
      return new AnimationMode[]{apC, wx, Gt, nx};
   }
}
