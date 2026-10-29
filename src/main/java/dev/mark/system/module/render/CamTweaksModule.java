package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.SliderSetting;

public class CamTweaksModule extends Module {
   private BooleanSetting smoothTransitionSetting = new BooleanSetting("Smooth Transition", "Enables smooth camera transition animation", true);
   private SliderSetting transitionSpeedSetting = new SliderSetting("Transition Speed", "Speed of camera transition animation", 0.1F, 0.1F, 3.0, 0.05F);
   private SliderSetting cameraDistanceSetting = new SliderSetting(
      "Camera Distance", "Maximum camera distance from player", 4.0, -1.0, 10.0, 0.5, "", 1
   );
   private SliderSetting xOffsetSetting = new SliderSetting("X Offset", "Camera offset on X axis (left/right)", 0.0, -5.0, 5.0, 0.1F, "", 1);
   private SliderSetting yOffsetSetting = new SliderSetting("Y Offset", "Camera offset on Y axis (up/down)", 0.0, -5.0, 5.0, 0.1F, "", 1);
   private BooleanSetting onlyF5Setting = new BooleanSetting("F5 Only", "Apply camera delay only in third person", true);
   private SliderSetting xDelaySetting = new SliderSetting("X Delay", "Position delay on X axis (1.0 = no delay)", 0.04F, 0.0, 1.0, 0.01F, "", 2);
   private SliderSetting yDelaySetting = new SliderSetting("Y Delay", "Position delay on Y axis (1.0 = no delay)", 0.6F, 0.0, 1.0, 0.01F, "", 2);
   private SliderSetting zDelaySetting = new SliderSetting("Z Delay", "Position delay on Z axis (1.0 = no delay)", 0.04F, 0.0, 1.0, 0.01F, "", 2);
   private SliderSetting rotationDelaySetting = new SliderSetting(
      "Rotation Delay", "Camera rotation delay (1.0 = no delay)", 1.0, 0.0, 1.0, 0.01F, "", 2
   );
   private BooleanSetting throughWallsSetting = new BooleanSetting("Through Walls", "Allow camera to pass through walls", true);
   private float aeQ = 0.0F;
   private float Hs = 0.0F;
   private float MB = 0.0F;
   private boolean eI = false;
   private double afs = 0.0;

   public CamTweaksModule() {
      super("CamTweaks", "Allows looking through walls in F5 and changing camera animation", Category.VISUAL);
      this.addSettings(
         this.smoothTransitionSetting,
         this.transitionSpeedSetting,
         this.cameraDistanceSetting,
         this.xOffsetSetting,
         this.yOffsetSetting,
         this.throughWallsSetting,
         this.onlyF5Setting,
         this.xDelaySetting,
         this.yDelaySetting,
         this.zDelaySetting,
         this.rotationDelaySetting
      );
   }

   @Override
   public void onEnable() {
      this.aeQ = 0.0F;
      this.Hs = 0.0F;
      this.MB = 0.0F;
      this.afs = 0.0;
      this.eI = false;
   }

   @Override
   public void onDisable() {
      this.aeQ = 0.0F;
      this.Hs = 0.0F;
      this.MB = 0.0F;
      this.afs = 0.0;
   }

   @Override
   public void onEndTick() {
      if (this.getOptions() != null) {
         boolean flag = !this.getOptions().getPerspective().isFirstPerson();
         this.Hs = this.aeQ;
         if (flag) {
            this.MB = this.cameraDistanceSetting.getFloatValue();
         } else {
            this.MB = 0.0F;
         }

         if (this.smoothTransitionSetting.getValue()) {
            float f = this.MB - this.aeQ;
            double d0 = f / (this.transitionSpeedSetting.getFloatValue() * 10.0F);
            this.afs = this.afs + (d0 - this.afs) * 0.2;
            if (Math.abs(f) < 0.5F) {
               this.afs *= 0.7;
            }

            double d1 = (Math.random() - 0.5) * 0.002;
            this.aeQ = this.aeQ + (float)(this.afs + d1);
            this.afs *= 0.92;
            float f1 = -1.0F;
            float f2 = 10.0F;
            if (this.aeQ < f1) {
               this.aeQ = f1;
            }

            if (this.aeQ > f2) {
               this.aeQ = f2;
            }

            if (flag != this.eI) {
               this.eI = flag;
            }
         } else {
            this.aeQ = this.MB;
            this.Hs = this.MB;
            this.afs = 0.0;
            this.eI = flag;
         }
      }
   }

   public float a(float f) {
      return !this.smoothTransitionSetting.getValue() ? this.aeQ : this.Hs + (this.aeQ - this.Hs) * f;
   }

   public SliderSetting b() {
      return this.xOffsetSetting;
   }

   public SliderSetting c() {
      return this.yOffsetSetting;
   }

   public BooleanSetting d() {
      return this.onlyF5Setting;
   }

   public SliderSetting e() {
      return this.xDelaySetting;
   }

   public SliderSetting f() {
      return this.yDelaySetting;
   }

   public SliderSetting g() {
      return this.zDelaySetting;
   }

   public SliderSetting h() {
      return this.rotationDelaySetting;
   }

   public BooleanSetting i() {
      return this.throughWallsSetting;
   }

   public float j() {
      return this.aeQ;
   }
}
