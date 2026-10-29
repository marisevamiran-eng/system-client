package dev.mark.system.enums;

public enum HookType {
   Init,
   FirstWorld,
   EveryWorld;

   private static HookType[] $values() {
      return new HookType[]{Init, FirstWorld, EveryWorld};
   }
}
