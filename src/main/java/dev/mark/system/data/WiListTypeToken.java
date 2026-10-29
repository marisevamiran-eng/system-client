package dev.mark.system.data;

import dev.mark.system.core.Loader;

import dev.mark.system.core.FriendListApiClient;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class WiListTypeToken extends TypeToken<List<ConfigJsonData>> {
   WiListTypeToken(FriendListApiClient FriendListApiClient) {
   }

   static {
      Loader.init(WiListTypeToken.class);
   }

   public static void guard() {  }
}
