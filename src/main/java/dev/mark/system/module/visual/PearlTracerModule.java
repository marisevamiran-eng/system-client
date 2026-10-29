package dev.mark.system.module.visual;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import dev.mark.system.render.BuiltLine3d;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.data.EntityTrailData;
import dev.mark.system.render.BoxWireframeRenderer;
import dev.mark.system.render.builder.Line3dBuilder;

public class PearlTracerModule extends Module {
   private BooleanSetting showTrajectorySetting = new BooleanSetting("Show Trajectory", "Display ender pearl flight path", true);
   private BooleanSetting showLandingPointSetting = new BooleanSetting("Show Landing Point", "Display pearl landing point", true);
   private BooleanSetting showHitboxSetting = new BooleanSetting("Show Hitbox", "Display hitbox at landing point", true);
   private BooleanSetting ignoreOwnPearlsSetting = new BooleanSetting("Ignore Own Pearls", "Don't show trajectory for own pearls", true);
   private SliderSetting lineWidthSetting = new SliderSetting("Line Width", "Trajectory line width", 2.0, 1.0, 4.0, 0.1);
   private ColorSetting trajectoryColorSetting = new ColorSetting("Trajectory Color", "Pearl trajectory line color", new Color(255, 255, 255), true);
   private ColorSetting landingColorSetting = new ColorSetting("Landing Color", "Landing point color", new Color(255, 255, 255), true);
   private final Map<Integer, EntityTrailData> YN = new ConcurrentHashMap<Integer, EntityTrailData>();
   private final Line3dBuilder aeF = new Line3dBuilder();
   private final Line3dBuilder arQ = new Line3dBuilder();
   private final Line3dBuilder po = new Line3dBuilder();

   public PearlTracerModule() {
      super("PearlTracer", "Shows pearl flight trajectory and landing spot", Category.RENDER);
      this.addSettings(
         this.showTrajectorySetting,
         this.showLandingPointSetting,
         this.showHitboxSetting,
         this.ignoreOwnPearlsSetting,
         this.lineWidthSetting,
         this.trajectoryColorSetting,
         this.landingColorSetting
      );
      this.U(this.showTrajectorySetting.getName(), this.lineWidthSetting.getName(), true);
      this.U(this.showTrajectorySetting.getName(), this.trajectoryColorSetting.getName(), true);
      this.U(this.showLandingPointSetting.getName(), this.landingColorSetting.getName(), true);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
      this.YN.clear();
   }

   @Override
   public void onEndTick() {
      if (this.isEnabled() && !this.isNotInWorld()) {
         this.a();
         this.o();
      }
   }

   @Override
   public void onRenderAfterTranslucent(WorldRenderContext worldrendercontext) {
      if (!this.isNotInWorld()) {
         try {
            this.e(worldrendercontext);
         } catch (Exception exception) {
         }
      }
   }

   private void a() {
      Iterable iterable = this.getClientWorld().getEntities();
      HashSet hashset = new HashSet();

      for (Object _e : iterable) {
         Entity entity = (Entity)_e;
         if (entity instanceof EnderPearlEntity enderpearlentity && (!this.ignoreOwnPearlsSetting.getValue() || !this.b(enderpearlentity))) {
            int i = enderpearlentity.getId();
            hashset.add(i);
            EntityTrailData EntityTrailData = this.YN.computeIfAbsent(i, integer -> {
               return new EntityTrailData();
            });
            Vec3d vec3d = enderpearlentity.getPos();
            EntityTrailData.NL = vec3d;
            EntityTrailData.QY = System.currentTimeMillis();
            if (!EntityTrailData.aaP) {
               this.c(enderpearlentity, EntityTrailData);
               EntityTrailData.aaP = true;
            }

            if (!EntityTrailData.awy.isEmpty()) {
               EntityTrailData.yz = EntityTrailData.MR;
               EntityTrailData.MR = this.i(EntityTrailData.awy, vec3d, EntityTrailData.MR);
               EntityTrailData.pl = (int)EntityTrailData.MR;
            }
         }
      }

      this.YN.keySet().retainAll(hashset);
   }

   private boolean b(EnderPearlEntity enderpearlentity) {
      Entity entity = enderpearlentity.getOwner();
      return entity != null && entity.equals(this.getPlayer());
   }

