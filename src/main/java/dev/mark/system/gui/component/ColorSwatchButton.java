package dev.mark.system.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;
import dev.mark.system.render.ShaderUtil;

import dev.mark.system.render.Size;
public class ColorSwatchButton extends IconButton {
   private static final ShaderProgramKey avz = ShaderUtil.a("rectangle", VertexFormats.POSITION_COLOR, Defines.EMPTY);
   private static final float Wv = 0.3F;
   private static final float apO = 2.3F;
   private Color abk;

   public ColorSwatchButton(float f, float f1, Color color) {
      super(f, f1, 20.0F, "L", 15.0F, 0.0F, 4.0F, null);
      this.abk = color;
   }

   public void a(Color color) {
      this.abk = color;
   }

   public Color b() {
      return this.abk;
   }

   @Override
   protected void a(Matrix4f matrix4f, float f, float f1, int i, int j, float f3, float f2) {
      this.eV.a(this.cH);
      this.eV.f();
      Matrix4f matrix4f1 = this.b(matrix4f, f, f1, this.eV.d());
      if (this.abk != null) {
         this.c(matrix4f1, f, f1, f2);
      }
   }

   private void c(Matrix4f matrix4f, float f, float f1, float f2) {
      float f3 = 20.0F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      ShaderProgram shaderprogram = RenderSystem.setShader(avz);

       dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "Size", f3, f3);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "Offset", 2.3F, 2.3F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "CornerRadius", 4.0F, 4.0F, 4.0F, 4.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "BlurRadius", 0.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "ShadowOffset", 0.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "ShadowStrength", 0.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "BorderThickness", 0.5F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "BorderSoftness", 1.5F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "EdgeSoftness", 0.3F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "DitherStrength", 0.003F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "Margin", 0.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "NoiseStrength", 0.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "GlobalAlpha", f2);
      
      float f4 = this.abk.getRed() / 255.0F;
      float f5 = this.abk.getGreen() / 255.0F;
      float f6 = this.abk.getBlue() / 255.0F;
      float f7 = this.abk.getAlpha() / 255.0F;
      
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "GradientColor1", f4, f5, f6, f7);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "GradientColor2", f4, f5, f6, f7);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "GradientColor3", f4, f5, f6, f7);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "GradientColor4", f4, f5, f6, f7);

       float borderR = Math.min(1.0F, f4 + 0.15F);
      float borderG = Math.min(1.0F, f5 + 0.15F);
      float borderB = Math.min(1.0F, f6 + 0.15F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "BorderColor1", borderR, borderG, borderB, 1.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "BorderColor2", borderR, borderG, borderB, 1.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "BorderColor3", borderR, borderG, borderB, 1.0F);
      dev.mark.system.render.BuiltRectangle.safeSetUniform(shaderprogram, "BorderColor4", borderR, borderG, borderB, 1.0F);
      
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      float f8 = f - 2.3F;
      float f9 = f1 - 2.3F;
      float f10 = f3 + 4.6F;
      float f11 = f3 + 4.6F;
      int i = (int)(f2 * 255.0F) << 24 | 16777215;
      bufferbuilder.vertex(matrix4f, f8, f9, 0.0F).color(i);
      bufferbuilder.vertex(matrix4f, f8, f9 + f11, 0.0F).color(i);
      bufferbuilder.vertex(matrix4f, f8 + f10, f9 + f11, 0.0F).color(i);
      bufferbuilder.vertex(matrix4f, f8 + f10, f9, 0.0F).color(i);
      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }
}
