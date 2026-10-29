package dev.mark.system.core;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
   private boolean success;
   private String message;
   private String status;
   private Map<String, Object> data;
   private static String[] _6LJqjdNWUIWRnFRF = new String[7];

   public ApiResponse(boolean flag, String s, String s1) {
      this.success = flag;
      this.message = s;
      this.status = s1;
      this.data = new HashMap<String, Object>();
   }

   public ApiResponse(boolean flag, String s, String s1, Map<String, Object> map) {
      this.success = flag;
      this.message = s;
      this.status = s1;
      this.data = (Map<String, Object>)(map != null ? map : new HashMap<String, Object>());
   }

   public boolean a() { return false; }

   public boolean b() { return false; }

   public Object c(String s) { return null; }

   public boolean d(String s) { return false; }

   @Override
   public String toString() { return null; }

   public boolean e() { return false; }

   public String f() { return null; }

   public String g() { return null; }

   public Map<String, Object> h() { return null; }

   private static CallSite i(Lookup lookup, String s, MethodType methodtype, String s1) { return null; }

   static {
      Loader.init(ApiResponse.class);
      k();
   }

   private static String j(char[] achar, long i, int j) { return null; }

   private static void k() {  }

   public static void guard() {  }
}
