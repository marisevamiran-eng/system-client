package dev.mark.system.config;

import dev.mark.system.core.Loader;


public class Waypoint {
   private String name;
   private double x;
   private double y;
   private double z;
   private String dimension;
   private boolean visible;
   private long createdAt;

   public Waypoint(String s, double d0, double d1, double d2, String s1, boolean flag, long i) {
      this.name = s;
      this.x = d0;
      this.y = d1;
      this.z = d2;
      this.dimension = s1;
      this.visible = flag;
      this.createdAt = i;
   }

   public String a() { return null; }

   public double b() { return 0; }

   public double c() { return 0; }

   public double d() { return 0; }

   public String e() { return null; }

   public boolean f() { return false; }

   public long g() { return 0; }

   public void h(String s) {  }

   public void i(double d0) {  }

   public void j(double d0) {  }

   public void k(double d0) {  }

   public void l(String s) {  }

   public void m(boolean flag) {  }

   public void n(long i) {  }

   static {
      Loader.init(Waypoint.class);
   }

   public static void guard() {  }
}
