package dev.mark.system.core;

import java.util.Map;

public class ConfigApiResponse {
   boolean success;
   String message;
   String configData;
   Map<String, Object> data;

   private ConfigApiResponse() {
   }

   boolean a() { return false; }

   String b() { return null; }

   Map<String, Object> c() { return null; }

   static {
      Loader.init(ConfigApiResponse.class);
   }

   public static void guard() {  }
}
