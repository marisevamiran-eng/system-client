package dev.mark.system.core;


import dev.mark.system.util.DangerDetector;
public class NativeGuardAlt {
   private static String[] DangerDetector = new String[1];

   public static void a(String s) {  }

   static {
      Loader.init(NativeGuardAlt.class);
      c();
   }

   private static String b(char[] achar, long i, int j) { return null; }

   private static void c() {  }

   public static void guard() {  }
}
