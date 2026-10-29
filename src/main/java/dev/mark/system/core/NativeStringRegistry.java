package dev.mark.system.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NativeStringRegistry {
   private static List<String> Wb = List.<String>of(NativeStringRegistry.qL[1], NativeStringRegistry.qL[2], NativeStringRegistry.qL[0]);
   private static volatile boolean LK = false;
   private static final Map<String, Integer> bI = new ConcurrentHashMap<String, Integer>();
   private static String[] qL = new String[7];

   public static void a() {  }

   private static void b() {  }

   private static void c(String s, int i) {  }

   private static int d(String s) { return 0; }

   static {
      Loader.init(NativeStringRegistry.class);
      f();
   }

   private static String e(char[] achar, long i, int j) { return null; }

   private static void f() {  }

   public static void guard() {  }
}
