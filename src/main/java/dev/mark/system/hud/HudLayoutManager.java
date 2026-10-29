package dev.mark.system.hud;

import dev.mark.system.enums.ConstraintSide;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HudLayoutManager {
   private final Map<HudComponent, List<HudConstraint>> IQ = new HashMap<HudComponent, List<HudConstraint>>();
   private long Mw = System.nanoTime();
   private List<HudComponent> aQ = new ArrayList<HudComponent>();

   public void a(List<HudComponent> list) {
      this.aQ = list;
   }

   public void b() {
      this.IQ.clear();

      for (HudComponent hudcomponent : this.aQ) {
         HudComponent hudcomponent1 = this.c(hudcomponent);
         if (hudcomponent1 != null) {
            float f = hudcomponent1.getY() - (hudcomponent.getY() + this.g(hudcomponent));
            HudConstraint hudconstraint = new HudConstraint(hudcomponent1, hudcomponent, ConstraintSide.KC, f);
            this.IQ.computeIfAbsent(hudcomponent1, hudcomponent2 -> {
               return new ArrayList<HudConstraint>();
            }).add(hudconstraint);
         }
      }
   }

   private HudComponent c(HudComponent hudcomponent) {
      float f = hudcomponent.getY() + this.g(hudcomponent);
      float f1 = hudcomponent.getX() + this.f(hudcomponent) / 2.0F;
      HudComponent hudcomponent1 = null;
      float f2 = Float.MAX_VALUE;

      for (HudComponent hudcomponent2 : this.aQ) {
         if (hudcomponent2 != hudcomponent && !(hudcomponent2.getY() <= f)) {
            float f3 = hudcomponent2.getX() + this.f(hudcomponent2) / 2.0F;
            boolean flag = Math.abs(f1 - f3) < this.f(hudcomponent) / 2.0F + this.f(hudcomponent2) / 2.0F;
            if (flag) {
               float f4 = hudcomponent2.getY() - f;
               if (f4 < f2 && f4 < 100.0F) {
                  f2 = f4;
                  hudcomponent1 = hudcomponent2;
               }
            }
         }
      }

      return hudcomponent1;
   }

   public void d() {
      long i = System.nanoTime();
      float f = (float)(i - this.Mw) / 1.0E9F;
      this.Mw = i;

      for (List list : this.IQ.values()) {
         for (Object _hc : list) {
            HudConstraint hudconstraint = (HudConstraint)_hc;
            hudconstraint.a();
         }
      }

      for (List list1 : this.IQ.values()) {
         for (Object _hc1 : list1) {
            HudConstraint hudconstraint1 = (HudConstraint)_hc1;
            hudconstraint1.b(f);
         }
      }
   }

   public void e() {
      this.IQ.clear();
   }

   private float f(HudComponent hudcomponent) {
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

   private float g(HudComponent hudcomponent) {
      return hudcomponent.getTotalHeight();
   }
}
