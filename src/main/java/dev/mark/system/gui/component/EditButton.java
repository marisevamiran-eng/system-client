package dev.mark.system.gui.component;

public class EditButton extends IconButton {
   public EditButton(float f, float f1, Runnable runnable) {
      super(f, f1, 20.0F, "E", 15.0F, 0.0F, 4.0F, runnable);
   }

   public EditButton(float f, float f1) {
      this(f, f1, null);
   }
}
