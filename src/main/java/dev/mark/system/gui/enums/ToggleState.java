package dev.mark.system.gui.enums;

public enum ToggleState {
   fY,
   wo;

   public static ToggleState a(String s) {
      return Enum.<ToggleState>valueOf(ToggleState.class, s);
   }

   private static ToggleState[] b() {
      return new ToggleState[]{fY, wo};
   }
}
