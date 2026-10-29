package dev.mark.system.module.visual;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import java.awt.Color;
import java.lang.reflect.Field;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

public class HitColorModule extends Module {
    private ColorSetting hitColor;
    private static final int DEFAULT_COLOR = -1291911168;
    private static volatile boolean isActive;
    private static Field overlayTextureField;
    private BooleanSetting armor;
    private int currentColor;
    public static volatile boolean needsRefresh;

    static { needsRefresh = false; isActive = false; }

    public HitColorModule() {
        super("HitColor", "Changes hit overlay color", Category.VISUAL);
        this.hitColor = new ColorSetting("Color", "Hit overlay color", new Color(251, 127, 127, 148), true);
        this.armor = new BooleanSetting("Armor", "Change armor hit color", true);
        this.currentColor = 0;
        addSettings(this.hitColor, this.armor);
    }

    @Override public void onEnable() { this.currentColor = 0; }
    @Override public void onDisable() { resetToDefault(); needsRefresh = false; isActive = false; }

    @Override
    public void onEndTick() {
        isActive = this.armor.getValue();
        int c = packColor(this.hitColor.getRed(), this.hitColor.getGreen(), this.hitColor.getBlue(), this.hitColor.getAlpha());
        if (c != this.currentColor) { setOverlayColor(c); this.currentColor = c; }
    }

    public static boolean isActive() { return isActive; }
    private void setOverlayColor(int c) { try { NativeImage img = getOverlayImage(); if (img != null) img.fillRect(0, 0, 0, 0, c); } catch (Exception ignored) {} }
    private void resetToDefault() { try { NativeImage img = getOverlayImage(); if (img != null) img.fillRect(0, 0, 0, 0, DEFAULT_COLOR); } catch (Exception ignored) {} }

    private static NativeImage getOverlayImage() throws Exception {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.gameRenderer == null) return null;
        OverlayTexture ot = mc.gameRenderer.getOverlayTexture();
        if (ot == null) return null;
        NativeImageBackedTexture tex = getOverlayTexture(ot);
        return tex != null ? tex.getImage() : null;
    }

    private static NativeImageBackedTexture getOverlayTexture(OverlayTexture ot) throws Exception {
        if (overlayTextureField == null) {
            for (Field f : OverlayTexture.class.getDeclaredFields())
                if (NativeImageBackedTexture.class.isAssignableFrom(f.getType())) { f.setAccessible(true); overlayTextureField = f; break; }
        }
        return overlayTextureField != null ? (NativeImageBackedTexture)overlayTextureField.get(ot) : null;
    }
    private static int packColor(int r, int g, int b, int a) { return ((255-a)<<24)|(r<<16)|(g<<8)|b; }
}
