package dev.mark.system.gui.animation.easing;

public enum EasingFunctionType {
   KV {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         return f2 * f / f3 + f1;
      }
   },
   act {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         f /= f3;
         return f2 * f * f * f + f1;
      }
   },
   CU {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         f = f / f3 - 1.0F;
         return f2 * (f * f * f + 1.0F) + f1;
      }
   },
   ayR {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         f /= f3 / 2.0F;
         if (f < 1.0F) {
            return f2 / 2.0F * f * f * f + f1;
         } else {
            f -= 2.0F;
            return f2 / 2.0F * (f * f * f + 2.0F) + f1;
         }
      }
   },
   agP {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         f /= f3;
         return f2 * f * f * f * f * f + f1;
      }
   },
   auP {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         f /= f3 / 2.0F;
         if (f < 1.0F) {
            return f2 / 2.0F * f * f * f * f * f + f1;
         } else {
            f -= 2.0F;
            return f2 / 2.0F * (f * f * f * f * f + 2.0F) + f1;
         }
      }
   },
   LS {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         f = f / f3 - 1.0F;
         return f2 * (f * f * f * f * f + 1.0F) + f1;
      }
   },
   BM {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         float f4 = 1.70158F;
         float f5;
         return f2 * (f5 = f / f3) * f5 * ((f4 + 1.0F) * f5 - f4) + f1;
      }
   },
   WG {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         float f4 = 1.70158F;
         float f5;
         return f2 * ((f5 = f / f3 - 1.0F) * f5 * ((f4 + 1.0F) * f5 + f4) + 1.0F) + f1;
      }
   },
   Xx {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         if (f == 0.0F) {
            return f1;
         } else if ((f = f / f3) == 1.0F) {
            return f1 + f2;
         } else {
            float f4 = f3 * 0.3F;
            float f5 = f4 / 4.0F;
            return -(float)(f2 * Math.pow(2.0, 10.0F * --f) * Math.sin((f * f3 - f5) * (Math.PI * 2) / f4)) + f1;
         }
      }
   },
   yO {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         if (f == 0.0F) {
            return f1;
         } else if ((f = f / f3) == 1.0F) {
            return f1 + f2;
         } else {
            float f4 = f3 * 0.3F;
            float f5 = f4 / 4.0F;
            return (float)(f2 * Math.pow(2.0, -10.0F * f) * Math.sin((f * f3 - f5) * (Math.PI * 2) / f4)) + f2 + f1;
         }
      }
   },
   aft {
      @Override
      public float b(float f, float f1, float f2, float f3) {
         if ((f = f / f3) < 0.36363637F) {
            return f2 * (7.5625F * f * f) + f1;
         } else if (f < 0.72727275F) {
            float f6;
            return f2 * (7.5625F * (f6 = f - 0.54545456F) * f6 + 0.75F) + f1;
         } else {
            float f4;
            float f5;
            return f < 0.9090909090909091
               ? f2 * (7.5625F * (f4 = f - 0.8181818F) * f4 + 0.9375F) + f1
               : f2 * (7.5625F * (f5 = f - 0.95454544F) * f5 + 0.984375F) + f1;
         }
      }
   };

   public static EasingFunctionType a(String s) {
      return Enum.<EasingFunctionType>valueOf(EasingFunctionType.class, s);
   }

   public abstract float b(float f, float f1, float f2, float f3);

   private static EasingFunctionType[] c() {
      return new EasingFunctionType[]{KV, act, CU, ayR, agP, auP, LS, BM, WG, Xx, yO, aft};
   }
}