   private void c(EnderPearlEntity enderpearlentity, EntityTrailData EntityTrailData) {
      EntityTrailData.awy.clear();
      EntityTrailData.gU = null;
      Vec3d vec3d = enderpearlentity.getPos();
      Vec3d vec3d1 = enderpearlentity.getVelocity();
      double d0 = 0.99;
      double d1 = 0.03;
      short short1 = 200;
      byte b0 = 100;
      int i = short1 / b0;

      for (int j = 0; j < short1; j++) {
         if (j % i == 0) {
            EntityTrailData.awy.add(vec3d);
         }

         if (this.d(vec3d, vec3d1)) {
            EntityTrailData.gU = vec3d;
            break;
         }

         Vec3d vec3d2 = vec3d1.multiply(d0);
         vec3d1 = vec3d2.add(0.0, -d1, 0.0);
         vec3d = vec3d.add(vec3d1);
         if (vec3d.y < -64.0 || vec3d.squaredDistanceTo(enderpearlentity.getPos()) > 250000.0) {
            break;
         }
      }

      if (EntityTrailData.gU == null && !EntityTrailData.awy.isEmpty()) {
         EntityTrailData.gU = EntityTrailData.awy.getLast();
      }
   }

   private boolean d(Vec3d vec3d, Vec3d vec3d1) {
      Vec3d vec3d2 = vec3d.add(vec3d1);
      RaycastContext raycastcontext = new RaycastContext(vec3d, vec3d2, ShapeType.COLLIDER, FluidHandling.NONE, ShapeContext.absent());
      BlockHitResult blockhitresult = this.getWorld().raycast(raycastcontext);
      return blockhitresult.getType() == Type.BLOCK;
   }

   private void e(WorldRenderContext worldrendercontext) {
      if (!this.YN.isEmpty()) {
         MatrixStack matrixstack = worldrendercontext.matrixStack();
         Vec3d vec3d = worldrendercontext.camera().getPos();
         float f = this.lineWidthSetting.getFloatValue();
         if (this.showTrajectorySetting.getValue()) {
            this.f(matrixstack, vec3d, f);
         }

         if (this.showLandingPointSetting.getValue()) {
            this.g(matrixstack, vec3d, f);
         }

         if (this.showHitboxSetting.getValue()) {
            this.h(matrixstack, vec3d, f);
         }
      }
   }

   private void f(MatrixStack matrixstack, Vec3d vec3d, float f) {
      Color color = this.trajectoryColorSetting.getColor();
      float f1 = color.getRed() / 255.0F;
      float f2 = color.getGreen() / 255.0F;
      float f3 = color.getBlue() / 255.0F;
      float f4 = color.getAlpha() / 255.0F;
      BuiltLine3d builtline3d = this.aeF.a(f).b(f1, f2, f3, f4).l(6).a();

      for (EntityTrailData EntityTrailData : this.YN.values()) {
         if (EntityTrailData.awy.size() >= 2 && EntityTrailData.NL != null) {
            float f5 = EntityTrailData.MR;
            int i = (int)f5;
            float f6 = f5 - i;
            BoxWireframeRenderer BoxWireframeRenderer = builtline3d.h();
            if (i < EntityTrailData.awy.size() - 1) {
               Vec3d vec3d1 = EntityTrailData.awy.get(i);
               Vec3d vec3d2 = EntityTrailData.awy.get(i + 1);
               Vec3d vec3d3 = vec3d1.add(vec3d2.subtract(vec3d1).multiply(f6));
               Vec3d vec3d4 = vec3d3.subtract(vec3d);
               Vec3d vec3d5 = vec3d2.subtract(vec3d);
               BoxWireframeRenderer.b(vec3d4, vec3d5);
            }

            for (int j = i + 1; j < EntityTrailData.awy.size() - 1; j++) {
               Vec3d vec3d6 = EntityTrailData.awy.get(j).subtract(vec3d);
               Vec3d vec3d7 = EntityTrailData.awy.get(j + 1).subtract(vec3d);
               BoxWireframeRenderer.b(vec3d6, vec3d7);
            }

            BoxWireframeRenderer.d(matrixstack, Vec3d.ZERO);
         }
      }
   }

