package dev.mark.system.module.visual;

import dev.mark.system.module.Module;

import dev.mark.system.data.WaypointRegistry;

import dev.mark.system.util.ElapsedTimer;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mark.system.command.WaypointCommand;
import dev.mark.system.enums.Category;
import dev.mark.system.event.ChatMessageEvent;
import dev.mark.system.font.MSDFFont;
import dev.mark.system.hook.GameRendererInitHook;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltText;
import dev.mark.system.render.TextCache;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.util.ChatUtil;
import dev.mark.system.data.WaypointData;
import dev.mark.system.gui.animation.easing.EasingFunctions;

public class WaypointsModule extends Module {
   private final WaypointRegistry gi = WaypointRegistry.a();
   private final WaypointCommand mZ;
   private ListSetting arrowStyleSetting = new ListSetting(
      "Arrow Style", "Choose between 2D and 3D arrow", Arrays.<String>asList("2D", "3D"), List.<String>of("2D"), false
   );
   private ColorSetting arrowColorSetting = new ColorSetting("Arrow Color", "Arrow color", new Color(255, 255, 255, 255), true);
   private ColorSetting textColorSetting = new ColorSetting("Text Color", "Text color", Color.WHITE, false);
   private MSDFFont afn;
   private Map<String, ElapsedTimer> ajI = new HashMap<String, ElapsedTimer>();
   private long afu;
   private float adC = 25.0F;
   private float afZ = 25.0F;
   private static final float aca = 25.0F;
   private static final float awf = 45.0F;
   private double yM;
   private double atN;
   private float HI = 0.0F;

   public WaypointsModule() {
      super("Waypoints", "World waypoints by coordinates. .wp", Category.RENDER);
      this.mZ = new WaypointCommand(this);
      this.afn = MSDFFont.g().b("bb").c("bb").e();
      this.afu = System.nanoTime();
      this.addSettings(this.arrowStyleSetting, this.arrowColorSetting, this.textColorSetting);
   }

   @Override
   public void onEnable() {
      String s = ChatUtil.formatHighlight(".wp help");
      ChatUtil.sendSuccess("Waypoints enabled! Use " + s);
      this.ajI.clear();
      this.afu = System.nanoTime();
      this.adC = 25.0F;
      this.afZ = 25.0F;
      this.HI = 0.0F;
      if (this.getPlayer() != null) {
         this.yM = this.getPlayer().getX();
         this.atN = this.getPlayer().getZ();
      }
   }

   @Override
   public void onDisable() {
      this.ajI.clear();
      this.adC = 25.0F;
      this.HI = 0.0F;
   }

   @Override
   public void onChatMessage(ChatMessageEvent chatmessageevent) {
      this.mZ.handleMessage(chatmessageevent);
   }

   public String c() {
      if (this.getWorld() == null) {
         return "overworld";
      } else {
         String s = this.getWorld().getRegistryKey().getValue().getPath();
         switch (s) {
            case "overworld":
               return "overworld";
            case "the_nether":
               return "nether";
            case "the_end":
               return "end";
            default:
               return s;
         }
      }
   }

   public void d(DrawContext drawcontext) {
      if (this.isEnabled() && !this.isNotInWorld()) {
         try {
            this.e();
            this.f(drawcontext);
         } catch (Exception exception) {
         }
      }
   }

