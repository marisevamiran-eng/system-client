package dev.mark.system.core;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;

public class NativeGuard {
   private static String[] Ao = new String[12];

   public static boolean a() { return false; }

   private static String b() { return null; }

   private static String c() { return null; }

   private static String d(int i, int j, char c0) { return null; }

   private static char e() { return '\0'; }

   private static String f(String s) { return null; }

   private static String g(String s) { return null; }

   private static CallSite h(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   static {
      Loader.init(NativeGuard.class);
      j();
   }

   private static String i(char[] achar, long i, int j) { return null; }

   private static void j() {  }

   public static void guard() {  }
}
