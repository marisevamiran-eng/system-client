package dev.mark.system.util;

import dev.mark.system.module.ModuleBase;

public class ElapsedTimer {
   public static float art = 0.0F;
   long QK = System.nanoTime();

   public ElapsedTimer() {
   }

   public float a() {
      return (float)(System.nanoTime() - this.QK) / (float)ModuleBase.NANOS_PER_SECOND;
   }
}
