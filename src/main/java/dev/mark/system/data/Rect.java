package dev.mark.system.data;

public class Rect {
   private final int x;
   private final int y;
   private final int width;
   private final int height;

   public Rect(int i, int j, int k, int l) {
      this.x = i;
      this.y = j;
      this.width = k;
      this.height = l;
   }

   public int x() { return this.x; }
   public int y() { return this.y; }
   public int width() { return this.width; }
   public int height() { return this.height; }
   public int getX() { return this.x; }
   public int getY() { return this.y; }
   public int getWidth() { return this.width; }
   public int getHeight() { return this.height; }
}
