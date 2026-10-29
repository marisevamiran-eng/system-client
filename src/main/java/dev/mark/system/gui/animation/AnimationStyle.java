package dev.mark.system.gui.animation;
import dev.mark.system.core.ResourceApiClient;

public enum AnimationStyle {
   Ew("Smооth"),
   ResourceApiClient("Punсh"),
   ln("Slidе"),
   aqp("Свэговая"),
   ahc("SеlfBack");

   private String acr;

   public static AnimationStyle a(String s) {
      return Enum.<AnimationStyle>valueOf(AnimationStyle.class, s);
   }

   private AnimationStyle(String s1) {
      this.acr = s1;
   }

   public static AnimationStyle b(String s) {
      for (AnimationStyle AnimationStyle : values()) {
         if (AnimationStyle.acr.equals(s)) {
            return AnimationStyle;
         }
      }

      return Ew;
   }

   public String c() {
      return this.acr;
   }

   private static AnimationStyle[] d() {
      return new AnimationStyle[]{Ew, ResourceApiClient, ln, aqp, ahc};
   }
}
