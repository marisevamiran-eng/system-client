package dev.mark.system.font;

import dev.mark.system.data.ConfigJsonData;
import dev.mark.system.core.FriendListApiClient;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FontCache {
   private static final Map<String, ConfigJsonData> cache = new HashMap<String, ConfigJsonData>();
   private static boolean initialized = false;
   private static final String[] FONT_NAMES = new String[]{"a", "native_bootstrap_alt", "b", "bb", "c", "msdf-regular", "msdf-semibold"};

   public static synchronized void a() {
      if (!initialized) {
         try {
             FriendListApiClient FriendListApiClient = new FriendListApiClient();

            for (ConfigJsonData ConfigJsonData : FriendListApiClient.a()) {
               cache.put(ConfigJsonData.b(), ConfigJsonData);
            }

             if (cache.isEmpty()) {
               loadFromResources();
            }

            initialized = true;
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }
   }

   private static void loadFromResources() {
      for (String fontName : FONT_NAMES) {
         try {
            String resourcePath = "/fonts/" + fontName + ".json";
            InputStream inputStream = FontCache.class.getResourceAsStream(resourcePath);
            
            if (inputStream != null) {
               byte[] jsonBytes = inputStream.readAllBytes();
               String jsonData = new String(jsonBytes, StandardCharsets.UTF_8);
               
               ConfigJsonData configData = new ConfigJsonData();
                cache.put(fontName, new ConfigJsonData() {
                  @Override
                  public byte[] a() {
                     return jsonBytes;
                  }
                  
                  @Override
                  public String b() {
                     return fontName;
                  }
                  
                  @Override
                  public String c() {
                     return jsonData;
                  }
               });
               
               inputStream.close();
            } else {
               System.err.println("Font resource not found: " + resourcePath);
            }
         } catch (Exception e) {
            System.err.println("Failed to load font from resources: " + fontName);
            e.printStackTrace();
         }
      }
   }

   public static ConfigJsonData b(String s) {
      if (!initialized) {
         a();
      }

      return cache.get(s);
   }

   public static void c() {
      cache.clear();
      initialized = false;
   }
}
