package dev.mark.system.hud;


import dev.mark.system.gui.animation.HoverStateAnimator;
public abstract class ListEntry<T> {
   protected final T alQ;
   protected final String RQ;
   protected final HoverStateAnimator ox;

   public ListEntry(T object, String s) {
      this.alQ = (T)object;
      this.RQ = s;
      this.ox = new HoverStateAnimator();
      this.ox.a();
   }

   public abstract void a();

   public T b() {
      return this.alQ;
   }

   public String c() {
      return this.RQ;
   }

   public HoverStateAnimator d() {
      return this.ox;
   }
}
