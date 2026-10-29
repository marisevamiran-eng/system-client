package dev.mark.system.data;

import dev.mark.system.core.Loader;


public class SmallStateData {
   boolean oT;
   String ih;
   String abn;

   private SmallStateData() {
   }

   static {
      Loader.init(SmallStateData.class);
   }

   public static void guard() {  }
}
