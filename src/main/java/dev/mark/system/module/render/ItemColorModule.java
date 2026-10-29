package dev.mark.system.module.render;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;

public class ItemColorModule extends Module {
    private final Map<SpecialItem, ItemColorData> itemColorMap;

    public ItemColorModule() {
        super("ItemColor", "Highlights selected items in inventory slots.", Category.RENDER);
        this.itemColorMap = new EnumMap<>(SpecialItem.class);
        for (SpecialItem item : SpecialItem.values()) {
            BooleanSetting enabled = new BooleanSetting(item.getDisplayName(), "Enable highlight for " + item.getDisplayName() + ".", true);
            ColorSetting color = new ColorSetting(item.getDisplayName() + " Color", "Highlight color for " + item.getDisplayName() + ".", getDefaultColor(item.ordinal(), SpecialItem.values().length), true);
            this.itemColorMap.put(item, new ItemColorData(enabled, color));
            addSettings(enabled, color);
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public ItemColorData getItemData(ItemStack stack) {
        for (Map.Entry<SpecialItem, ItemColorData> entry : this.itemColorMap.entrySet()) {
            SpecialItem item = entry.getKey();
            if (item == SpecialItem.SHULKER) {
                if (stack.isIn(ItemTags.SHULKER_BOXES)) {
                    return entry.getValue();
                }
            } else if (item.getItem() != null && stack.isOf(item.getItem())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static Color getAnimatedColor(Color color) {
        double anim = 0.5f - 0.5f * (float) Math.cos(
            ((System.currentTimeMillis() % 900L) / 900.0f) * 6.2831855f);
        double alpha = 0.35f + 0.65f * anim;
        int result = (int) (color.getAlpha() * alpha);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(),
            Math.max(0, Math.min(255, result)));
    }

    private static Color getDefaultColor(int index, int total) {
        Color hsb = Color.getHSBColor(total <= 1 ? 0.0f : (float) index / total, 0.85f, 1.0f);
        return new Color(hsb.getRed(), hsb.getGreen(), hsb.getBlue(), 120);
    }

    public enum SpecialItem {
        TOTEM("Totem", Items.TOTEM_OF_UNDYING),
        CRYSTAL("Crystal", Items.END_CRYSTAL),
        OBSIDIAN("Obsidian", Items.OBSIDIAN),
        ANCHOR("Anchor", Items.RESPAWN_ANCHOR),
        GLOWSTONE("Glowstone", Items.GLOWSTONE),
        SHULKER("Shulker", null),
        GAPPLE("Gapple", Items.GOLDEN_APPLE),
        PEARL("Pearl", Items.ENDER_PEARL),
        XP("XP", Items.EXPERIENCE_BOTTLE),
        SWORD("Sword", Items.NETHERITE_SWORD),
        TNT("TNT", Items.TNT),
        WEB("Cobweb", Items.COBWEB);

        private final String displayName;
        private final Item item;

        SpecialItem(String displayName, Item item) {
            this.displayName = displayName;
            this.item = item;
        }

        public String getDisplayName() { return displayName; }
        public Item getItem() { return item; }
    }

    public static class ItemColorData {
        public final BooleanSetting enabled;
        public final ColorSetting color;

        public ItemColorData(BooleanSetting enabled, ColorSetting color) {
            this.enabled = enabled;
            this.color = color;
        }
    }
}
