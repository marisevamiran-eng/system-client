package dev.mark.system.core;


public class ConfigApiRequest {
   String action;
   String hwid;
   String configData;
   String configName;
   String configDescription;
   String configKey;

   ConfigApiRequest(String s, String s1) {
      this.action = s;
      this.hwid = s1;
   }

   void a(String s) {  }

   void b(String s) {  }

   void c(String s) {  }

   void d(String s) {  }

   static {
      Loader.init(ConfigApiRequest.class);
   }

   public static void guard() {  }
}
