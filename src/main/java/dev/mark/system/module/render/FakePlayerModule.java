package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import com.mojang.authlib.GameProfile;
import dev.mark.system.enums.Category;
import java.util.UUID;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Entity.RemovalReason;

public class FakePlayerModule extends Module {
   private OtherClientPlayerEntity fakePlayer;

   public FakePlayerModule() {
      super("FakePlayer", "Creates a fake player", Category.VISUAL);
   }

   @Override
   public void onEnable() {
      if (this.hasPlayerAndWorld()) {
         this.fakePlayer = new OtherClientPlayerEntity(this.getClientWorld(), new GameProfile(UUID.randomUUID(), "System"));
         this.fakePlayer.setPosition(this.getPlayer().getPos());
         this.fakePlayer.setYaw(this.getPlayer().getYaw());
         this.fakePlayer.setPitch(this.getPlayer().getPitch());
         this.fakePlayer.bodyYaw = this.getPlayer().bodyYaw;
         this.fakePlayer.headYaw = this.getPlayer().headYaw;

         for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            this.fakePlayer.equipStack(equipmentslot, this.getPlayer().getEquippedStack(equipmentslot).copy());
         }

         this.getClientWorld().addEntity(this.fakePlayer);
      }
   }

   @Override
   public void onDisable() {
      if (this.hasPlayerAndWorld() && this.fakePlayer != null) {
         this.getClientWorld().removeEntity(this.fakePlayer.getId(), RemovalReason.KILLED);
         this.fakePlayer = null;
      }
   }

   @Override
   public void onServerDisconnect() {
      this.setEnabled(false);
   }

   @Override
   public void onServerJoin() {
      this.setEnabled(false);
   }
}
