package dev.mark.system.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModuleBase {
   protected static final MinecraftClient mc = MinecraftClient.getInstance();
   public static final long NANOS_PER_SECOND = 1000000000L;

   // Glow texture identifiers
   protected static final Identifier glowTexture = Identifier.of("minecraft", "textures/glow.png");
   protected static final Identifier glowStarTexture = Identifier.of("minecraft", "textures/glow_star.png");
   protected static final Identifier glowEllipseTexture = Identifier.of("minecraft", "textures/glow_ellipse.png");
   protected static final Identifier glowPolygonTexture = Identifier.of("minecraft", "textures/glow_polygon.png");
   protected static final Identifier glowSquareTexture = Identifier.of("minecraft", "textures/glow_square.png");

   public ClientPlayerEntity getClientPlayer() {
      return mc.player;
   }

   public MinecraftClient getClient() {
      return mc;
   }

   public ClientWorld getClientWorld() {
      return mc.world;
   }

   public World getWorld() {
      return mc.world;
   }

   public PlayerEntity getPlayer() {
      return mc.player;
   }

   public boolean hasPlayerAndWorld() {
      return mc.player != null && mc.world != null;
   }

   public boolean isNotInWorld() {
      return mc.player == null || mc.world == null;
   }

   public void sendChatMessage(String message) {
      if (mc.player != null) {
         if (message.startsWith("/")) {
            mc.player.networkHandler.sendCommand(message.substring(1));
         } else {
            mc.player.networkHandler.sendChatMessage(message);
         }
      }
   }

   public Screen getScreen() {
      return mc.currentScreen;
   }

   public ClientPlayNetworkHandler getNetworkHandler() {
      return mc.player != null ? mc.player.networkHandler : null;
   }

   public void clickSlot(int slot) {
      if (mc.player != null && mc.interactionManager != null) {
         mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot, 0, net.minecraft.screen.slot.SlotActionType.PICKUP, mc.player);
      }
   }

   public void clickSlot(int syncId, int slot, int button, net.minecraft.screen.slot.SlotActionType actionType, PlayerEntity player) {
      if (mc.interactionManager != null) {
         mc.interactionManager.clickSlot(syncId, slot, button, actionType, player);
      }
   }

   public ClientPlayerInteractionManager getInteractionManager() {
      return mc.interactionManager;
   }

   public GameOptions getOptions() {
      return mc.options;
   }

   public PlayerInventory getInventory() {
      return mc.player != null ? mc.player.getInventory() : null;
   }

   public Perspective getPerspective() {
      return mc.options.getPerspective();
   }

   public boolean isFriend(String name) {
      return dev.mark.system.core.FriendManager.getInstance().isFriend(name);
   }

   public boolean isFriendPlayer(PlayerEntity player) {
      return dev.mark.system.core.FriendManager.getInstance().isFriendPlayer(player);
   }

   public void U(String setting1Name, String setting2Name, boolean link) {
   }

   public void interactBlock(ClientPlayerEntity player, Hand hand, net.minecraft.util.hit.BlockHitResult hitResult) {
      if (mc.interactionManager != null) {
         mc.interactionManager.interactBlock(player, hand, hitResult);
      }
   }

   public void attackEntity(PlayerEntity player, LivingEntity target) {
      if (mc.interactionManager != null) {
         mc.interactionManager.attackEntity(player, target);
      }
   }

   public void attackEntity(PlayerEntity player, net.minecraft.entity.Entity target) {
      if (mc.interactionManager != null) {
         mc.interactionManager.attackEntity(player, target);
      }
   }

   public Hand getMainHand() {
      return Hand.MAIN_HAND;
   }

   public void interactItem(Hand hand) {
      if (mc.interactionManager != null && mc.player != null) {
         mc.interactionManager.interactItem(mc.player, hand);
      }
   }

   public void close() {
      mc.setScreen(null);
   }

   public void equipArmor(int slot) {
      if (mc.player != null) {
         PlayerInventory inventory = mc.player.getInventory();
         ItemStack stack = inventory.getStack(slot);
         int armorSlot = slot - 36;
         if (armorSlot >= 0 && armorSlot < 4) {
            inventory.armor.set(armorSlot, stack);
         }
      }
   }

   public void swapSlots(int fromSlot, int toSlot) {
      if (mc.player != null && mc.interactionManager != null) {
         PlayerInventory inventory = mc.player.getInventory();
         ItemStack fromStack = inventory.getStack(fromSlot);
         ItemStack toStack = inventory.getStack(toSlot);
         inventory.setStack(fromSlot, toStack);
         inventory.setStack(toSlot, fromStack);
      }
   }

   public void setStack(int slot, ItemStack stack) {
      if (mc.player != null) {
         mc.player.getInventory().setStack(slot, stack);
      }
   }

   public ItemStack getStack(int slot) {
      return mc.player != null ? mc.player.getInventory().getStack(slot) : ItemStack.EMPTY;
   }

   public boolean hasPlayer() {
      return mc.player != null;
   }

   public int size() {
      return mc.player != null ? mc.player.getInventory().size() : 0;
   }

   public boolean isFriendLiving(LivingEntity entity) {
      return dev.mark.system.core.FriendManager.getInstance().isFriendLiving(entity);
   }

   public net.minecraft.server.world.ServerWorld getServerWorld() {
      if (mc.player == null) return null;
      if (mc.getServer() != null) {
         return mc.getServer().getWorld(mc.world.getRegistryKey());
      }
      return null;
   }

   public void sendPacket(net.minecraft.network.packet.Packet<?> packet) {
      if (mc.player != null && mc.player.networkHandler != null) {
         mc.player.networkHandler.sendPacket(packet);
      }
   }

   // Key bindings
   public KeyBinding getSprintKey() {
      return mc.options.sprintKey;
   }

   public KeyBinding getUseKey() {
      return mc.options.useKey;
   }

   public KeyBinding getJumpKey() {
      return mc.options.jumpKey;
   }

   public KeyBinding getAttackKey() {
      return mc.options.attackKey;
   }

   public KeyBinding getForwardKey() {
      return mc.options.forwardKey;
   }

   public KeyBinding getBackKey() {
      return mc.options.backKey;
   }

   public KeyBinding getLeftKey() {
      return mc.options.leftKey;
   }

   public KeyBinding getRightKey() {
      return mc.options.rightKey;
   }

   public KeyBinding getSneakKey() {
      return mc.options.sneakKey;
   }

   public int getSelectedSlot() {
      return mc.player != null ? mc.player.getInventory().selectedSlot : 0;
   }
}
