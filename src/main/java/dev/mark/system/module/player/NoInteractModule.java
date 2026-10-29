package dev.mark.system.module.player;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class NoInteractModule extends Module {

    public NoInteractModule() {
        super("NoInteract", "Blocks interaction with containers", Category.PLAYER);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (getScreen() == null && isContainer(world.getBlockState(hitResult.getBlockPos()).getBlock())) {
            if (getInteractionManager() != null) {
                getInteractionManager().interactItem(getPlayer(), hand);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private boolean isContainer(Block block) {
        return (block instanceof ChestBlock) || (block instanceof EnderChestBlock) ||
               (block instanceof BarrelBlock) || (block instanceof ShulkerBoxBlock) ||
               (block instanceof CraftingTableBlock) || (block instanceof AnvilBlock) ||
               (block instanceof AbstractFurnaceBlock) || (block instanceof BrewingStandBlock) ||
               (block instanceof HopperBlock) || (block instanceof DispenserBlock) ||
               (block instanceof BeaconBlock) || (block instanceof GrindstoneBlock) ||
               (block instanceof StonecutterBlock) || (block instanceof CartographyTableBlock) ||
               (block instanceof LoomBlock) || (block instanceof SmithingTableBlock);
    }
}
