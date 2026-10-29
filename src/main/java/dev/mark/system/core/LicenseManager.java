package dev.mark.system.core;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LicenseManager {
   private static final ReadWriteLock lock = new ReentrantReadWriteLock();
   private static Set<Integer> allowedModuleIds = new HashSet<Integer>();
   private static String userGroup = "user";
   private static Map<String, Boolean> permissions = new HashMap<String, Boolean>();
   private static final Gson gson = new Gson();
   private static String[] LgJcyqU7BAWXtgvv = new String[4];

   public static Set<Integer> a() {
      return null;
   }

   public static boolean b() {
      return false;
   }

   private static void c(ApiResponse ApiResponse) {
   }

   private static void d(ApiResponse ApiResponse) {
   }

   private static void e(ApiResponse ApiResponse) {
   }

   public static boolean f(String s) {
      return false;
   }

   static {
      Loader.init(LicenseManager.class);
      h();
   }

   private static String g(char[] achar, long i, int j) {
      return null;
   }

   private static void h() {
   }

   public static void guard() {
   }
}
