package dev.mark.system.gui;

public final class CursorStateManager {
   private static CursorType oQ = CursorType.ZU;
   private static CursorType alT = CursorType.eZ;

   public static void a(CursorType CursorType) {
      oQ = CursorType;
   }

   public static CursorType b() {
      return oQ;
   }

   public static CursorType c() {
      return alT;
   }

   public static void d(CursorType CursorType) {
      alT = CursorType;
   }
}
