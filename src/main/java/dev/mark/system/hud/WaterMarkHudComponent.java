package dev.mark.system.hud;

import dev.mark.system.core.ClientMain;
import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import dev.mark.system.module.client.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.BuiltText;
import dev.mark.system.render.RectangleCache;
import dev.mark.system.util.TPSTracker;
import dev.mark.system.gui.animation.OpacityAnimation;

public class WaterMarkHudComponent extends HudComponent {
   private static final float aew = 26.0F;
   private static final float kw = 13.0F;
   private static final float Qb = 5.0F;
   private static final float aqB = 5.0F;
   private static final float CB = 8.0F;
   private static final float Mn = 10.0F;
   private static final float IK = 15.0F;
   private String aqr = "";
   private String Zq = "";
   private String da = "";
   private String Nl = "";
   private boolean dK = true;
   private boolean xH = true;
   private boolean rQ = true;
   private boolean vg = true;
   private float avq = -1.0F;
   private long UZ = 0L;
   private float Ih = 1.0F;
   private float apA = 1.0F;
   private float WT = 1.0F;
   private float YK = 1.0F;
   private MinecraftClient mc = MinecraftClient.getInstance();
   private static final DateTimeFormatter dG = DateTimeFormatter.ofPattern("HH:mm");

   public WaterMarkHudComponent(float f, float f1) {
      super(f, f1, "", "watermark");
   }

   @Override
   public boolean isAlwaysVisible() {
      return true;
   }

   @Override
   protected float calculateContentHeight() {
      return 0.0F;
   }

   @Override
   protected void renderContent(Matrix4f matrix4f, float f, float f1, float f2) {
   }

   private void a() {
      HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
      if (hudmodule != null) {
         this.dK = hudmodule.d().getValue();
         this.xH = hudmodule.e().getValue();
         this.rQ = hudmodule.f().getValue();
         this.vg = hudmodule.g().getValue();
      }
   }

   private void b() {
      long i = System.nanoTime();
      if (this.UZ == 0L) {
         this.UZ = i;
      } else {
         float f = (float)(i - this.UZ) / 1.0E9F;
         this.UZ = i;
         float f1 = 1.0F - (float)Math.exp(-f * 15.0F);
         this.Ih = e(this.Ih, this.dK ? 1.0F : 0.0F, f1);
         this.apA = e(this.apA, this.xH ? 1.0F : 0.0F, f1);
         this.WT = e(this.WT, this.rQ ? 1.0F : 0.0F, f1);
         this.YK = e(this.YK, this.vg ? 1.0F : 0.0F, f1);
         float f2 = this.c();
         float f3 = 1.0F - (float)Math.exp(-f * 10.0F);
         if (this.avq < 0.0F) {
            this.avq = f2;
         } else {
            this.avq = e(this.avq, f2, f3);
            if (Math.abs(f2 - this.avq) < 0.5F) {
               this.avq = f2;
            }
         }
      }
   }

   private float c() {
      float f = 5.0F + this.ew.c("System", 13.0F);
      f += this.d(this.aqr, this.Ih);
      f += this.d(this.Zq, this.apA);
      f += this.d(this.da, this.WT);
      f += this.d(this.Nl, this.YK);
      return f + 5.0F;
   }

   private float d(String s, float f) {
      return f < 0.01F ? 0.0F : (5.0F + this.ew.c(s, 13.0F)) * f;
   }

   private static float e(float f, float f1, float f2) {
      return f + (f1 - f) * f2;
   }

   @Override
   public void render(Matrix4f matrix4f, float f) {
      this.initialize();
      this.a();
      this.g();
      this.b();
      this.width = this.avq;
      BuiltRectangle builtrectangle = RectangleCache.b(this.avq + 1.0F, 26.0F, 8.0F);
      builtrectangle.a(matrix4f, this.x, this.y, f);
      float f1 = this.x + 5.0F;
      float f2 = this.y + 6.5F - 1.0F;
      BuiltText builttext = this.w(this.ew, "System", 13.0F, Color.WHITE);
      builttext.a(matrix4f, f1, f2, f);
      f1 += this.ew.c("System", 13.0F);
      f1 = this.f(matrix4f, f1, f2, this.aqr, this.Ih, f);
      f1 = this.f(matrix4f, f1, f2, this.Zq, this.apA, f);
      f1 = this.f(matrix4f, f1, f2, this.da, this.WT, f);
      this.f(matrix4f, f1, f2, this.Nl, this.YK, f);
   }

   private float f(Matrix4f matrix4f, float f, float f1, String s, float f2, float f3) {
      if (f2 < 0.01F) {
         return f;
      } else {
         float f4 = 5.0F + this.ew.c(s, 13.0F);
         float f5 = f4 * f2;
         float f6 = f + 5.0F * f2;
         BuiltText builttext = this.w(this.ew, s, 13.0F, Color.WHITE);
         builttext.a(matrix4f, f6, f1, f3 * f2);
         return f + f5;
      }
   }

   private void g() {
      int j = this.mc.getCurrentFps();
      this.aqr = j + " Fps";
      int i = 0;
      if (this.mc.player != null && this.mc.player.networkHandler != null) {
         PlayerListEntry playerlistentry = this.mc.player.networkHandler.getPlayerListEntry(this.mc.player.getUuid());
         if (playerlistentry != null) {
            i = playerlistentry.getLatency();
         }
      }

      this.Zq = i + " Ping";
      float f = TPSTracker.getInstance().getTps();
      this.da = f + " TPS";
      this.Nl = LocalTime.now().format(dG);
   }

   @Override
   public boolean mouseClicked(double d0, double d1, int i) {
      if (i == 0 && d0 >= this.x && d0 <= this.x + this.avq && d1 >= this.y && d1 <= this.y + 26.0F) {
         this.dragging = true;
         this.dragOffsetX = d0 - this.x;
         this.dragOffsetY = d1 - this.y;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isHovered(double d0, double d1) {
      return d0 >= this.x && d0 <= this.x + this.avq && d1 >= this.y && d1 <= this.y + 26.0F;
   }
}
