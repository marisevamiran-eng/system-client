package dev.mark.system.module.player;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.entity.FreecamEntity;
import dev.mark.system.util.MouseLookHandler;

public class FreeCamModule extends Module {
   private BooleanSetting disableOnDamageSetting = new BooleanSetting("Disable on Damage", "Automatically disable when taking damage", true);
   private SliderSetting moveSpeedSetting = new SliderSetting("Move Speed", "Normal movement speed in FreeCam", 0.3, 0.1, 2.0, 0.05);
   private SliderSetting fastSpeedSetting = new SliderSetting("Fast Speed", "Movement speed when sprinting", 1.0, 0.5, 5.0, 0.1);
   private boolean active = false;
   private FreecamEntity iJ;
   private ClientPlayerEntity originalPlayer;
   private MouseLookHandler PD;
   private float originalHealth = 20.0F;

   public FreeCamModule() {
      super("FreeCam", "Free camera", Category.MOVEMENT);
      this.addSettings(this.disableOnDamageSetting, this.moveSpeedSetting, this.fastSpeedSetting);
   }

   @Override
   public void onEnable() {
      this.PD = new MouseLookHandler();
      this.a();
   }

   @Override
   public void onDisable() {
      this.b();
   }

   @Override
   public void onEndTick() {
      if (this.active && this.iJ != null) {
         this.PD.a(this.iJ, 1.0F);
      }

      if (this.active && this.iJ != null) {
         this.iJ.updateSpeeds((float)this.moveSpeedSetting.getValue(), (float)this.fastSpeedSetting.getValue());
         if (this.disableOnDamageSetting.getValue()) {
            this.c();
         }
      }
   }

   private void a() {
      ClientPlayerEntity clientplayerentity = this.getClientPlayer();
      if (!this.isNotInWorld()) {
         this.originalPlayer = clientplayerentity;
         this.originalHealth = clientplayerentity.getHealth();
         this.iJ = new FreecamEntity(
            this.getWorld(),
            clientplayerentity.getPos(),
            clientplayerentity.getYaw(),
            clientplayerentity.getPitch(),
            (float)this.moveSpeedSetting.getValue(),
            (float)this.fastSpeedSetting.getValue()
         );
         this.getClientWorld().addEntity(this.iJ);
         this.getClient().setCameraEntity(this.iJ);
         this.active = true;
      }
   }

   private void b() {
      if (this.originalPlayer != null) {
         this.getClient().setCameraEntity(this.originalPlayer);
         if (this.iJ != null) {
            this.iJ.restoreGameMode();
            this.iJ.remove(RemovalReason.DISCARDED);
            this.iJ = null;
         }

         this.active = false;
         if (this.PD != null) {
            this.PD.b();
         }

         this.originalHealth = 20.0F;
      }
   }

   private void c() {
      ClientPlayerEntity clientplayerentity = this.getClientPlayer();
      if (clientplayerentity != null && this.originalPlayer != null) {
         float f = clientplayerentity.getHealth();
         if (f < this.originalHealth) {
            this.setEnabled(false);
            return;
         }

         this.originalHealth = f;
      }
   }

   @Override
   public void onPlayerDeath(PlayerEntity playerentity) {
      super.onPlayerDeath(playerentity);
      if (this.enabledSetting.getValue()) {
         this.setEnabled(false);
      }
   }
}
