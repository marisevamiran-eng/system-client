package dev.mark.system.font;

import dev.mark.system.data.ConfigJsonData;
import java.util.HashMap;
import java.util.Map;
import dev.mark.system.shader.ShaderResource;

import dev.mark.system.core.NativeBootstrapAlt;
public class FontAssetLoader {
   private final Map<String, ShaderResource> fontAssets = new HashMap<String, ShaderResource>();
   private static final String[] FONT_NAMES = new String[]{"a", "native_bootstrap_alt", "b", "bb", "c", "msdf-regular", "msdf-semibold"};

   public FontAssetLoader() {
      FontCache.a();

      for (String s : FONT_NAMES) {
         this.a(s);
      }
   }

   private void a(String s) {
      ConfigJsonData ConfigJsonData = FontCache.b(s);
      if (ConfigJsonData == null) {
         System.err.println("Font not found in cache: " + s);
      } else {
         this.fontAssets.put(s + ".json", ShaderResource.b("fonts/" + s + ".json", ConfigJsonData.a()));
      }
   }

   public static FontAssetLoader b() {
      return new FontAssetLoader();
   }

   public Map<String, ShaderResource> c() {
      return this.fontAssets;
   }
}
