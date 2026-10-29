package dev.mark.system.module.visual;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;

public class BackToolsModule extends Module {
    public SliderSetting size;
    public ListSetting position;
    public static final ConcurrentHashMap<String, WeaponData> weaponCache = new ConcurrentHashMap<>();
    public SliderSetting rotation;
    private boolean hasUpdated;

    public BackToolsModule() {
        super("BackTools", "Renders weapons on back", Category.VISUAL);
        this.position = new ListSetting("Position", "", Arrays.asList("Back", "Belt"), Collections.singletonList("Back"), false);
        this.rotation = new SliderSetting("Rotation", "", 0.0d, -180.0d, 180.0d, 1.0d, "°", 0);
        this.size = new SliderSetting("Size", "", 1.0d, 0.1d, 2.0d, 0.05d);
        this.hasUpdated = false;
        addSettings(this.position, this.rotation, this.size);
    }

    public static boolean isWeapon(Item item) { return item instanceof SwordItem || item instanceof AxeItem || item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem || item instanceof MaceItem; }

    @Override public void onEnable() { weaponCache.clear(); this.hasUpdated = false; }
    @Override public void onDisable() { weaponCache.clear(); this.hasUpdated = false; }

    @Override
    public void onEndTick() {
        if (this.hasUpdated || getScreen() != null) return;
        this.hasUpdated = true;
        ClientPlayerEntity player = getClientPlayer();
        if (player == null) return;
        int cur = player.getInventory().selectedSlot;
        for (int i = 0; i < player.getInventory().size(); i++) {
            if (i != cur) {
                ItemStack stack = player.getInventory().getStack(i);
                if (isWeapon(stack.getItem())) { weaponCache.computeIfAbsent(player.getName().getString(), k -> new WeaponData()).itemId = stack.getItem().toString(); return; }
            }
        }
    }

    public static class WeaponData { public String itemId; }
}
