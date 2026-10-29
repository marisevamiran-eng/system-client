package dev.mark.system.data;

import dev.mark.system.core.Loader;


public class Position {
   private float x;
   private float y;

   public Position(float f, float f1) {
      this.x = f;
      this.y = f1;
   }

   public float getX() { return 0; }

   public float getY() { return 0; }

   public void setX(float f) {  }

   public void setY(float f) {  }

   static {
      Loader.init(Position.class);
   }

   public static void guard() {  }
}
