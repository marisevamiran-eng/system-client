package dev.mark.system.hud;

import org.joml.Matrix4f;
import dev.mark.system.render.BuiltText;

import dev.mark.system.gui.animation.easing.EasingFunctions;
public abstract class ListHudComponent<T extends ListEntry<?>> extends HudComponent {
   protected final ListEntryManager<T> entryManager;
   protected final float rowHeight;
   protected final float minContentHeight;

   public ListHudComponent(float f, float f1, String s, String s1, float f2, float f3) {
      super(f, f1, s, s1);
      this.rowHeight = f2;
      this.minContentHeight = f3;
      this.entryManager = new ListEntryManager<T>(f2, 5.0F);
   }

   @Override
   protected float calculateContentHeight() {
      if (this.hidden) {
         this.b();
         float f = this.entryManager.d(this.minContentHeight, 5.0F);
         return Math.max(f, this.minContentHeight);
      } else {
         this.a();
         return this.entryManager.d(this.minContentHeight, 5.0F);
      }
   }

   protected abstract void a();

   protected void b() {
      this.a();
   }

   @Override
   public void render(Matrix4f matrix4f, float f) {
      this.initialize();
      if (!this.dragging) {
         this.x = this.savedX;
         this.y = this.savedY;
      }

      if (!this.hidden) {
         this.recalculateHeight();
         this.clampPosition();
         this.entryManager.c();
         float f4 = this.entryManager.g().h();

          if (this.isAlwaysVisible() || !(f4 <= 0.001F)) {
            float f5 = this.isAlwaysVisible() && f4 <= 0.001F ? f : EasingFunctions.a(f4) * f;
            float f1 = 0.92F + 0.08F * EasingFunctions.c(f4);
            float f2 = this.x + this.width / 2.0F;
            float f3 = this.y + this.totalHeight / 2.0F;
            matrix4f.translate(f2, f3, 0.0F);
            matrix4f.scale(f1, f1, 1.0F);
            matrix4f.translate(-f2, -f3, 0.0F);
            this.c(matrix4f, f5);
            matrix4f.translate(f2, f3, 0.0F);
            matrix4f.scale(1.0F / f1, 1.0F / f1, 1.0F);
            matrix4f.translate(-f2, -f3, 0.0F);
         }
      } else {
         this.entryManager.g().f(true);
         this.entryManager.g().d(1.0F);
         this.recalculateHeight();
         this.clampPosition();

         for (ListEntry listentry : this.entryManager.e()) {
            listentry.d().e(true);
         }

         this.entryManager.c();
         if (this.backgroundRect != null && this.headerRect != null) {
            this.c(matrix4f, f);
         }
      }
   }

   protected void c(Matrix4f matrix4f, float f) {
      if (this.backgroundRect != null && this.headerRect != null) {
         this.backgroundRect.a(matrix4f, this.x, this.y, f);
         this.headerRect.a(matrix4f, this.x, this.y, f);
         BuiltText builttext = this.w(this.ew, this.displayName, 16.0F, Bz);
         float f1 = this.ew.c(this.displayName, 16.0F);
         float f2 = this.x + (this.width - f1) * 0.5F;
         float f3 = this.y + 5.0F;
         builttext.a(matrix4f, f2, f3, f);
         float f4 = this.y + 29.0F + 5.0F;
         this.renderContent(matrix4f, this.x, f4, f);
      }
   }

   @Override
   protected void renderContent(Matrix4f matrix4f, float f, float f1, float f2) {
      if (!this.entryManager.f()) {
         for (ListEntry listentry : this.entryManager.e()) {
            float f3 = listentry.d().g();
            if (this.hidden) {
               f3 = 1.0F;
            }

            if (!(f3 <= 0.001F)) {
               float f4 = EasingFunctions.a(f3) * f2;
               float f5 = 0.95F + 0.05F * EasingFunctions.b(f3);
               float f6 = this.rowHeight * EasingFunctions.a(f3);
               float f7 = f1 + listentry.d().j();
               float f8 = f + this.width / 2.0F;
               float f9 = f7 + f6 / 2.0F;
               matrix4f.translate(f8, f9, 0.0F);
               matrix4f.scale(f5, f5, 1.0F);
               matrix4f.translate(-f8, -f9, 0.0F);
               this.d(matrix4f, f, f7, (T)listentry, f6, f4);
               matrix4f.translate(f8, f9, 0.0F);
               matrix4f.scale(1.0F / f5, 1.0F / f5, 1.0F);
               matrix4f.translate(-f8, -f9, 0.0F);
            }
         }
      }
   }

   protected abstract void d(Matrix4f matrix4f, float f, float f1, T t, float f2, float f3);

   @Override
   public boolean mouseClicked(double d0, double d1, int i) {
      if (this.hidden) {
         return super.mouseClicked(d0, d1, i);
      } else {
          boolean canClick = this.isAlwaysVisible() || !(this.entryManager.g().h() < 0.5F);
         return !this.entryManager.f() && canClick ? super.mouseClicked(d0, d1, i) : false;
      }
   }

   @Override
   public boolean isHovered(double d0, double d1) {
      if (this.hidden) {
         return super.isHovered(d0, d1);
      } else {
          boolean canHover = this.isAlwaysVisible() || !(this.entryManager.g().h() < 0.5F);
         return !this.entryManager.f() && canHover ? super.isHovered(d0, d1) : false;
      }
   }
}
