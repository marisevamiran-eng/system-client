package dev.mark.system.gui.panel;

import dev.mark.system.gui.animation.PanelTransitionAnimator;
import dev.mark.system.gui.Component;
import java.util.List;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltText;
import dev.mark.system.setting.Setting;

import dev.mark.system.gui.model.SettingComponentEntry;
import dev.mark.system.gui.model.SettingRowEntry;
import dev.mark.system.gui.Component;
import dev.mark.system.gui.component.CloseButton;
import dev.mark.system.gui.component.DropdownSelector;
import dev.mark.system.gui.component.KeyBindButtonComponent;
import dev.mark.system.gui.component.ScrollBarComponent;
import dev.mark.system.gui.component.SliderComponent;
import dev.mark.system.gui.model.SettingComponentFactory;
public class SettingsGroupPanel extends AbstractSettingsPanel {
   private static final int Va = 3;
   private static final float arn = 3.0F;
   private static final float Ea = 0.2F;
   public final PanelTransitionAnimator QZ;
   private final Runnable aae;
   private final CloseButton Kh;
   private boolean Aq = false;
   private boolean Cr = false;
   private DropdownSelector nL;

   public SettingsGroupPanel(float f, float f1, String s, List<Setting> list, Runnable runnable) {
      super(f, f1, 286.0F, 201.0F, s, list.size() > 3);
      this.aae = runnable;
      this.QZ = new PanelTransitionAnimator();
      this.QZ.a();
      this.QZ.b();
      this.Kh = new CloseButton(246.0F, 5.0F, this::c);
      this.e(list);
      this.g();
      this.a();
   }

   private void c() {
      if (!this.Aq && this.aae != null) {
         this.Aq = true;
         this.aae.run();
      }
   }

   public void d(Runnable runnable) {
      this.QZ.c(runnable);
   }

   private void e(List<Setting> list) {
      float f = this.Zt != null ? ScrollBarComponent.c() + ScrollBarComponent.e() : 0.0F;

      for (SettingComponentEntry settingEntry : SettingComponentFactory.a(list, 51.0F, f, null)) {
         this.lK.add(new SettingRowEntry(settingEntry.a(), settingEntry.b(), settingEntry.c(), settingEntry.e()));
         if (settingEntry.d() != null) {
            DropdownSelector dropdownSel = settingEntry.d();
            dropdownSel.t(obool -> {
               if (obool) {
                  if (this.nL != null && this.nL != dropdownSel) {
                     this.nL.a(false);
                  }

                  this.nL = dropdownSel;
               } else {
                  if (this.nL == dropdownSel) {
                     this.nL = null;
                  }
               }
            });
         }
      }
   }

   private void f() {
      boolean flag = false;

      for (SettingRowEntry settingRow : this.lK) {
         if (settingRow.MA != null) {
            boolean flag1 = settingRow.MA.isVisible();
            boolean flag2 = settingRow.r();
            if (flag1 != flag2) {
               if (flag1) {
                  settingRow.f();
               } else {
                  settingRow.g(null);
               }

               flag = true;
            }
         }
      }

      if (flag || this.Cr) {
         this.Cr = false;
      }
   }

   private void g() {
      float f = 51.0F;

      for (SettingRowEntry settingRow : this.lK) {
         settingRow.e();
         float f1 = settingRow.a();
         settingRow.n(f);
         if (f1 > 0.0F) {
            f += f1;
         }
      }

      this.Nx = true;
   }

   private void h() {
      boolean flag = false;

      for (SettingRowEntry settingRow : this.lK) {
         if (settingRow.h()) {
            settingRow.e();
            flag = true;
         }
      }

      if (flag) {
         this.g();
      }
   }

   @Override
   protected void a() {
      float f = 0.0F;

      for (SettingRowEntry settingRow : this.lK) {
         if (settingRow.d() || settingRow.h()) {
            float f1 = settingRow.m() + settingRow.a();
            if (settingRow.aoL instanceof DropdownSelector DropdownSelector && DropdownSelector.r()) {
               f1 += DropdownSelector.d();
            }

            if (f1 > f) {
               f = f1;
            }
         }
      }

      this.ayt = Math.max(0.0F, f - this.iP);
      this.Nx = false;
   }

   @Override
   protected void a(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3) {
      this.QZ.f();
      this.f();
      this.h();
      this.b();
      float f4 = f3 * this.QZ.k();
      if (!(f4 < 0.01F)) {
         Matrix4f matrix4f1 = this.QZ.g() ? this.d(matrix4f, f, f1, this.QZ.j(), 201.0F) : matrix4f;
         if (this.QZ.g()) {
            this.QZ.g(matrix4f, f, f1);
         }

         this.hH.a(matrix4f1, f, f1, f4);
         BuiltText builttext = this.k(this.ayW, this.acH, 15.0F, Bz);
         builttext.a(matrix4f1, f + 10.0F, f1 + 12.0F, f4);
         this.asw.a(matrix4f1, f, f1 + 40.0F, f4);
         this.c(matrix4f1, f, f1, i, j, f2, f4);
         this.f(this.Kh, f, f1, i, j, f2, f4, matrix4f1);
         if (this.Zt != null) {
            this.f(this.Zt, f, f1, i, j, f2, f4, matrix4f1);
         }
      }
   }

