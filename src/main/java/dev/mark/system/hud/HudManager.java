package dev.mark.system.hud;

import dev.mark.system.core.ClientMain;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import dev.mark.system.module.client.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltLiquidGlass;

public class HudManager {
   private static HudManager instance;
   private final List<HudEntry> entries = new ArrayList<HudEntry>();
   private final HudLayoutManager layoutManager = new HudLayoutManager();
   private float aqN = 1.0F;
   private float aeU = -1.0F;
   private long zx = 0L;
   private static final long zL = 500000000L;
   private boolean NG = false;

   private HudManager() {
      this.c();
   }

   public void a(boolean flag) {
      this.NG = flag;
      if (!flag) {
         for (HudEntry hudentry1 : this.entries) {
            if (hudentry1.component != null) {
               hudentry1.component.ah(false);
            }
         }
      } else {
         for (HudEntry hudentry : this.entries) {
            HudComponent hudcomponent = hudentry.getComponent();
            boolean flag1 = hudentry.isVisible();
            boolean flag2 = hudcomponent.isAlwaysVisible();

             if (flag1 || flag2) {
               hudcomponent.initialize();
               hudcomponent.recalculateHeight();
               float f = hudcomponent.getTotalHeight();
               boolean flag3 = f > 0.0F;
               boolean flag4 = !flag3 && flag2;
               hudcomponent.ah(flag4);
            }
         }
      }
   }

   public static HudManager b() {
      if (instance == null) {
         instance = new HudManager();
      }

      return instance;
   }

   private void c() {
      this.d(() -> {
         return new WaterMarkHudComponent(10.0F, 10.0F);
      }, () -> {
         HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
         return hudmodule != null && hudmodule.isEnabled() && hudmodule.b().getValue();
      });
      this.d(() -> {
         return new KeyBindsHudComponent(10.0F, 200.0F);
      }, () -> {
         HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
         return hudmodule != null && hudmodule.isEnabled() && hudmodule.h().getValue();
      });
      this.d(() -> {
         return new StaffListHudComponent(10.0F, 400.0F);
      }, () -> {
         HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
         return hudmodule != null && hudmodule.isEnabled() && hudmodule.j().getValue();
      });
      this.d(() -> {
         return new PotionsHudComponent(10.0F, 580.0F);
      }, () -> {
         HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
         return hudmodule != null && hudmodule.isEnabled() && hudmodule.k().getValue();
      });
      this.d(() -> {
         return new ArmorHudComponent(10.0F, 70.0F);
      }, () -> {
         HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
         return hudmodule != null && hudmodule.isEnabled() && hudmodule.l().getValue();
      });
      this.d(() -> {
         return new TargetHudComponent(1665.0F, 300.0F);
      }, () -> {
         HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
         return hudmodule != null && hudmodule.isEnabled() && hudmodule.m().getValue();
      });
   }

   public void d(Supplier<HudComponent> supplier, Supplier<Boolean> supplier1) {
      this.entries.add(new HudEntry(supplier, supplier1));
   }

   public void e(DrawContext drawcontext) {
      this.i();
      long i = System.nanoTime();
      if (i - this.zx > 500000000L) {
         this.f();
         this.zx = i;
      }

      this.layoutManager.d();
      drawcontext.getMatrices().push();
      drawcontext.getMatrices().scale(this.aqN, this.aqN, 1.0F);
      Matrix4f matrix4f = drawcontext.getMatrices().peek().getPositionMatrix();

      for (HudEntry hudentry : this.entries) {
         HudComponent hudcomponent = hudentry.getComponent();
         boolean flag = hudentry.isVisible();
         boolean flag1 = hudcomponent.isAlwaysVisible();
          if (flag || (this.NG && flag1)) {
            hudcomponent.render(matrix4f, 1.0F);
         }
      }

      drawcontext.getMatrices().pop();
   }

   private void f() {
      ArrayList arraylist = new ArrayList();

      for (HudEntry hudentry : this.entries) {
         HudComponent hudcomponent = hudentry.getComponent();
         boolean flag = hudentry.isVisible();
         boolean flag1 = hudcomponent.isAlwaysVisible();
          if (flag || (this.NG && flag1)) {
            arraylist.add(hudcomponent);
         }
      }

      this.layoutManager.a(arraylist);
      this.layoutManager.b();
   }

   public boolean g(double d0, double d1, int i) {
      double d2 = d0 / this.aqN;
      double d3 = d1 / this.aqN;

      for (HudEntry hudentry : this.entries) {
         HudComponent hudcomponent = hudentry.getComponent();
         boolean flag = hudentry.isVisible();
         boolean flag1 = hudcomponent.isAlwaysVisible();
         if ((flag || this.NG && flag1) && hudcomponent.mouseClicked(d2, d3, i)) {
            return true;
         }
      }

      return false;
   }

   public boolean h(double d0, double d1, int i) {
      double d2 = d0 / this.aqN;
      double d3 = d1 / this.aqN;

      for (HudEntry hudentry : this.entries) {
         HudComponent hudcomponent = hudentry.getComponent();
         boolean flag = hudentry.isVisible();
         boolean flag1 = hudcomponent.isAlwaysVisible();
         if ((flag || this.NG && flag1) && hudcomponent.mouseReleased(d2, d3, i)) {
            this.f();
            return true;
         }
      }

      return false;
   }

   private void i() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      int i = minecraftclient.getWindow().getWidth();
      int j = minecraftclient.getWindow().getHeight();
      double d0 = minecraftclient.getWindow().getScaleFactor();
      float f = i / 1920.0F;
      this.aqN = (float)(f / d0);
      float f1 = i > 0 ? j * 1920.0F / i : 1080.0F;
      if (this.aeU > 0.0F && Math.abs(f1 - this.aeU) > 1.0F) {
         this.j(this.aeU, f1);
      }

      this.aeU = f1;
   }

   private void j(float f, float f1) {
      float f2 = f1 / f;

      for (HudEntry hudentry : this.entries) {
         if (hudentry.component != null && hudentry.component.Q()) {
            HudComponent hudcomponent = hudentry.component;
            float f3 = Math.max(0.0F, Math.min(hudcomponent.getSavedX(), 1920.0F - hudcomponent.getWidth()));
            float f4 = hudcomponent.getSavedY() * f2;
            f4 = Math.max(0.0F, Math.min(f4, f1 - hudcomponent.getTotalHeight()));
            hudcomponent.setPosition(f3, f4);
         }
      }
   }

   public float k() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      int i = minecraftclient.getWindow().getWidth();
      int j = minecraftclient.getWindow().getHeight();
      return i == 0 ? 1080.0F : j * 1920.0F / i;
   }

   public void l() {
      for (HudEntry hudentry : this.entries) {
         hudentry.component = null;
      }

      this.layoutManager.e();
      this.zx = 0L;
      this.aeU = -1.0F;
   }

   public void m() {
      for (HudEntry hudentry : this.entries) {
         if (hudentry.component != null) {
            hudentry.component.savePosition();
         }
      }
   }

   public List<HudEntry> n() {
      return this.entries;
   }

   public HudLayoutManager o() {
      return this.layoutManager;
   }

   public boolean p() {
      return this.NG;
   }
}
