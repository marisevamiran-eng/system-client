package dev.mark.system.module.render;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;

public class ShulkerPreviewModule extends Module {
    private static final int MAX_SLOTS = 27;

    public ShulkerPreviewModule() { super("ShulkerPreview", "Shows shulker contents on hover", Category.RENDER); }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    public List<ItemStack> getShulkerContent(ItemStack stack) {
        if (!isShulker(stack)) return null;
        ContainerComponent container = stack.get(DataComponentTypes.CONTAINER);
        if (container != null) return container.stream().toList();
        NbtComponent nbtComp = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
        if (nbtComp != null) return getItemsFromNbt(nbtComp.copyNbt());
        NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (customData != null) return getItemsFromNbt(customData.copyNbt());
        return null;
    }

    private List<ItemStack> getItemsFromNbt(NbtCompound nbt) {
        NbtList itemsList = null;
        if (nbt.contains("Items")) itemsList = nbt.getList("Items", 10);
        else if (nbt.contains("BlockEntityTag")) { NbtCompound bt = nbt.getCompound("BlockEntityTag"); if (bt.contains("Items")) itemsList = bt.getList("Items", 10); }
        if (itemsList == null || itemsList.isEmpty()) return null;
        DefaultedList<ItemStack> inv = DefaultedList.ofSize(MAX_SLOTS, ItemStack.EMPTY);
        for (int i = 0; i < itemsList.size(); i++) {
            try { NbtCompound it = itemsList.getCompound(i); int slot = it.getByte("Slot") & 255; if (slot < MAX_SLOTS) inv.set(slot, ItemStack.fromNbt(getWorld().getRegistryManager(), it).orElse(ItemStack.EMPTY)); }
            catch (Exception ignored) {}
        }
        return inv;
    }

    private boolean isShulker(ItemStack stack) { return stack != null && Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock; }
}
