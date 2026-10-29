package dev.mark.system.module.particles;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.data.WaypointData;
import dev.mark.system.render.CameraSnapshot;
import dev.mark.system.render.WorldRenderHelper;
import dev.mark.system.render.WorldRenderUtils;
import dev.mark.system.render.particle.ParticleState;
import dev.mark.system.util.MathUtil;

public class ParticlesModule extends Module {
   private ColorSetting colorSetting = new ColorSetting("Color", "1", new Color(255, 255, 255));
   private ListSetting particleTypeSetting = new ListSetting(
      "Тип частиц", "1", List.<String>of("Кубы", "Треугольники", "Шарики", "Кометы"), List.<String>of("Кубы"), false
   );
   private SliderSetting maxParticlesSetting = new SliderSetting("Макс. частиц", "1", 100.0, 10.0, 200.0, 10.0);
   private SliderSetting spawnRadiusSetting = new SliderSetting("Radius спавна", "1", 20.0, 5.0, 50.0, 5.0);
   private SliderSetting lifetimeSetting = new SliderSetting("Время жизни (ms)", "1", 3000.0, 500.0, 10000.0, 100.0);
   private SliderSetting sizeSetting = new SliderSetting("Size", "1", 0.2F, 0.05F, 1.0, 0.05F);
   private SliderSetting glowAlphaSetting = new SliderSetting("Прозрачность свечения", "1", 0.4F, 0.0, 1.0, 0.1F);
   private SliderSetting glowSizeSetting = new SliderSetting("Size свечения", "1", 4.0, 1.0, 10.0, 0.5);
   private final List<ParticleState> pW = new ArrayList<ParticleState>();
   private long aoF = 0L;
   private final MatrixStack zi = new MatrixStack();
   private static final Box app = new Box(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5);

   public ParticlesModule() {
      super("Партислез", "Создает летающие анимированные кубы вокруг игрока", Category.PARTICLES);
      this.addSettings(
         this.colorSetting,
         this.particleTypeSetting,
         this.maxParticlesSetting,
         this.spawnRadiusSetting,
         this.lifetimeSetting,
         this.sizeSetting,
         this.glowAlphaSetting,
         this.glowSizeSetting
      );
   }

   @Override
   public void onEnable() {
      this.pW.clear();
      this.aoF = System.nanoTime();
   }

   @Override
   public void onDisable() {
      this.pW.clear();
   }

   @Override
   public void onEndTick() {
      if (this.isNotInWorld()) {
         this.pW.clear();
      } else {
         int i = (int)this.maxParticlesSetting.getValue();
         float f = this.spawnRadiusSetting.getFloatValue();
         float f1 = this.sizeSetting.getFloatValue();
         String s = this.particleTypeSetting.getFirst();
         if (this.pW.size() < i) {
            if (s.equals("Кометы")) {
               Vec3d vec3d = this.getPlayer().getPos().add(MathUtil.e(-f, f), MathUtil.f(8.0, 14.0), MathUtil.e(-f, f));
               Vec3d vec3d1 = new Vec3d(MathUtil.f(-0.08, 0.08), MathUtil.f(-0.25, -0.15), MathUtil.f(-0.08, 0.08));
               ParticleState ParticleState = new ParticleState(vec3d, Vec3d.ZERO, vec3d1, Vec3d.ZERO, (long)this.lifetimeSetting.getValue(), MathUtil.e(f1 * 0.9F, f1 * 1.1F), "comet");
               Vec3d vec3d2 = vec3d1.normalize().multiply(-1.0);

               for (int j = ParticleState.Qs - 1; j >= 0; j--) {
                  ParticleState.asV.add(vec3d.add(vec3d2.multiply(j * 0.15)));
               }

               this.pW.add(ParticleState);
               return;
            }

            this.pW
               .add(
                  new ParticleState(
                     this.getPlayer().getPos().add(MathUtil.e(-f, f), MathUtil.f(0.0, 5.0), MathUtil.e(-f, f)),
                     Vec3d.ZERO,
                     new Vec3d(MathUtil.f(-1.0, 1.0), MathUtil.f(0.0, 2.0), MathUtil.f(-1.0, 1.0)),
                     new Vec3d(MathUtil.f(-1.0, 1.0), MathUtil.f(-1.0, 1.0), MathUtil.f(-1.0, 1.0)),
                     (long)this.lifetimeSetting.getValue(),
                     MathUtil.e(f1 * 0.9F, f1 * 1.1F)
                  )
               );
         }
      }
   }

