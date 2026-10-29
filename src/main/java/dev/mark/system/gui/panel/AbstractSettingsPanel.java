package dev.mark.system.gui.panel;

import dev.mark.system.gui.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltLine2d;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.BuiltText;

import dev.mark.system.gui.model.SettingRowEntry;
import dev.mark.system.render.ScissorStack;
import dev.mark.system.render.builder.Line2dBuilder;
import dev.mark.system.gui.Component;
import dev.mark.system.gui.animation.SmoothFloat;
import dev.mark.system.gui.component.DropdownSelector;
import dev.mark.system.gui.component.ScrollBarComponent;
import dev.mark.system.render.builder.RectangleBuilder;
import dev.mark.system.util.RotationManager;
public abstract class AbstractSettingsPanel extends Component {
   private static final List<String> cC = new ArrayList<String>(4);
   protected final Map<String, String[]> aqJ = new HashMap<String, String[]>();
   protected static final int kQ = 25;
   protected static final float aAj = -2.0F;
   protected static final float Br = 12.0F;
   protected static final float wm = 286.0F;
   protected static final float asO = 201.0F;
   protected static final float asZ = 143.0F;
   protected static final float gc = 100.5F;
   protected static final float avM = 12.0F;
   protected static final float Nd = 40.0F;
   protected static final float axK = 2.0F;
   protected static final float Vu = 51.0F;
   protected static final float Nb = 0.01F;
   protected static final float ahb = 2.0F;
   public final String acH;
   protected final BuiltRectangle hH;
   protected final BuiltLine2d asw;
   protected final List<SettingRowEntry> lK;
   protected final ScrollBarComponent Zt;
   protected final float iP;
   protected final SmoothFloat oj;
   public float aoj = 0.0F;
   protected float ayt = 0.0F;
   protected boolean Nx = true;
   public float uQ = 1.0F;
   public float uv = 0.0F;
   public float Cc = 0.0F;
   private final Matrix4f UM = new Matrix4f();

   protected AbstractSettingsPanel(float f, float f1, float f2, float f3, String s, boolean flag) {
      super(f, f1, f2, f3);
      this.acH = s;
      this.iP = f3 - 40.0F;
      this.hH = new RectangleBuilder().a(f2, f3).b(8.0F).a();
      this.asw = new Line2dBuilder().a(f2).b(1.0F).d().a();
      this.Zt = flag ? new ScrollBarComponent(f2 - ScrollBarComponent.e() - ScrollBarComponent.c(), 40.0F + ScrollBarComponent.d()) : null;
      this.lK = new ArrayList<SettingRowEntry>();
      this.oj = new SmoothFloat();
      this.oj.f(0.0F);
   }

   protected void a() {
      float f = 0.0F;

      for (SettingRowEntry SettingRowEntry : this.lK) {
         float f1 = SettingRowEntry.m() + SettingRowEntry.aoL.getHeight();
         if (SettingRowEntry.aoL instanceof DropdownSelector DropdownSelector && DropdownSelector.r()) {
            f1 += DropdownSelector.d();
         }

         if (f1 > f) {
            f = f1;
         }
      }

      this.ayt = Math.max(0.0F, f - this.iP);
      this.Nx = false;
   }

   protected void b() {
      if (this.Zt != null) {
         if (this.Nx) {
            this.a();
         }

         float f = this.Zt.f() * this.ayt;
         this.oj.a(f);
         this.oj.c();
         this.aoj = Math.max(0.0F, Math.min(this.oj.d(), this.ayt));
      }
   }

   protected void c(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3) {
      float f4 = f1 + 40.0F;
      float f5 = f1 + this.aem - 2.0F;
      ScissorStack.a(0.0F, 42.0F, this.qd, this.aem - 40.0F - 2.0F - 2.0F, this.uQ, this.uv + f * this.uQ, this.Cc + f1 * this.uQ);

      for (SettingRowEntry SettingRowEntry : this.lK) {
         this.e(matrix4f, f, f1, SettingRowEntry, i, j, f2, f3);
         if (SettingRowEntry.aoL instanceof DropdownSelector RotationManager) {
            float f11 = f1 + SettingRowEntry.m() - this.aoj;
            RotationManager.atW = f11;
            RotationManager.u(true);
            if (RotationManager.r() && this.Zt != null) {
               float f6 = f11 + RotationManager.getHeight() + 5.0F;
               float f7 = f6 + RotationManager.e();
               if (f7 > f5) {
                  float f8 = f7 - f5;
                  float f9 = this.aoj + f8;
                  f9 = Math.min(f9, this.ayt);
                  float f10 = this.ayt > 0.0F ? f9 / this.ayt : 0.0F;
                  this.Zt.a(f10);
                  this.oj.a(f9);
               }

               if (f6 < f4) {
                  float f15 = f4 - f6;
                  float f16 = this.aoj - f15;
                  f16 = Math.max(0.0F, f16);
                  float f17 = this.ayt > 0.0F ? f16 / this.ayt : 0.0F;
                  this.Zt.a(f17);
                  this.oj.a(f16);
               }
            }
         }
      }

      ScissorStack.b();

      for (SettingRowEntry wyjx : this.lK) {
         if (wyjx.aoL instanceof DropdownSelector RotationManager && (RotationManager.r() || RotationManager.b())) {
            RotationManager.u(false);
            float f12 = f1 + wyjx.m() - this.aoj;
            RotationManager.it = f + RotationManager.getX();
            RotationManager.atW = f12;
            RotationManager.cM = this.uQ;
            RotationManager.Lp = this.uv;
            RotationManager.Kp = this.Cc;
            float f13 = f12 + RotationManager.getHeight() + 5.0F;
            float f14 = f13 + RotationManager.e();
            if (f13 < f5 && f14 > f4) {
               RotationManager.l(matrix4f, f + RotationManager.getX(), f12, f3);
            }
         }
      }
   }

