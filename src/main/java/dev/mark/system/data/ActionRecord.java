package dev.mark.system.data;

import dev.mark.system.core.Loader;


public class ActionRecord {
   String xc;
   String Ie;
   String uU;

   ActionRecord(String s, String s1, String s2) {
      this.xc = s;
      this.Ie = s1;
      this.uU = s2;
   }

   static {
      Loader.init(ActionRecord.class);
   }

   public static void guard() {  }
}