   @Override
   public void onRenderEnd(WorldRenderContext worldrendercontext) {
      if (!this.isNotInWorld() && !this.pW.isEmpty()) {
         long i = System.nanoTime();
         float f = Math.min((float)(i - this.aoF) / (float)NANOS_PER_SECOND, 0.1F);
         this.aoF = i;
         long j = System.currentTimeMillis();
         String s = this.particleTypeSetting.getFirst();

         for (int k = this.pW.size() - 1; k >= 0; k--) {
            ParticleState ParticleState = this.pW.get(k);
            if (j - ParticleState.Yx <= ParticleState.Vm && (!s.equals("Кометы") || !(ParticleState.lW < this.getPlayer().getY() + 5.0))) {
               ParticleState.a(f);
            } else {
               this.pW.remove(k);
            }
         }

         if (!this.pW.isEmpty()) {
            CameraSnapshot cameraSnapshot = CameraSnapshot.a(this.getClient());
            if (!s.equals("Кометы")) {
               this.a(cameraSnapshot, j);
            }

            switch (s) {
               case "Кубы":
                  this.b(cameraSnapshot, j);
                  return;
               case "Треугольники":
                  this.c(cameraSnapshot, j);
                  return;
               case "Кометы":
                  this.d(cameraSnapshot, j);
            }
         }
      }
   }

   private void a(CameraSnapshot CameraSnapshot, long i) {
      WorldRenderUtils.d(this.glowTexture, this.getClient());
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      float f = this.sizeSetting.getFloatValue() * this.glowSizeSetting.getFloatValue();
      boolean flag = false;

      for (ParticleState ParticleState : this.pW) {
         float f1 = e(ParticleState, i);
         if (!(f1 > 1.0F)) {
            float f2 = f(f1);
            if (!(f2 <= 0.01F) && WorldRenderUtils.a(CameraSnapshot, this.getWorld(), this.getPlayer(), ParticleState.Hg, ParticleState.lW, ParticleState.ath)) {
               float[] afloat = WorldRenderUtils.j(this.colorSetting.getColor(), f2 * this.glowAlphaSetting.getFloatValue());
               float f3 = f * ParticleState.afi;
               WorldRenderUtils.b(this.zi, CameraSnapshot, ParticleState.Hg, ParticleState.lW, ParticleState.ath);
               WorldRenderUtils.g(bufferbuilder, this.zi, f3, afloat[0], afloat[1], afloat[2], afloat[3]);
               this.zi.pop();
               flag = true;
            }
         }
      }

      h(bufferbuilder, flag);
      WorldRenderUtils.f();
   }

   private void b(CameraSnapshot CameraSnapshot, long i) {
      WorldRenderUtils.e();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
      boolean flag = false;

      for (ParticleState ParticleState : this.pW) {
         float f = e(ParticleState, i);
         if (!(f > 1.0F)) {
            float f1 = f(f);
            if (!(f1 <= 0.01F) && WorldRenderUtils.a(CameraSnapshot, this.getWorld(), this.getPlayer(), ParticleState.Hg, ParticleState.lW, ParticleState.ath)) {
               float[] afloat = WorldRenderUtils.j(this.colorSetting.getColor(), f1);
               WorldRenderUtils.c(this.zi, CameraSnapshot, ParticleState.Hg, ParticleState.lW, ParticleState.ath, ParticleState.atT, ParticleState.QV, ParticleState.hS, ParticleState.afi);
               Matrix4f matrix4f = this.zi.peek().getPositionMatrix();
               WorldRenderHelper.s(
                  bufferbuilder,
                  matrix4f,
                  (float)app.minX,
                  (float)app.minY,
                  (float)app.minZ,
                  (float)app.maxX,
                  (float)app.maxY,
                  (float)app.maxZ,
                  afloat[0],
                  afloat[1],
                  afloat[2],
                  afloat[3]
               );
               WorldRenderHelper.t(
                  bufferbuilder,
                  matrix4f,
                  (float)app.minX,
                  (float)app.minY,
                  (float)app.minZ,
                  (float)app.maxX,
                  (float)app.maxY,
                  (float)app.maxZ,
                  afloat[0],
                  afloat[1],
                  afloat[2],
                  afloat[3]
               );
               this.zi.pop();
               flag = true;
            }
         }
      }

      h(bufferbuilder, flag);
      WorldRenderUtils.f();
   }

