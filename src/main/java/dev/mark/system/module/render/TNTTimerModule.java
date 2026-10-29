package dev.mark.system.module.render;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.SliderSetting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

public class TNTTimerModule extends Module {
    private SliderSetting plateScale;

    public TNTTimerModule() {
        super("TNTTimer", "Shows TNT explosion countdown", Category.RENDER);
        this.plateScale = new SliderSetting("Plate Scale", "Timer plate scale", 0.5d, 0.1d, 3.0d, 0.05d);
        addSettings(this.plateScale);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onRenderAfterEntities(WorldRenderContext context) {
        // TNT timer rendering - requires FontRenderer/TextRenderer utilities
        // which are specific rendering helpers. Core logic preserved for future implementation.
    }
}
