package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.event.RotationEvent;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.util.MathUtil;

public class JumpResetModule extends Module {
   private final BooleanSetting chanceSetting = new BooleanSetting("Chance", "Enable random jump chance", true);
   private final SliderSetting percentSetting = new SliderSetting("Percent", "Jump probability", 80.0, 0.0, 100.0, 0.0);

   public JumpResetModule() {
      super("JumpReset", "Jumps when taking damage from a player", Category.MOVEMENT);
      this.addSettings(this.chanceSetting, this.percentSetting);
   }

   private boolean a() {
      if (this.getWorld() == null) {
         return false;
      } else {
         for (PlayerEntity playerentity : this.getWorld().getPlayers()) {
            if (!playerentity.getAbilities().creativeMode
               && playerentity != this.getPlayer()
               && playerentity.isAlive()
               && playerentity.distanceTo(this.getPlayer()) <= 6.0F) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onRotation(RotationEvent rotationevent) {
      if (!this.isNotInWorld()) {
         PlayerEntity playerentity = this.getPlayer();
         if (playerentity != null) {
            if (this.getClient().currentScreen == null
               && !playerentity.isBlocking()
               && !playerentity.isUsingItem()
               && playerentity.isSprinting()
               && playerentity.getAttacker() instanceof PlayerEntity
               && !playerentity.isOnFire()
               && !(this.getClient().currentScreen instanceof HandledScreen)
               && !playerentity.isTouchingWater()
               && !playerentity.isInsideWall()) {
               MathHelper.nextInt(Random.create(), 1, 100);
               if (playerentity.hurtTime > 6 && playerentity.isOnGround() && !playerentity.isOnFire() && this.a() && !this.chanceSetting.getValue()) {
                  playerentity.jump();
               }

               if (this.chanceSetting.getValue()
                  && playerentity.hurtTime > 6
                  && playerentity.isOnGround()
                  && !playerentity.isOnFire()
                  && MathUtil.d(0, 100, this.percentSetting.getValue())) {
                  playerentity.jump();
               }
            }
         }
      }
   }
}
