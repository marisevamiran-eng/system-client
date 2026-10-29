package dev.mark.system.gui.enums;

public enum TriStateMode {
   qC,
   sr,
   ako;

   public static TriStateMode a(String s) {
      return Enum.<TriStateMode>valueOf(TriStateMode.class, s);
   }

   private static TriStateMode[] b() {
      return new TriStateMode[]{qC, sr, ako};
   }
}
