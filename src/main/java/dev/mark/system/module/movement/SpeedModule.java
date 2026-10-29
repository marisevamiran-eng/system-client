package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;

public class SpeedModule extends Module {
   private ListSetting modeSetting = new ListSetting(
      "Mode", "Module mode selection", Arrays.<String>asList("GrimCollision", "GrimDistance"), Collections.<String>singletonList("GrimCollision"), false
   );
   private SliderSetting radiusSetting = new SliderSetting("Radius", "Module action zone", 1.0, 0.5, 2.0, 0.1, "", 1);
   private SliderSetting distanceSetting = new SliderSetting("Distance", "Distance to target", 2.5, 1.0, 4.0, 0.1, "", 1);
   private SliderSetting speedSetting = new SliderSetting("Speed", "Movement speed", 3.0, 1.0, 10.0, 0.1, "", 1);
   private BooleanSetting onlyPlayersSetting = new BooleanSetting("Players Only", "", true);

   public SpeedModule() {
      super("Speed", "Speed up (Grim anti-cheat only)", Category.MOVEMENT);
      this.addSettings(this.modeSetting, this.radiusSetting, this.distanceSetting, this.speedSetting, this.onlyPlayersSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onEndTick() {
      if (!this.isNotInWorld()) {
         if (this.modeSetting.getFirst().contains("GrimCollision") && this.b()) {
            double[] adouble = new double[]{0.0, 0.0};
            int i = 0;

            for (Entity entity : this.getClientWorld().getEntities()) {
               if ((!this.onlyPlayersSetting.getValue() || entity instanceof PlayerEntity)
                  && entity != this.getPlayer()
                  && (entity instanceof LivingEntity || entity instanceof PlayerEntity || entity instanceof BoatEntity)) {
                  double d0 = this.getPlayer().squaredDistanceTo(entity);
                  double d1 = Math.max(0.0, this.radiusSetting.getDoubleValue() - Math.sqrt(d0));
                  if (this.getPlayer().getBoundingBox().expand(this.radiusSetting.getDoubleValue()).intersects(entity.getBoundingBox())) {
                     i++;
                     double[] adouble1 = this.a(this.speedSetting.getDoubleValue() * 0.01 * d1);
                     adouble[0] += adouble1[0];
                     adouble[1] += adouble1[1];
                  }
               }
            }

            if (i > 0) {
               adouble[0] += (adouble[0] - adouble[0]) * 0.6;
               adouble[1] += (adouble[1] - adouble[1]) * 0.6;
               double d2 = this.speedSetting.getDoubleValue() * 0.02;
               adouble[0] = Math.max(Math.min(adouble[0], d2), -d2);
               adouble[1] = Math.max(Math.min(adouble[1], d2), -d2);
               this.getPlayer().addVelocity(adouble[0], 0.0, adouble[1]);
               adouble[0] = adouble[0];
               adouble[1] = adouble[1];
            }
         }
      }
   }

   public double[] a(double d0) {
      float f = this.getClientPlayer().input.movementForward;
      float f1 = this.getClientPlayer().input.movementSideways;
      float f2 = this.getClientPlayer().getYaw();
      if (f != 0.0F) {
         if (f1 > 0.0F) {
            f2 += f > 0.0F ? -45.0F : 45.0F;
         } else if (f1 < 0.0F) {
            f2 += f > 0.0F ? 45.0F : -45.0F;
         }

         f1 = 0.0F;
         if (f > 0.0F) {
            f = 1.0F;
         } else if (f < 0.0F) {
            f = -1.0F;
         }
      }

      double d1 = Math.sin(Math.toRadians(f2 + 90.0F));
      double d2 = Math.cos(Math.toRadians(f2 + 90.0F));
      double d3 = f * d0 * d2 + f1 * d0 * d1;
      double d4 = f * d0 * d1 - f1 * d0 * d2;
      return new double[]{d3, d4};
   }

   public boolean b() {
      return this.getClientPlayer().input.movementForward != 0.0 || this.getClientPlayer().input.movementSideways != 0.0;
   }
}
