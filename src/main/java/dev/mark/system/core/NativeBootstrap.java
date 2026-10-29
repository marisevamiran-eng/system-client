package dev.mark.system.core;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;

public class NativeBootstrap {
   private static String[] avg = new String[10];

   public static void a() {  }

   private static boolean b() { return false; }

   private static boolean c() { return false; }

   private static boolean d() { return false; }

   private static boolean e() { return false; }

   private static String f(String s) { return null; }

   private static CallSite g(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   private static CallSite h(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   private static CallSite i(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   static {
      Loader.init(NativeBootstrap.class);
      k();
   }

   private static String j(char[] achar, long i, int j) { return null; }

   private static void k() {  }

   public static void guard() {  }
}
