package dev.mark.system.module;

import dev.mark.system.enums.Category;
import dev.mark.system.event.ChatMessageEvent;
import dev.mark.system.event.MouseMoveEvent;
import dev.mark.system.event.MovementEvent;
import dev.mark.system.event.PacketEvent;
import dev.mark.system.event.RenderHudEvent;
import dev.mark.system.event.RotationEvent;
import dev.mark.system.event.SpeedEvent;
import dev.mark.system.event.UseItemEvent;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.KeyBindSetting;
import dev.mark.system.setting.Setting;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public abstract class Module extends ModuleBase {
   private final String name;
   private final String description;
   private final Category category;
   private volatile boolean enabled = false;
   private final List<Setting> settings = new ArrayList<>();
   protected final BooleanSetting enabledSetting = new BooleanSetting("Enabled", "Enable/disable module", false);
   protected final KeyBindSetting keyBindSetting = new KeyBindSetting("Key", "Key for quick enable/disable", -1);

   public Module(String name, String description, Category category) {
      this.name = name;
      this.description = description;
      this.category = category;
      this.keyBindSetting.setCallback(this::toggle);
      this.settings.add(this.enabledSetting);
      this.settings.add(this.keyBindSetting);
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public Category getCategory() {
      return this.category;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
      if (enabled) {
         this.onEnable();
      } else {
         this.onDisable();
      }
   }

   public void toggle() {
      this.setEnabled(!this.enabled);
   }

   public abstract void onEnable();
   public abstract void onDisable();

   public void onEndTick() {}
   public void onStartTick() {}
   public void onRenderStart() {}
   public void onRenderAfterEntities(WorldRenderContext ctx) {}
   public void onRenderBeforeEntities(WorldRenderContext ctx) {}
   public void onRenderLate(WorldRenderContext ctx) {}
   public void onRenderHand() {}
   public void onRenderEnd(WorldRenderContext ctx) {}
   public void onRenderAfterSetup(WorldRenderContext ctx) {}
   public void onRenderAfterTranslucent(WorldRenderContext ctx) {}
   public void onPacket(PacketEvent event) {}
   public void onRotation(RotationEvent event) {}
   public void onSpeed(SpeedEvent event) {}
   public void onMovement(MovementEvent event) {}
   public void onRenderHud(RenderHudEvent event) {}
   public void onMouseScroll(MouseMoveEvent event) {}
   public void onChatMessage(ChatMessageEvent event) {}
   public void onChatSend(String message) {}
   public void onUseItem(UseItemEvent event) {}
   public void onAttackEntity(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {}
   public void onPlayerDeath(PlayerEntity player) {}
   public void onServerJoin() {}
   public void onServerDisconnect() {}
   public void onPush() {}
   public void onCameraUpdate() {}
   public ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) { return ActionResult.PASS; }
   public ActionResult onUseEntity(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) { return ActionResult.PASS; }

   public void addSetting(Setting setting) {
      if (setting != null) {
         this.settings.add(setting);
      }
   }

   public void addSettings(Setting... settings) {
      for (Setting setting : settings) {
         this.addSetting(setting);
      }
   }

   public List<Setting> getSettings() {
      return this.settings;
   }

   public String getDisplayName() {
      return this.name;
   }

   public List<Setting> getVisibleSettings() {
      return this.settings;
   }

   public String getDisplayDescription() {
      return this.description;
   }

   public void onSettingChanged(Setting setting) {}
}
