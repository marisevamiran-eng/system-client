package dev.mark.system.hook;

import dev.mark.system.enums.HookType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import dev.mark.system.render.CustomInGameHud;
import dev.mark.system.render.CustomPlayerListHud;
import dev.mark.system.util.UnsafeFieldAccessor;

public class PlayerListHudHook extends Hook {
   public static MinecraftClient mc = MinecraftClient.getInstance();

   public PlayerListHudHook() {
      super(HookType.Init);
   }

   @Override
   public void hook() {
      new UnsafeFieldAccessor<CustomPlayerListHud>(mc.inGameHud, CustomInGameHud.class, PlayerListHud.class).check(new CustomPlayerListHud(mc, mc.inGameHud));
   }

   @Override
   public void unHook() {
   }
}
