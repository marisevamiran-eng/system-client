package dev.mark.system.module.visual;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.SwordItem;

public class SwordBlockModule extends Module {

    public SwordBlockModule() {
        super("SwordBlock", "Visual 1.8 sword block pose", Category.VISUAL);
    }

    public boolean isHoldingSword() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null || client.options == null || !client.options.attackKey.isPressed()) {
            return false;
        }
        return client.player.getMainHandStack().getItem() instanceof SwordItem;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}
