package dev.mark.system.font;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mark.system.font.Glyph;
import dev.mark.system.font.MSDFFont;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import dev.mark.system.render.ShaderUtil;
import dev.mark.system.shader.ShaderResource;

import dev.mark.system.data.SmallStateData;
public class MSDFFontLoader {
   private String name = "?";
   private String dataFileName;
   private Identifier atlasIdentifier;
   private String desktopAtlasFileName;

   public MSDFFontLoader() {
   }

   public MSDFFontLoader a(String s) {
      this.name = s;
      return this;
   }

   public MSDFFontLoader b(String s) {
      this.dataFileName = s;
      return this;
   }

   public MSDFFontLoader c(String s) {
      this.desktopAtlasFileName = s;
      this.atlasIdentifier = null;
      return this;
   }

   private NativeImage d(BufferedImage bufferedimage) {
      int i = bufferedimage.getWidth();
      int j = bufferedimage.getHeight();
      NativeImage nativeimage = new NativeImage(i, j, false);

      for (int k = 0; k < i; k++) {
         for (int l = 0; l < j; l++) {
            nativeimage.setColorArgb(k, l, bufferedimage.getRGB(k, l));
         }
      }

      return nativeimage;
   }

   public MSDFFont e() {
      FontDefinition FontDefinition = null;
      FontAssetLoader fontAssetLoader = FontAssetLoader.b();
      String s3 = this.dataFileName;
      String s = s3 + ".json";

      try {
         ShaderResource shaderresource = fontAssetLoader.c().get(s);
         if (shaderresource != null) {
            try (InputStream inputstream = shaderresource.toResource().getInputStream()) {
               String s1 = new String(inputstream.readAllBytes(), StandardCharsets.UTF_8);
               FontDefinition = ShaderUtil.<FontDefinition>b(s1, FontDefinition.class);
            }
         }
      } catch (Exception exception) {
         throw new RuntimeException("Failed to load font data: " + s, exception);
      }

      if (FontDefinition == null) {
         throw new RuntimeException("Font data is null for: " + s);
      } else {
         Object object;
         if (this.desktopAtlasFileName != null) {
            try {
               byte[] abyte = FontAtlasLoader.c(this.dataFileName);
               if (abyte == null) {
                  throw new IOException("Font atlas not found in cache");
               }

               ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
               BufferedImage bufferedimage = ImageIO.read(bytearrayinputstream);
               if (bufferedimage == null) {
                  throw new IOException("Failed to read image from bytes");
               }

               object = new NativeImageBackedTexture(this.d(bufferedimage));
            } catch (IOException ioexception) {
               if (this.atlasIdentifier == null) {
                  throw new RuntimeException("Failed to load atlas from cache and no fallback identifier", ioexception);
               }

               object = MinecraftClient.getInstance().getTextureManager().getTexture(this.atlasIdentifier);
            }
         } else {
            if (this.atlasIdentifier == null) {
               throw new RuntimeException("No atlas source specified");
            }

            object = MinecraftClient.getInstance().getTextureManager().getTexture(this.atlasIdentifier);
         }

         AbstractTexture object1 = (AbstractTexture) object;
         RenderSystem.recordRenderCall(() -> {
            object1.setFilter(true, false);
         });
         float f = FontDefinition.a().b();
         float f1 = FontDefinition.a().c();
         Map<Integer, Glyph> map = FontDefinition.c().stream()
            .filter(glyphDescriptor -> glyphDescriptor.a() > 0)
            .collect(Collectors.toMap(GlyphDescriptor::a, GlyphDescriptor -> {
               return new Glyph(GlyphDescriptor, f, f1);
            }));
         HashMap hashmap = new HashMap();
         FontDefinition.d().forEach(KerningPair -> {
            Map<Integer, Float> map2 = (Map<Integer, Float>)hashmap.computeIfAbsent(KerningPair.a(), integer -> {
               return new HashMap();
            });
            map2.put(KerningPair.b(), KerningPair.c());
         });
         String s2 = "?".equals(this.name) ? this.dataFileName : this.name;
         return new MSDFFont(s2, (AbstractTexture)object, FontDefinition.a(), FontDefinition.b(), map, hashmap);
      }
   }
}