   private void g(MatrixStack matrixstack, Vec3d vec3d, float f) {
      Color color = this.landingColorSetting.getColor();
      float f1 = color.getRed() / 255.0F;
      float f2 = color.getGreen() / 255.0F;
      float f3 = color.getBlue() / 255.0F;
      float f4 = color.getAlpha() / 255.0F;
      BuiltLine3d builtline3d = this.arQ.a(f * 0.8F).b(f1, f2, f3, f4).l(6).a();
      BoxWireframeRenderer BoxWireframeRenderer = builtline3d.h();

      for (EntityTrailData EntityTrailData : this.YN.values()) {
         if (EntityTrailData.gU != null) {
            this.j(BoxWireframeRenderer, EntityTrailData, vec3d);
         }
      }

      BoxWireframeRenderer.d(matrixstack, Vec3d.ZERO);
   }

   private void h(MatrixStack matrixstack, Vec3d vec3d, float f) {
      Color color = this.landingColorSetting.getColor();
      float f1 = color.getRed() / 255.0F;
      float f2 = color.getGreen() / 255.0F;
      float f3 = color.getBlue() / 255.0F;
      float f4 = color.getAlpha() / 255.0F * 0.5F;
      BuiltLine3d builtline3d = this.po.a(f * 0.6F).b(f1, f2, f3, f4).l(4).a();

      for (EntityTrailData EntityTrailData : this.YN.values()) {
         if (EntityTrailData.gU != null) {
            Vec3d vec3d1 = EntityTrailData.gU.subtract(vec3d);
            matrixstack.push();
            matrixstack.translate(vec3d1.x, vec3d1.y, vec3d1.z);
            builtline3d.g(matrixstack, Vec3d.ZERO, 0.6F, 1.8F);
            matrixstack.pop();
         }
      }
   }

   private float i(List<Vec3d> list, Vec3d vec3d, float f) {
      if (list.size() < 2) {
         return 0.0F;
      } else {
         int i = Math.max(0, (int)f);
         double d0 = Double.MAX_VALUE;
         float f1 = f;
         int j = Math.max(0, i - 1);
         int k = Math.min(list.size() - 1, i + 10);

         for (int l = j; l < k; l++) {
            Vec3d vec3d1 = (Vec3d)list.get(l);
            Vec3d vec3d2 = (Vec3d)list.get(l + 1);
            Vec3d vec3d3 = vec3d2.subtract(vec3d1);
            double d1 = vec3d3.lengthSquared();
            if (!(d1 < 1.0E-4)) {
               Vec3d vec3d4 = vec3d.subtract(vec3d1);
               double d2 = vec3d4.dotProduct(vec3d3) / d1;
               d2 = Math.max(0.0, Math.min(1.0, d2));
               Vec3d vec3d5 = vec3d1.add(vec3d3.multiply(d2));
               double d3 = vec3d.squaredDistanceTo(vec3d5);
               if (d3 < d0) {
                  d0 = d3;
                  f1 = l + (float)d2;
               }
            }
         }

         return Math.max(f, f1);
      }
   }

   private void j(BoxWireframeRenderer BoxWireframeRenderer, EntityTrailData EntityTrailData, Vec3d vec3d) {
      Vec3d vec3d1 = EntityTrailData.gU.subtract(vec3d);
      float f = 0.5F;
      double d0 = vec3d1.x;
      double d1 = vec3d1.y;
      double d2 = vec3d1.z;
      BoxWireframeRenderer.b(new Vec3d(d0 - f, d1, d2), new Vec3d(d0 + f, d1, d2));
      BoxWireframeRenderer.b(new Vec3d(d0, d1 - f, d2), new Vec3d(d0, d1 + f, d2));
      BoxWireframeRenderer.b(new Vec3d(d0, d1, d2 - f), new Vec3d(d0, d1, d2 + f));
   }

   private void o() {
      long i = System.currentTimeMillis();
      this.YN.entrySet().removeIf(entry -> {
         return i - entry.getValue().QY > 5000L;
      });
   }

   @Override
   public void onPlayerDeath(PlayerEntity playerentity) {
      super.onPlayerDeath(playerentity);
      if (this.enabledSetting.getValue() && playerentity == this.getPlayer()) {
         this.setEnabled(false);
      }
   }
}
