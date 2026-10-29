package dev.mark.system.core;

import dev.mark.system.data.ConfigJsonData;
import dev.mark.system.core.ApiClient;
import dev.mark.system.core.AuthConfig;
import java.util.List;

public class FriendListApiClient extends ApiClient {
   private static String[] zs6JS4CdWsooThlX = new String[4];

   public FriendListApiClient() {
      super(AuthConfig.getHost(), AuthConfig.getPort());
   }

   public List<ConfigJsonData> a() {
      return new java.util.ArrayList<>();
   }

   private List<ConfigJsonData> b(String s) {
      return null;
   }

   static {
      Loader.init(FriendListApiClient.class);
      d();
   }

   private static String c(char[] achar, long i, int j) {
      return null;
   }

   private static void d() {
   }

   public static void guard() {
   }
}
