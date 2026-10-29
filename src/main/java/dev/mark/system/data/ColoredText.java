package dev.mark.system.data;

import java.awt.Color;

public class ColoredText {
   private final String text;
   private final Color color;

   public ColoredText(String s, Color color) {
      this.text = s;
      this.color = color;
   }

   public String text() { return this.text; }
   public Color color() { return this.color; }
   public String getText() { return this.text; }
   public Color getColor() { return this.color; }
}
