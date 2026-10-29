package dev.mark.system.core;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;

public class NativeBootstrapAlt {
   private static String[] mp = new String[4];

   public static void a() {  }

   private static CallSite b(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   private static CallSite c(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   private static CallSite d(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   static {
      Loader.init(NativeBootstrapAlt.class);
      f();
   }

   private static String e(char[] achar, long i, int j) { return null; }

   private static void f() {  }

   public static void guard() {  }
}
