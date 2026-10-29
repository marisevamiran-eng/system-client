package dev.mark.system.gui.enums;

public enum SliderInteractionState {
   Xm,
   tJ,
   cf;

   public static SliderInteractionState a(String s) {
      return Enum.<SliderInteractionState>valueOf(SliderInteractionState.class, s);
   }

   private static SliderInteractionState[] b() {
      return new SliderInteractionState[]{Xm, tJ, cf};
   }
}