   private void c(CameraSnapshot CameraSnapshot, long i) {
      WorldRenderUtils.e();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
      boolean flag = false;

      for (ParticleState ParticleState : this.pW) {
         float f = e(ParticleState, i);
         if (!(f > 1.0F)) {
            float f1 = f(f);
            if (!(f1 <= 0.01F) && WorldRenderUtils.a(CameraSnapshot, this.getWorld(), this.getPlayer(), ParticleState.Hg, ParticleState.lW, ParticleState.ath)) {
               float[] afloat = WorldRenderUtils.j(this.colorSetting.getColor(), f1);
               WorldRenderUtils.c(this.zi, CameraSnapshot, ParticleState.Hg, ParticleState.lW, ParticleState.ath, ParticleState.atT, ParticleState.QV, ParticleState.hS, ParticleState.afi);
               WorldRenderHelper.w(bufferbuilder, this.zi.peek().getPositionMatrix(), afloat[0], afloat[1], afloat[2], afloat[3]);
               this.zi.pop();
               flag = true;
            }
         }
      }

      h(bufferbuilder, flag);
      WorldRenderUtils.f();
   }

   private void d(CameraSnapshot CameraSnapshot, long i) {
      WorldRenderUtils.d(this.glowTexture, this.getClient());
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      float f = this.sizeSetting.getFloatValue() * this.glowSizeSetting.getFloatValue();
      boolean flag = false;

      for (ParticleState ParticleState : this.pW) {
         float f1 = e(ParticleState, i);
         if (!(f1 > 1.0F)) {
            float f2 = g(f1);
            if (!(f2 <= 0.01F)) {
               int j = ParticleState.asV.size();

               for (int k = 0; k < j; k++) {
                  Vec3d vec3d = ParticleState.asV.get(k);
                  float f3 = (float)k / Math.max(1, j - 1);
                  float f4 = f * (0.15F + f3 * 0.85F);
                  float[] afloat = WorldRenderUtils.j(this.colorSetting.getColor(), f2 * f3 * this.glowAlphaSetting.getFloatValue());
                  WorldRenderUtils.b(this.zi, CameraSnapshot, vec3d.x, vec3d.y, vec3d.z);
                  WorldRenderUtils.g(bufferbuilder, this.zi, f4, afloat[0], afloat[1], afloat[2], afloat[3]);
                  this.zi.pop();
               }

               float[] afloat1 = WorldRenderUtils.j(this.colorSetting.getColor(), f2);
               WorldRenderUtils.b(this.zi, CameraSnapshot, ParticleState.Hg, ParticleState.lW, ParticleState.ath);
               WorldRenderUtils.g(bufferbuilder, this.zi, f, afloat1[0], afloat1[1], afloat1[2], afloat1[3]);
               this.zi.pop();
               flag = true;
            }
         }
      }

      h(bufferbuilder, flag);
      WorldRenderUtils.f();
   }

   private static float e(ParticleState ParticleState, long i) {
      return (float)(i - ParticleState.Yx) / (float)ParticleState.Vm;
   }

   private static float f(float f) {
      return WorldRenderUtils.m(f, 0.1F, 0.8F);
   }

   private static float g(float f) {
      return WorldRenderUtils.m(f, 0.25F, 0.6F);
   }

   private static void h(BufferBuilder bufferbuilder, boolean flag) {
      if (flag) {
         BuiltBuffer builtbuffer = bufferbuilder.endNullable();
         if (builtbuffer != null) {
            BufferRenderer.drawWithGlobalProgram(builtbuffer);
         }
      }
   }
}
