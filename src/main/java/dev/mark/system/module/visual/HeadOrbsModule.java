package dev.mark.system.module.visual;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.SliderSetting;
import java.awt.Color;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

public class HeadOrbsModule extends Module {
    private SliderSetting size, radius, height, speed, chaos;
    private ColorSetting color3, color1, color2;
    private final MatrixStack matrixStack;

    public HeadOrbsModule() {
        super("HeadOrbs", "Three orbs around the head", Category.VISUAL);
        this.color1 = new ColorSetting("Color 1", "First orb color", new Color(255, 200, 100, 200));
        this.color2 = new ColorSetting("Color 2", "Second orb color", new Color(255, 150, 255, 200));
        this.color3 = new ColorSetting("Color 3", "Third orb color", new Color(150, 150, 255, 200));
        this.size = new SliderSetting("Size", "Orb size", 0.1d, 0.01d, 0.5d, 0.01d);
        this.radius = new SliderSetting("Radius", "Orbit radius", 0.5d, 0.1d, 1.0d, 0.05d);
        this.height = new SliderSetting("Height", "Height above head", 0.5d, -0.5d, 3.0d, 0.05d);
        this.speed = new SliderSetting("Rotation Speed", "Orb rotation speed", 0.5d, 0.1d, 2.0d, 0.05d);
        this.chaos = new SliderSetting("Chaos", "Movement chaos", 0.2d, 0.0d, 0.5d, 0.01d);
        this.matrixStack = new MatrixStack();
        addSettings(this.color1, this.color2, this.color3, this.size, this.radius, this.height, this.speed, this.chaos);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    @Override
    public void onRenderAfterEntities(WorldRenderContext ctx) {
        if (getScreen() != null || getClient().options.getPerspective().isFirstPerson()) return;
        renderOrbs(getPlayer(), ctx.tickCounter().getTickDelta(true));
    }

    private void renderOrbs(PlayerEntity player, float td) {
        double x = MathHelper.lerp(td, player.prevX, player.getX()), y = MathHelper.lerp(td, player.prevY, player.getY()), z = MathHelper.lerp(td, player.prevZ, player.getZ());
        Camera cam = getClient().gameRenderer.getCamera(); Vec3d cp = cam.getPos();
        float eh = (player.getEyeHeight(player.getPose()) * 0.5f) + (float)this.height.getValue();
        float r = (float)this.radius.getValue(), sz = (float)this.size.getValue();
        Color[] colors = {this.color1.getColor(), this.color2.getColor(), this.color3.getColor()};
        long ct = System.currentTimeMillis();
        double ao = ((ct * 0.001d) * this.speed.getValue()) % 360.0d, co = ct / 1000.0d;
        net.minecraft.client.texture.AbstractTexture tex = getClient().getTextureManager().getTexture(Identifier.of("minecraft", "textures/misc/white.png"));
        RenderSystem.enableBlend(); RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
        RenderSystem.enableDepthTest(); RenderSystem.depthMask(false);
        RenderSystem.setShaderTexture(0, tex.getGlId());
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        BufferBuilder buf = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        for (int i = 0; i < 3; i++) {
            double rad = Math.toRadians(ao + i*120.0d);
            double cy = (((Math.sin(co*0.7d + i*0.8d) + Math.sin(co*0.5d + i*1.2d) + Math.sin(co*0.9d + i*0.4d))/3.0d)*this.chaos.getValue());
            double ox = x + Math.cos(rad)*r, oy = y + eh + cy, oz = z + Math.sin(rad)*r;
            double dx = ox - cp.x, dy = oy - cp.y, dz = oz - cp.z;
            Color c = colors[i]; int ci = (c.getAlpha()<<24)|(c.getRed()<<16)|(c.getGreen()<<8)|c.getBlue();
            this.matrixStack.push(); this.matrixStack.translate(dx, dy, dz);
            this.matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-cam.getYaw()));
            this.matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(cam.getPitch()));
            Matrix4f mat = this.matrixStack.peek().getPositionMatrix();
            buf.vertex(mat, -sz, sz, 0f).texture(0f, 1f).color(ci);
            buf.vertex(mat, sz, sz, 0f).texture(1f, 1f).color(ci);
            buf.vertex(mat, sz, -sz, 0f).texture(1f, 0f).color(ci);
            buf.vertex(mat, -sz, -sz, 0f).texture(0f, 0f).color(ci);
            this.matrixStack.pop();
        }
        BufferRenderer.drawWithGlobalProgram(buf.end());
        RenderSystem.depthMask(true); RenderSystem.disableBlend();
    }
}
