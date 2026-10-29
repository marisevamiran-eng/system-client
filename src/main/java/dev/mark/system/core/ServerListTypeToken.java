package dev.mark.system.core;

import dev.mark.system.data.ConfigInfo;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class ServerListTypeToken extends TypeToken<List<ConfigInfo>> {
   ServerListTypeToken(CloudApiClient CloudApiClient) {
   }

   static {
      Loader.init(ServerListTypeToken.class);
   }

   public static void guard() {  }
}
