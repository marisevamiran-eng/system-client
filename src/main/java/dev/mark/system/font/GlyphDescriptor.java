package dev.mark.system.font;

public final class GlyphDescriptor {
   private int unicode;
   private int index;
   private float advance;
   private RectBounds planeBounds;
   private RectBounds atlasBounds;

   public int a() {
      return this.unicode;
   }

   public float b() {
      return this.advance;
   }

   public RectBounds c() {
      return this.planeBounds;
   }

   public RectBounds d() {
      return this.atlasBounds;
   }
}