   private void e() {
      long i = System.nanoTime();
      float f = (float)(i - this.afu) / (float)NANOS_PER_SECOND;
      this.afu = i;
      f = Math.min(f, 0.1F);
      double d0 = this.getPlayer().getX();
      double d1 = this.getPlayer().getZ();
      if (this.yM == 0.0 && this.atN == 0.0) {
         this.yM = d0;
         this.atN = d1;
      }

      double d2 = d0 - this.yM;
      double d3 = d1 - this.atN;
      float f1 = (float)Math.sqrt(d2 * d2 + d3 * d3) / Math.max(f, 0.001F);
      this.yM = d0;
      this.atN = d1;
      this.HI = MathHelper.lerp(f * 5.0F, this.HI, f1);
      float f2 = MathHelper.clamp(this.HI / 8.0F, 0.0F, 1.0F);
      this.afZ = MathHelper.lerp(f2, 25.0F, 45.0F);
      this.adC = MathHelper.lerp(f * 6.0F, this.adC, this.afZ);
      String s = this.c();
      List list = this.gi.f(s);
      HashSet hashset = new HashSet();

      for (Object _wpObj : list) {
         if (((WaypointData)_wpObj).h()) {
            String s3 = ((WaypointData)_wpObj).e();
            double d9 = ((WaypointData)_wpObj).c();
            double d10 = ((WaypointData)_wpObj).a();
            String s2 = s3;
            String s1 = s2 + "_" + d10 + "_" + d9;
            hashset.add(s1);
            ElapsedTimer ElapsedTimer = this.ajI.computeIfAbsent(s1, s4 -> {
               return new ElapsedTimer();
            });
            float f3 = this.getPlayer().getYaw();
            double d4 = ((WaypointData)_wpObj).a() - d0;
            double d5 = ((WaypointData)_wpObj).c() - d1;
            double d6 = Math.sqrt(d4 * d4 + d5 * d5);
            if (!(d6 < 0.1)) {
               double d7 = Math.toDegrees(Math.atan2(d5, d4));
               double d8 = MathHelper.wrapDegrees(d7 - f3 + 180.0);
               float f4 = (float)d8;
               float f5 = ElapsedTimer.art;
               float f6 = f4 - f5;
               if (f6 > 180.0F) {
                  f6 -= 360.0F;
               }

               if (f6 < -180.0F) {
                  f6 += 360.0F;
               }

               float f7 = 12.0F;
               ElapsedTimer.art += f6 * f * f7;
               ElapsedTimer.art = MathHelper.wrapDegrees(ElapsedTimer.art);
            }
         }
      }

      this.ajI.keySet().removeIf(s4 -> {
         return !hashset.contains(s4);
      });
   }

