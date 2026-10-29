package dev.mark.system.gui.layout;

import org.joml.Matrix4f;

import dev.mark.system.gui.panel.ConfigGridPanel;
import dev.mark.system.gui.panel.FriendsGridPanel;
import dev.mark.system.gui.animation.PageTransitionAnimation;
import dev.mark.system.gui.panel.ModuleSettingsPanel;
public class TabContentRouter {
   private final ModuleSettingsPanel uV;
   private final FriendsGridPanel Be;
   private final ConfigGridPanel awl;
   private String yU = "";

   public TabContentRouter(ModuleSettingsPanel ModuleSettingsPanel, FriendsGridPanel FriendsGridPanel, ConfigGridPanel ConfigGridPanel) {
      this.uV = ModuleSettingsPanel;
      this.Be = FriendsGridPanel;
      this.awl = ConfigGridPanel;
   }

   public void a(String s) {
      this.yU = s;
   }

   public boolean b() {
      return "Friends".equals(this.yU);
   }

   public boolean c() {
      return "Configs".equals(this.yU);
   }

   public boolean d() {
      return !this.b() && !this.c();
   }

   public void e(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3, PageTransitionAnimation PageTransitionAnimation) {
      this.uV.n(f, f1);
      if (PageTransitionAnimation.g()) {
         this.uV.s(matrix4f, f, f1, i, j, f2, f3, PageTransitionAnimation);
      } else {
         this.uV.render(matrix4f, f, f1, i, j, f2, f3);
      }
   }

   public void f(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3, PageTransitionAnimation PageTransitionAnimation) {
      if (PageTransitionAnimation.g()) {
         this.Be.a(matrix4f, f, f1, i, j, f2, f3, PageTransitionAnimation);
      } else {
         this.Be.render(matrix4f, f, f1, i, j, f2, f3);
      }
   }

   public void g(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3, PageTransitionAnimation PageTransitionAnimation) {
      if (PageTransitionAnimation.g()) {
         this.awl.a(matrix4f, f, f1, i, j, f2, f3, PageTransitionAnimation);
      } else {
         this.awl.render(matrix4f, f, f1, i, j, f2, f3);
      }
   }

   public void h(float f, float f1, float f2) {
      this.uV.m(f, f1, f2);
      this.Be.i(f, f1, f2);
      this.awl.i(f, f1, f2);
   }

   public boolean i(double d0, double d1, int i) {
      if (this.b()) {
         return this.Be.mouseClicked(d0, d1, i);
      } else {
         return this.c() ? this.awl.mouseClicked(d0, d1, i) : this.uV.mouseClicked(d0, d1, i);
      }
   }

   public boolean j(double d0, double d1, int i) {
      if (i == 0) {
         this.uV.mouseReleased(d0, d1, i);
         this.Be.mouseReleased(d0, d1, i);
         this.awl.mouseReleased(d0, d1, i);
         return true;
      } else if (this.b()) {
         return this.Be.mouseReleased(d0, d1, i);
      } else {
         return this.c() ? this.awl.mouseReleased(d0, d1, i) : this.uV.mouseReleased(d0, d1, i);
      }
   }

   public boolean k(double d0, double d1, int i, double d2, double d3) {
      if (this.b()) {
         return this.Be.mouseDragged(d0, d1, i, d2, d3);
      } else {
         return this.c() ? this.awl.mouseDragged(d0, d1, i, d2, d3) : this.uV.mouseDragged(d0, d1, i, d2, d3);
      }
   }

   public boolean l(double d0, double d1, double d2, double d3) {
      if (this.b()) {
         return this.Be.mouseScrolled(d0, d1, d2, d3);
      } else {
         return this.c() ? this.awl.mouseScrolled(d0, d1, d2, d3) : this.uV.mouseScrolled(d0, d1, d2, d3);
      }
   }

   public boolean m(int i, int j, int k) {
      if (this.b()) {
         return this.Be.f(i, j, k);
      } else {
         return this.c() ? this.awl.f(i, j, k) : false;
      }
   }

   public boolean n(char c0, int i) {
      if (this.b()) {
         return this.Be.h(c0, i);
      } else {
         return this.c() ? this.awl.h(c0, i) : false;
      }
   }

   public String o(double d0, double d1) {
      return this.d() ? this.uV.P(d0, d1) : null;
   }

   public ModuleSettingsPanel p() {
      return this.uV;
   }

   public FriendsGridPanel q() {
      return this.Be;
   }

   public ConfigGridPanel r() {
      return this.awl;
   }
}