   protected Matrix4f d(Matrix4f matrix4f, float f, float f1, float f2, float f3) {
      if (f2 == 1.0F) {
         return matrix4f;
      } else {
         float f4 = f + this.qd * 0.5F;
         float f5 = f1 + f3 * 0.5F;
         this.UM.set(matrix4f);
         this.UM.translate(f4, f5, 0.0F);
         this.UM.scale(f2, f2, 1.0F);
         this.UM.translate(-f4, -f5, 0.0F);
         return this.UM;
      }
   }

   protected void e(Matrix4f matrix4f, float f, float f1, SettingRowEntry SettingRowEntry, int i, int j, float f2, float f3) {
      float f4 = f1 + SettingRowEntry.m() - this.aoj;
      Component component = SettingRowEntry.aoL;
      component.it = f + component.getX();
      component.atW = f4;
      BuiltText builttext = this.k(this.nO, SettingRowEntry.atb, 13.0F, EaseLinear);
      builttext.a(matrix4f, f + 10.0F, f4, f3);
      component.render(matrix4f, f + component.getX(), f4, i, j, f2, f3);
   }

   protected void f(Component component, float f, float f1, int i, int j, float f2, float f3, Matrix4f matrix4f) {
      if (component != null) {
         float f4 = f + component.getX();
         float f5 = f1 + component.getY();
         component.it = f4;
         component.atW = f5;
         component.render(matrix4f, f4, f5, i, j, f2, f3);
      }
   }

   protected void g(Matrix4f matrix4f, float f, float f1, String s, float f2, int i) {
      String[] astring = this.aqJ.get(s);
      if (astring == null) {
         astring = h(s, i);
         this.aqJ.put(s, astring);
      }

      float f3 = f1 + -2.0F;

      for (int j = 0; j < astring.length; j++) {
         BuiltText builttext = this.k(this.nO, astring[j], 13.0F, EaseLinear);
         builttext.a(matrix4f, f + 10.0F, f3, f2);
         f3 += 12.0F;
      }
   }

   private static String[] h(String s, int i) {
      String[] astring = s.split(" ");
      cC.clear();
      StringBuilder stringbuilder = new StringBuilder();

      for (String s1 : astring) {
         if (stringbuilder.length() + s1.length() + 1 > i) {
            if (!stringbuilder.isEmpty()) {
               cC.add(stringbuilder.toString());
               stringbuilder.setLength(0);
               stringbuilder.append(s1);
            } else {
               cC.add(s1);
            }
         } else {
            if (!stringbuilder.isEmpty()) {
               stringbuilder.append(" ");
            }

            stringbuilder.append(s1);
         }
      }

      if (!stringbuilder.isEmpty()) {
         cC.add(stringbuilder.toString());
      }

      return cC.<String>toArray(new String[0]);
   }

   protected boolean i(Component component, double d0, double d1, int i) {
      if (component == null) {
         return false;
      } else {
         float f = this.it + component.getX();
         float f1 = this.atW + component.getY();
         component.it = f;
         component.atW = f1;
         return d0 >= f && d0 <= f + component.getWidth() && d1 >= f1 && d1 <= f1 + component.getHeight() && component.mouseClicked(d0, d1, i);
      }
   }

   @Override
   protected boolean c(double d0, double d1, int i) {
      if (this.Zt != null) {
         this.Zt.mouseReleased(d0, d1, i);
      }

      for (SettingRowEntry SettingRowEntry : this.lK) {
         if (SettingRowEntry.aoL.mouseReleased(d0, d1, i)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected boolean d(double d0, double d1, int i, double d2, double d3) {
      if (this.Zt != null && this.Zt.mouseDragged(d0, d1, i, d2, d3)) {
         return true;
      } else {
         for (SettingRowEntry SettingRowEntry : this.lK) {
            if (SettingRowEntry.aoL.mouseDragged(d0, d1, i, d2, d3)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   protected boolean e(double d0, double d1, double d3, double d2) {
      if (this.Zt == null) {
         return false;
      } else {
         float f = this.atW + 40.0F;
         float f1 = this.atW + this.aem;
         if (d0 >= this.it && d0 <= this.it + this.qd && d1 >= f && d1 <= f1) {
            float f2 = (float)(-d2 * 0.05F);
            float f3 = this.Zt.f() + f2;
            f3 = Math.max(0.0F, Math.min(f3, 1.0F));
            this.Zt.a(f3);
            return true;
         } else {
            return false;
         }
      }
   }
}