   public String i(double d0, double d1) {
      if (this.Kh != null && this.Kh.isHovered(d0, d1)) {
         return "Закрыть группу настроек";
      } else {
         for (SettingRowEntry settingRow : this.lK) {
            if (settingRow.d() && !(settingRow.b() < 0.01F)) {
               float f = this.atW + settingRow.agp - this.aoj;
               Component component = settingRow.aoL;
               component.atW = f;
               if (component.isHovered(d0, d1)) {
                  if (settingRow.MA != null && settingRow.MA.getDisplayDescription() != null && !settingRow.MA.getDisplayDescription().isEmpty()) {
                     return settingRow.MA.getDisplayDescription();
                  }

                  if (settingRow.MA != null) {
                     return settingRow.MA.getDisplayName();
                  }

                  return settingRow.atb;
               }
            }
         }

         if (d0 >= this.it && d0 <= this.it + this.qd && d1 >= this.atW && d1 <= this.atW + this.aem) {
            String s = this.acH;
            return "Группа настроек: " + s;
         } else {
            return null;
         }
      }
   }

   @Override
   protected void e(Matrix4f matrix4f, float f, float f1, SettingRowEntry SettingRowEntry, int i, int j, float f2, float f3) {
      if (SettingRowEntry.d()) {
         float f4 = f3 * SettingRowEntry.b();
         if (!(f4 < 0.01F)) {
            float f5 = f1 + SettingRowEntry.agp - this.aoj;
            Component component = SettingRowEntry.aoL;
            component.it = f + component.getX();
            component.atW = f5;
            String s = SettingRowEntry.MA != null ? SettingRowEntry.MA.getDisplayName() : SettingRowEntry.atb;
            if ((component instanceof SliderComponent || component instanceof DropdownSelector) && s.length() > 25) {
               this.j(matrix4f, f, f5, s, f4);
            } else {
               BuiltText builttext = this.k(this.nO, s, 13.0F, EaseLinear);
               builttext.a(matrix4f, f + 10.0F, f5 + 3.0F - 1.0F, f4);
            }

            component.render(matrix4f, f + component.getX(), f5, i, j, f2, f4);
         }
      }
   }

   private void j(Matrix4f matrix4f, float f, float f1, String s, float f2) {
      this.g(matrix4f, f, f1, s, f2, 25);
   }

   @Override
   protected boolean b(double d0, double d1, int i) {
      if ((!this.QZ.g() || !(this.QZ.k() < 0.5F)) && !this.Aq) {
         if (!this.i(this.Kh, d0, d1, i) && !this.i(this.Zt, d0, d1, i)) {
            for (SettingRowEntry settingRow : this.lK) {
               if (settingRow.d() && !(settingRow.b() < 0.01F)) {
                  settingRow.aoL.it = this.it + settingRow.aoL.getX();
                  settingRow.aoL.atW = this.atW + settingRow.agp - this.aoj;
                  if (settingRow.aoL.mouseClicked(d0, d1, i)) {
                     return true;
                  }
               }
            }

            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   protected boolean f(int i, int j, int k) {
      for (SettingRowEntry settingRow : this.lK) {
         if (settingRow.aoL instanceof KeyBindButtonComponent KeyBindButtonComponent && KeyBindButtonComponent.e()) {
            return KeyBindButtonComponent.keyPressed(i, j, k);
         }
      }

      return false;
   }

   @Override
   protected boolean e(double d0, double d1, double d2, double d3) {
      if (this.nL != null && this.nL.r()) {
         for (SettingRowEntry settingRow : this.lK) {
            if (settingRow.aoL == this.nL) {
               settingRow.aoL.it = this.it + settingRow.aoL.getX();
               settingRow.aoL.atW = this.atW + settingRow.agp - this.aoj;
               if (settingRow.aoL.mouseScrolled(d0, d1, d2, d3)) {
                  return true;
               }
               break;
            }
         }
      }

      if (this.Zt == null) {
         return false;
      } else {
         float f3 = this.atW + 40.0F;
         float f4 = this.atW + this.aem;
         if (d0 >= this.it && d0 <= this.it + this.qd && d1 >= f3 && d1 <= f4) {
            float f = (float)(-d3 * 0.2F);
            float f1 = this.Zt.f() + f;
            f1 = Math.max(0.0F, Math.min(f1, 1.0F));
            this.Zt.a(f1);
            float f2 = f1 * this.ayt;
            this.oj.a(f2);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   protected boolean c(double d0, double d1, int i) {
      if (this.Kh != null) {
         this.Kh.mouseReleased(d0, d1, i);
      }

      return super.c(d0, d1, i);
   }
}
