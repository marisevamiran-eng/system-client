package dev.mark.system.config;

import dev.mark.system.core.Loader;


public class GroupScrollState {
   private String moduleName;
   private String groupName;
   private float scrollPosition;

   public GroupScrollState(String s, String s1, float f) {
      this.moduleName = s;
      this.groupName = s1;
      this.scrollPosition = f;
   }

   public String a() { return null; }

   public String b() { return null; }

   public float c() { return 0; }

   public void d(String s) {  }

   public void e(String s) {  }

   public void f(float f) {  }

   static {
      Loader.init(GroupScrollState.class);
   }

   public static void guard() {  }
}