   private void f(DrawContext drawcontext) {
      String s = this.c();
      List list = this.gi.f(s);
      if (!list.isEmpty()) {
         double d0 = this.getPlayer().getX();
         double d1 = this.getPlayer().getZ();
         int i = this.getClient().getWindow().getScaledWidth() / 2;
         int j = this.getClient().getWindow().getScaledHeight() / 2;
         float f = this.adC;
         float f1 = 6.4F;
         float f2 = 10.0F;
         float f3 = 0.0F;
         float f4 = 2.0F;

         for (Object _wpObj : list) {
            if (((WaypointData)_wpObj).h()) {
               String s5 = ((WaypointData)_wpObj).e();
               double d3 = ((WaypointData)_wpObj).c();
               double d4 = ((WaypointData)_wpObj).a();
               String s4 = s5;
               String s1 = s4 + "_" + d4 + "_" + d3;
               ElapsedTimer ElapsedTimer = this.ajI.get(s1);
               if (ElapsedTimer != null) {
                  double d2 = Math.sqrt(Math.pow(((WaypointData)_wpObj).a() - d0, 2.0) + Math.pow(((WaypointData)_wpObj).c() - d1, 2.0));
                  if (!(d2 < 0.1)) {
                     float f5 = ElapsedTimer.art;
                     float f6 = EasingFunctions.a(Math.min(1.0F, ElapsedTimer.a() * 3.0F));
                     float f7 = (float)(i + Math.cos(Math.toRadians(f5)) * f);
                     float f8 = (float)(j + Math.sin(Math.toRadians(f5)) * f);
                     drawcontext.getMatrices().push();
                     drawcontext.getMatrices().translate(f7, f8, 0.0F);
                     drawcontext.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f5 + 90.0F));
                     float f9 = EasingFunctions.c(f6);
                     drawcontext.getMatrices().scale(f9, f9, 1.0F);
                     boolean flag = "3D".equals(this.arrowStyleSetting.getFirst());
                     if (flag) {
                        this.i(drawcontext.getMatrices(), -f2 / 2.0F, -f2 / 2.0F, f2, f2, this.arrowColorSetting.getColor());
                     } else {
                        this.h(drawcontext.getMatrices(), -f2 / 2.0F, -f2 / 2.0F, f2, f2, this.arrowColorSetting.getColor());
                     }

                     drawcontext.getMatrices().pop();
                     String s2 = ((WaypointData)_wpObj).e();
                     String s3 = String.format("%.0fm", d2);
                     Matrix4f matrix4f = drawcontext.getMatrices().peek().getPositionMatrix();
                     Color color = new Color(
                        this.textColorSetting.getColor().getRed(),
                        this.textColorSetting.getColor().getGreen(),
                        this.textColorSetting.getColor().getBlue(),
                        (int)(255.0F * f6)
                     );
                     BuiltText builttext = TextCache.a(this.afn, s2, f1, color);
                     float f10 = this.afn.c(s2, f1);
                     float f11 = f8 + f2 / 2.0F + f3;
                     builttext.render(matrix4f, f7 - f10 / 2.0F, f11);
                     BuiltText builttext1 = TextCache.a(this.afn, s3, f1 - 2.0F, color);
                     float f12 = this.afn.c(s3, f1 - 2.0F);
                     float f13 = f11 + f1 + f4;
                     builttext1.render(matrix4f, f7 - f12 / 2.0F, f13);
                  }
               }
            }
         }
      }
   }

   private Identifier g() {
      String s = this.arrowStyleSetting.getFirst();
      return "3D".equals(s) ? GameRendererInitHook.arrow_3d : GameRendererInitHook.arrow;
   }

   private void h(MatrixStack matrixstack, float f, float f1, float f2, float f3, Color color) {
      matrixstack.push();
      Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      RenderSystem.setShaderTexture(0, this.g());
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      int i = color.getRGB();
      int j = i >> 16 & 0xFF;
      int k = i >> 8 & 0xFF;
      int l = i & 0xFF;
      int i1 = i >> 24 & 0xFF;
      bufferbuilder.vertex(matrix4f, f, f1, 0.0F).texture(0.0F, 0.0F).color(j, k, l, i1);
      bufferbuilder.vertex(matrix4f, f, f1 + f3, 0.0F).texture(0.0F, 1.0F).color(j, k, l, i1);
      bufferbuilder.vertex(matrix4f, f + f2, f1 + f3, 0.0F).texture(1.0F, 1.0F).color(j, k, l, i1);
      bufferbuilder.vertex(matrix4f, f + f2, f1, 0.0F).texture(1.0F, 0.0F).color(j, k, l, i1);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      RenderSystem.disableBlend();
      matrixstack.pop();
   }

   private void i(MatrixStack matrixstack, float f, float f1, float f2, float f3, Color color) {
      matrixstack.push();
      Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      RenderSystem.setShaderTexture(0, this.g());
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      int i = color.getRGB();
      int j = i >> 16 & 0xFF;
      int k = i >> 8 & 0xFF;
      int l = i & 0xFF;
      int i1 = i >> 24 & 0xFF;
      float f4 = 0.0F;
      float f5 = 0.0F;
      int j1 = (int)(j * 0.4F);
      int k1 = (int)(k * 0.4F);
      int l1 = (int)(l * 0.4F);
      bufferbuilder.vertex(matrix4f, f + f5, f1 + f5, -1.0F).texture(0.0F, 0.0F).color(j1, k1, l1, i1);
      bufferbuilder.vertex(matrix4f, f + f5, f1 + f3 + f5, -1.0F).texture(0.0F, 1.0F).color(j1, k1, l1, i1);
      bufferbuilder.vertex(matrix4f, f + f2 + f5, f1 + f3 + f5, -1.0F).texture(1.0F, 1.0F).color(j1, k1, l1, i1);
      bufferbuilder.vertex(matrix4f, f + f2 + f5, f1 + f5, -1.0F).texture(1.0F, 0.0F).color(j1, k1, l1, i1);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      int i2 = (int)(j * 0.7F);
      int j2 = (int)(k * 0.7F);
      int k2 = (int)(l * 0.7F);
      bufferbuilder.vertex(matrix4f, f, f1, -0.5F).texture(0.0F, 0.0F).color(i2, j2, k2, i1);
      bufferbuilder.vertex(matrix4f, f, f1 + f3, -0.5F).texture(0.0F, 1.0F).color(i2, j2, k2, i1);
      bufferbuilder.vertex(matrix4f, f + f2, f1 + f3, -0.5F).texture(1.0F, 1.0F).color(i2, j2, k2, i1);
      bufferbuilder.vertex(matrix4f, f + f2, f1, -0.5F).texture(1.0F, 0.0F).color(i2, j2, k2, i1);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      bufferbuilder.vertex(matrix4f, f - f4, f1 - f4, 0.0F).texture(0.0F, 0.0F).color(j, k, l, i1);
      bufferbuilder.vertex(matrix4f, f - f4, f1 + f3 - f4, 0.0F).texture(0.0F, 1.0F).color(j, k, l, i1);
      bufferbuilder.vertex(matrix4f, f + f2 - f4, f1 + f3 - f4, 0.0F).texture(1.0F, 1.0F).color(j, k, l, i1);
      bufferbuilder.vertex(matrix4f, f + f2 - f4, f1 - f4, 0.0F).texture(1.0F, 0.0F).color(j, k, l, i1);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      RenderSystem.disableBlend();
      matrixstack.pop();
   }

   public WaypointRegistry j() {
      return this.gi;
   }
}

