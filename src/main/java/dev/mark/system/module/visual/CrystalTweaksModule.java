package dev.mark.system.module.visual;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.setting.ListSetting;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class CrystalTweaksModule extends Module {
    private ListSetting chamsMode;
    private BooleanSetting throughWalls;
    private final Set<Integer> placedCrystals;
    private ColorSetting chamsColor;
    private SliderSetting offsetY;
    private int lastCrystalCount;
    private BooleanSetting onlyOwnCrystals;
    private long lastPlaceTime;
    private SliderSetting offsetX;
    private BooleanSetting changeSize;
    private SliderSetting lineThickness;
    private final Set<Integer> validCrystals;
    private BooleanSetting chamsEnabled;
    private SliderSetting crystalSize;

    public CrystalTweaksModule() {
        super("CrystalTweaks", "Crystal utility", Category.VISUAL);
        this.changeSize = new BooleanSetting("Change Size", "Change crystal size and position", true);
        this.chamsEnabled = new BooleanSetting("Chams", "Render crystal with custom chams", false);
        this.crystalSize = new SliderSetting("Size", "Crystal size", 3.0d, 1.0d, 5.0d, 0.05d);
        this.offsetX = new SliderSetting("Offset X", "Crystal X offset", 0.0d, -1.0d, 1.0d, 0.01d);
        this.offsetY = new SliderSetting("Offset Y", "Crystal Y offset", 0.0d, -1.0d, 1.0d, 0.01d);
        this.onlyOwnCrystals = new BooleanSetting("Only Own", "Only apply to crystals placed by you", false);
        this.chamsMode = new ListSetting("Chams Mode", "Chams display mode", Arrays.asList("Color", "Outline", "Color+Outline"), Collections.singletonList("Color"), false);
        this.throughWalls = new BooleanSetting("Through Walls", "Render through walls", true);
        this.chamsColor = new ColorSetting("Chams Color", "Crystal chams color", new Color(138, 43, 226, 60), true);
        this.lineThickness = new SliderSetting("Line Thickness", "Outline line thickness", 3.0d, 1.0d, 5.0d, 0.5d, "", 1);
        this.placedCrystals = ConcurrentHashMap.newKeySet(); this.validCrystals = ConcurrentHashMap.newKeySet();
        this.lastCrystalCount = -1; this.lastPlaceTime = -1L;
        addSettings(this.changeSize, this.chamsEnabled, this.crystalSize, this.offsetX, this.offsetY, this.onlyOwnCrystals, this.chamsMode, this.throughWalls, this.chamsColor, this.lineThickness);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() { this.placedCrystals.clear(); this.validCrystals.clear(); this.lastCrystalCount = -1; this.lastPlaceTime = -1L; }

    @Override
    public void onEndTick() {
        if (!isEnabled() || !this.onlyOwnCrystals.getValue() || getPlayer() == null || getWorld() == null) return;
        int cnt = 0;
        for (Hand h : Hand.values()) if (getPlayer().getStackInHand(h).isOf(Items.END_CRYSTAL)) cnt += getPlayer().getStackInHand(h).getCount();
        if (this.lastCrystalCount > 0 && cnt < this.lastCrystalCount) this.lastPlaceTime = getWorld().getTime();
        this.lastCrystalCount = cnt;
        this.placedCrystals.removeIf(id -> getWorld().getEntityById(id) == null);
        this.validCrystals.removeIf(id -> getWorld().getEntityById(id) == null);
    }

    public boolean isOwnCrystal(Entity e) {
        if (!this.onlyOwnCrystals.getValue()) return true;
        int id = e.getId();
        if (this.validCrystals.add(id) && getWorld() != null && getWorld().getTime() - this.lastPlaceTime <= 1) this.placedCrystals.add(id);
        return this.placedCrystals.contains(id);
    }

    public boolean isColorMode() { String m = this.chamsMode.getFirst(); return "Color".equals(m) || "Color+Outline".equals(m); }
    public boolean isOutlineMode() { String m = this.chamsMode.getFirst(); return "Outline".equals(m) || "Color+Outline".equals(m); }
    public ListSetting getChamsMode() { return this.chamsMode; }
    public SliderSetting getLineThickness() { return this.lineThickness; }
    public SliderSetting getOffsetY() { return this.offsetY; }
    public SliderSetting getOffsetX() { return this.offsetX; }
    public ColorSetting getChamsColor() { return this.chamsColor; }
    public BooleanSetting getChamsEnabled() { return this.chamsEnabled; }
    public BooleanSetting getOnlyOwnCrystals() { return this.onlyOwnCrystals; }
    public BooleanSetting getThroughWalls() { return this.throughWalls; }
    public SliderSetting getCrystalSize() { return this.crystalSize; }
    public BooleanSetting getChangeSize() { return this.changeSize; }
}
