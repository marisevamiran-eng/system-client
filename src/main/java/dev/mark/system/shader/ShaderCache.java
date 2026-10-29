package dev.mark.system.shader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ShaderCache {
   private static final Map<String, ShaderData> cache = new HashMap<String, ShaderData>();
   private static boolean initialized = false;
   private static final String[] SHADER_NAMES = new String[]{
      "rectangle", "border", "white_rectangle", "outline", "line", "line3d", 
      "texture", "blur", "msdf_font", "colorpicker", "corner_bracket", "liquidglass"
   };

   public static synchronized void a() {
      if (!initialized) {
         try {
             ShaderDataFetcher shaderdatafetcher = new ShaderDataFetcher();
            java.util.List<ShaderData> serverShaders = shaderdatafetcher.a();

            if (serverShaders != null) {
               for (ShaderData shaderdata : serverShaders) {
                  cache.put(shaderdata.getName(), shaderdata);
               }
            }

             if (cache.isEmpty()) {
               loadFromResources();
            }

            initialized = true;
         } catch (Exception exception) {
            exception.printStackTrace();
             if (cache.isEmpty()) {
               loadFromResources();
            }
            initialized = true;
         }
      }
   }

   private static void loadFromResources() {
      for (String shaderName : SHADER_NAMES) {
         try {
            String basePath = "/assets/system-client/shaders/core/" + shaderName;
            
            InputStream jsonStream = ShaderCache.class.getResourceAsStream(basePath + ".json");
            InputStream fshStream = ShaderCache.class.getResourceAsStream(basePath + ".fsh");
            InputStream vshStream = ShaderCache.class.getResourceAsStream(basePath + ".vsh");
            
            if (jsonStream != null && fshStream != null && vshStream != null) {
               byte[] jsonBytes = jsonStream.readAllBytes();
               byte[] fshBytes = fshStream.readAllBytes();
               byte[] vshBytes = vshStream.readAllBytes();
               
               String jsonData = new String(jsonBytes, StandardCharsets.UTF_8);
               String fshData = new String(fshBytes, StandardCharsets.UTF_8);
               String vshData = new String(vshBytes, StandardCharsets.UTF_8);
               
               cache.put(shaderName, new ShaderData() {
                  @Override
                  public byte[] getJsonBytes() {
                     return jsonBytes;
                  }
                  
                  @Override
                  public byte[] getFshBytes() {
                     return fshBytes;
                  }
                  
                  @Override
                  public byte[] getVshBytes() {
                     return vshBytes;
                  }
                  
                  @Override
                  public String getName() {
                     return shaderName;
                  }
                  
                  @Override
                  public String e() {
                     return jsonData;
                  }
                  
                  @Override
                  public String f() {
                     return fshData;
                  }
                  
                  @Override
                  public String g() {
                     return vshData;
                  }
               });
               
               jsonStream.close();
               fshStream.close();
               vshStream.close();
            } else {
               System.err.println("Shader resource not found: " + basePath);
            }
         } catch (Exception e) {
            System.err.println("Failed to load shader from resources: " + shaderName);
            e.printStackTrace();
         }
      }
   }

   public static ShaderData getShader(String s) {
      if (!initialized) {
         a();
      }

      return cache.get(s);
   }

   public static void clear() {
      cache.clear();
      initialized = false;
   }
}
