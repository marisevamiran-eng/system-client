package dev.mark.system.font;

public final class Fonts {
   public static final MSDFFont ICONS;

   static {
      try {
         ICONS = MSDFFont.g()
            .a("icons")
            .b("icons")
            .c("icons")
            .e();
      } catch (Exception e) {
         throw new RuntimeException("Failed to load ICONS font", e);
      }
   }

   private Fonts() {
   }
}
