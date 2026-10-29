package dev.mark.system.hook;

import dev.mark.system.enums.HookType;
import net.minecraft.client.gui.hud.BossBarHud;
import dev.mark.system.render.CustomInGameHud;
import dev.mark.system.util.UnsafeFieldAccessor;

public class BossBarHudHook extends Hook {
   public BossBarHudHook() {
      super(HookType.Init);
   }

   @Override
   public void hook() {
      new UnsafeFieldAccessor<CustomBossBarHud>(this.mc.inGameHud, CustomInGameHud.class, BossBarHud.class).check(new CustomBossBarHud(this.mc));
   }

   @Override
   public void unHook() {
   }
}
