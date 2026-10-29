package dev.mark.system.module.render;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.SliderSetting;
import java.awt.Color;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import com.mojang.blaze3d.systems.RenderSystem;

public class AmbientModule extends Module {
    private BooleanSetting customSky;
    private ColorSetting skyTopColor;
    private BooleanSetting stars;
    public SliderSetting worldTime;
    private ColorSetting worldColor;
    private ColorSetting skyHorizonColor;

    public AmbientModule() {
        super("Ambient", "Changes world appearance", Category.VISUAL);
        this.worldColor = new ColorSetting("World Color", "World color tint", Color.WHITE);
        this.worldTime = new SliderSetting("World Time", "Custom time of day (0-24000)", 0.0d, 0.0d, 24000.0d, 100.0d);
        this.customSky = new BooleanSetting("Custom Sky", "Shader sky", false);
        this.skyTopColor = new ColorSetting("Sky Top Color", "Zenith sky color", new Color(15, 15, 60));
        this.skyHorizonColor = new ColorSetting("Sky Horizon Color", "Horizon sky color", new Color(180, 100, 50));
        this.stars = new BooleanSetting("Stars", "Procedural stars", true);
        addSettings(this.worldColor, this.worldTime, this.customSky, this.skyTopColor, this.skyHorizonColor, this.stars);
    }

    public Color getSkyHorizonColor() { return this.skyHorizonColor.getColor(); }
    public Color getSkyTopColor() { return this.skyTopColor.getColor(); }
    public boolean isCustomSkyEnabled() { return isEnabled() && this.customSky.getValue(); }
    public boolean isStarsEnabled() { return this.stars.getValue(); }
    public float getWorldTime() { return getClient().world != null ? getClient().world.getTimeOfDay() % 24000 : (float)this.worldTime.getValue(); }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    @Override
    public void onRenderAfterEntities(WorldRenderContext context) { if (isEnabled()) resetWorldColor(); }

    @Override
    public void onRenderAfterTranslucent(WorldRenderContext context) { if (isEnabled()) applyWorldColor(); }

    private void applyWorldColor() {
        Color c = this.worldColor.getColor();
        float r = c.getRed()/255f, g = c.getGreen()/255f, b = c.getBlue()/255f;
        float max = Math.max(r, Math.max(g, b));
        float sat = max == 0f ? 0f : (max - Math.min(r, Math.min(g, b))) / max;
        RenderSystem.setShaderColor((1f-sat)+(sat*r), (1f-sat)+(sat*g), (1f-sat)+(sat*b), 1f);
    }
    private void resetWorldColor() { RenderSystem.setShaderColor(1f, 1f, 1f, 1f); }
}
