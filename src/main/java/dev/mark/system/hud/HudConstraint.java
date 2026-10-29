package dev.mark.system.hud;

import dev.mark.system.enums.ConstraintSide;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HudConstraint {
   private final HudComponent apN;
   private final HudComponent UF;
   private final ConstraintSide aee;
   private float akV;
   private float abj;
   private float hl;
   private static final float afY = 12.0F;

   public HudConstraint(HudComponent hudcomponent, HudComponent hudcomponent1, ConstraintSide constraintside, float f) {
      this.apN = hudcomponent;
      this.UF = hudcomponent1;
      this.aee = constraintside;
      this.akV = f;
      this.abj = hudcomponent.getY();
      this.hl = this.d(hudcomponent1);
   }

   public void a() {
      if (this.aee == ConstraintSide.KC) {
         float f = this.d(this.UF);
         float f1 = f - this.hl;
         if (Math.abs(f1) > 0.1F) {
            float f2 = this.UF.getY() + f + this.akV;
            this.apN.Y(f2);
            this.hl = f;
         }
      }
   }

   public void b(float f) {
   }

   private float c(HudComponent hudcomponent) {
      float f = hudcomponent.getWidth();
      if (hudcomponent.getClass().getSimpleName().equals("DynamicIslandComponent")) {
         try {
            Field field = hudcomponent.getClass().getDeclaredField("currentWidth");
            field.setAccessible(true);
            float f1 = field.getFloat(hudcomponent);
            if (f1 > 0.0F) {
               f = f1;
            }
         } catch (Exception exception1) {
         }
      } else if (hudcomponent.getClass().getSimpleName().equals("WaterMarkHudComponent")) {
         try {
            Method method = hudcomponent.getClass().getDeclaredMethod("calculateBoxWidth");
            method.setAccessible(true);
            f = (Float)method.invoke(hudcomponent) + 1.0F;
         } catch (Exception exception) {
         }
      }

      return f;
   }

   private float d(HudComponent hudcomponent) {
      return hudcomponent.getTotalHeight();
   }

   public HudComponent e() {
      return this.apN;
   }

   public HudComponent f() {
      return this.UF;
   }

   public ConstraintSide g() {
      return this.aee;
   }

   public float h() {
      return this.akV;
   }

   public float i() {
      return this.abj;
   }

   public float j() {
      return this.hl;
   }
}
