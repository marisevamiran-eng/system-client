package dev.mark.system.internal;

import net.minecraft.util.hit.HitResult.Type;

public class HitResultTypeSwitchMap {
   static final int[] KZ = new int[Type.values().length];

   static {
      try {
         KZ[Type.ENTITY.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         KZ[Type.BLOCK.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
