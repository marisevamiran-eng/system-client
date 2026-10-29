package dev.mark.system.module.player;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.util.List;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;

import dev.mark.system.util.DangerDetector;
public class AutoLeaveModule extends Module {
   private final SliderSetting distanceSetting = new SliderSetting(
      "Distance", "Minimum distance to player to trigger", 10.0, 1.0, 150.0, 1.0, " blocks", 0
   );
   private final BooleanSetting crystalsSetting = new BooleanSetting("Crystals", "Disconnect when crystal detected nearby", false);
   private final SliderSetting crystalDistanceSetting = new SliderSetting(
      "Crystal Distance", "Maximum distance to crystal", 6.0, 1.0, 12.0, 0.5, " blocks", 1
   );
   private final BooleanSetting tntSetting = new BooleanSetting("TNT", "Disconnect when activated TNT detected nearby", true);
   private final SliderSetting tntDistanceSetting = new SliderSetting("TNT Distance", "Maximum distance to TNT", 10.0, 1.0, 50.0, 1.0, " blocks", 0);
   private final SliderSetting tntFuseSetting = new SliderSetting(
      "TNT Fuse", "Maximum remaining TNT fuse to trigger", 40.0, 1.0, 80.0, 1.0, " ticks", 0
   );
   private final ListSetting exitModeSetting = new ListSetting(
      "Exit Mode", "Method to leave the server", List.<String>of("Disconnect", "/hub"), List.<String>of("Disconnect"), false
   );
   private boolean Pd = false;
   private String Mr = "";

   public AutoLeaveModule() {
      super("AutoLeave", "Automatically disconnects when a player, crystal or TNT is nearby", Category.PLAYER);
      this.crystalDistanceSetting.setVisibilitySupplier(this.crystalsSetting::getValue);
      this.tntDistanceSetting.setVisibilitySupplier(this.tntSetting::getValue);
      this.tntFuseSetting.setVisibilitySupplier(this.tntSetting::getValue);
      this.addSettings(
         this.distanceSetting,
         this.crystalsSetting,
         this.crystalDistanceSetting,
         this.tntSetting,
         this.tntDistanceSetting,
         this.tntFuseSetting,
         this.exitModeSetting
      );
   }

   @Override
   public String getDisplayName() {
      if (this.Mr.isEmpty()) {
         return super.getDisplayName();
      } else {
         String s = this.Mr;
         String s1 = super.getDisplayName();
         return s1 + " §7[" + s + "]";
      }
   }

   @Override
   public void onEnable() {
      this.Pd = false;
      this.Mr = "";
   }

   @Override
   public void onDisable() {
      this.Pd = false;
   }

   @Override
   public void onEndTick() {
      if (this.isEnabled() && this.hasPlayerAndWorld() && !this.Pd) {
         PlayerEntity playerentity = this.a();
         if (playerentity != null) {
            double d0 = this.getPlayer().distanceTo(playerentity);
            if (d0 <= this.distanceSetting.getValue()) {
               int i = (int)Math.round(d0);
               String s = playerentity.getName().getString();
               this.Mr = s + " " + i + "m";
               this.b();
               this.Pd = true;
               return;
            }
         }

         if (this.crystalsSetting.getValue() && DangerDetector.a(this.getWorld(), this.getPlayer(), this.crystalDistanceSetting.getValue())) {
            this.Mr = "Crystal";
            this.b();
            this.Pd = true;
         } else {
            if (this.tntSetting.getValue() && DangerDetector.b(this.getWorld(), this.getPlayer(), this.tntDistanceSetting.getValue(), (int)this.tntFuseSetting.getValue())) {
            this.Mr = "TNT";
               this.b();
               this.Pd = true;
            }
         }
      }
   }

   private PlayerEntity a() {
      if (this.getWorld() != null && this.getPlayer() != null) {
         PlayerEntity playerentity = null;
         double d0 = Double.MAX_VALUE;

         for (PlayerEntity playerentity1 : this.getWorld().getPlayers()) {
            if (playerentity1 != this.getPlayer() && !this.isFriendPlayer(playerentity1)) {
               double d1 = this.getPlayer().distanceTo(playerentity1);
               if (d1 < d0) {
                  d0 = d1;
                  playerentity = playerentity1;
               }
            }
         }

         return playerentity;
      } else {
         return null;
      }
   }

   private void b() {
      String s = this.exitModeSetting.getFirst();
      if ("Disconnect".equals(s)) {
         if (this.getNetworkHandler() != null) {
            this.getNetworkHandler().getConnection().disconnect(Text.of("Auto-leave"));
         }
      } else if ("/hub".equals(s) && this.getPlayer() != null) {
         this.getNetworkHandler().sendChatCommand("hub");
      }

      this.setEnabled(false);
   }

   @Override
   public void onServerJoin() {
      this.Pd = false;
      this.Mr = "";
   }

   @Override
   public void onServerDisconnect() {
      this.Pd = false;
   }
}
