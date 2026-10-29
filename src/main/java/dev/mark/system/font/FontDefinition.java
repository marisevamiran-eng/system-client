package dev.mark.system.font;

import com.google.gson.annotations.SerializedName;
import dev.mark.system.font.FontAtlas;
import dev.mark.system.font.FontMetrics;
import java.util.List;

public final class FontDefinition {
   private FontAtlas atlas;
   private FontMetrics metrics;
   private List<GlyphDescriptor> glyphs;
   @SerializedName("kerning")
   private List<KerningPair> kernings;

   public FontAtlas a() {
      return this.atlas;
   }

   public FontMetrics b() {
      return this.metrics;
   }

   public List<GlyphDescriptor> c() {
      return this.glyphs;
   }

   public List<KerningPair> d() {
      return this.kernings;
   }
}
