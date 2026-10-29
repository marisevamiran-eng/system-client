package dev.mark.system.gui;

import org.lwjgl.glfw.GLFW;
import dev.mark.system.module.util.CombatScanResult;

public enum CursorType {
   ZU(GLFW.glfwCreateStandardCursor(221185)),
   eZ(GLFW.glfwCreateStandardCursor(221188)),
   G(GLFW.glfwCreateStandardCursor(221189)),
   arr(GLFW.glfwCreateStandardCursor(221190)),
   aah(GLFW.glfwCreateStandardCursor(221186)),
   Uy(GLFW.glfwCreateStandardCursor(221187)),
   FG(GLFW.glfwCreateStandardCursor(221194)),
   CombatScanResult(GLFW.glfwCreateStandardCursor(221193));

   private final long ayg;

   public static CursorType a(String s) {
      return Enum.<CursorType>valueOf(CursorType.class, s);
   }

      public long b() {
      return this.ayg;
   }

      private CursorType(long j) {
      this.ayg = j;
   }

   private static CursorType[] c() {
      return new CursorType[]{ZU, eZ, G, arr, aah, Uy, FG, CombatScanResult};
   }
}
