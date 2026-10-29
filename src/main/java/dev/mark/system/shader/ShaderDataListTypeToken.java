package dev.mark.system.shader;

import dev.mark.system.core.Loader;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import dev.mark.system.shader.ShaderData;
import dev.mark.system.shader.ShaderDataFetcher;

public class ShaderDataListTypeToken extends TypeToken<List<ShaderData>> {
   ShaderDataListTypeToken(ShaderDataFetcher shaderdatafetcher) {
   }

   static {
      Loader.init(ShaderDataListTypeToken.class);
   }

   public static void guard() {  }
}
