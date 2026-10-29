package dev.mark.system.util;

import dev.mark.system.core.Loader;

import com.google.gson.reflect.TypeToken;
import java.util.Map;

public class BooleanMapTypeToken extends TypeToken<Map<String, Boolean>> {
   static {
      Loader.init(BooleanMapTypeToken.class);
   }

   public static void guard() {  }
}
