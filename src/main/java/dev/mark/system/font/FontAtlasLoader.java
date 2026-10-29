package dev.mark.system.font;

import dev.mark.system.core.ImageCache;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import dev.mark.system.core.NativeBootstrapAlt;
import dev.mark.system.data.SmallStateData;
public class FontAtlasLoader {
   private static final Map<String, byte[]> fontAtlases = new HashMap<String, byte[]>();
   private static boolean initialized = false;

   private static void a() {
      b("a", "msdf-semibold");
      b("native_bootstrap_alt", "native_bootstrap_alt");
      b("b", "msdf-semibold");
      b("bb", "msdf-regular");
      b("c", "msdf-regular");
      initialized = true;
   }

   private static void b(String s, String s1) {
      try {
          byte[] abyte = ImageCache.a(s1);

          if (abyte == null) {
            String resourcePath = "/fonts/" + s1 + ".png";
            InputStream inputStream = FontAtlasLoader.class.getResourceAsStream(resourcePath);
            
            if (inputStream != null) {
               abyte = inputStream.readAllBytes();
               inputStream.close();
            } else {
               System.err.println("Font atlas resource not found: " + resourcePath);
            }
         }
         
         if (abyte != null && abyte.length > 0) {
            fontAtlases.put(s, abyte);
         } else {
            System.err.println("Failed to load atlas: " + s1);
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public static byte[] c(String s) {
      return fontAtlases.get(s);
   }

   static {
      a();
   }
}
