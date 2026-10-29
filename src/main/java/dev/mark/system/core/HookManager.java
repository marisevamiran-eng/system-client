package dev.mark.system.core;

import dev.mark.system.enums.HookType;
import dev.mark.system.hook.BossBarHudHook;
import dev.mark.system.hook.CameraHook;
import dev.mark.system.hook.ChatHudHook;
import dev.mark.system.hook.GameRendererHook;
import dev.mark.system.hook.GameRendererInitHook;
import dev.mark.system.hook.HeldItemRendererHook;
import dev.mark.system.hook.Hook;
import dev.mark.system.hook.InGameHudHook;
import dev.mark.system.hook.PlayerListHudHook;
import dev.mark.system.hook.TextRendererHook;
import dev.mark.system.hook.WorldRendererHook;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import dev.mark.system.hook.ProfilerHook;
import dev.mark.system.resource.CustomResourceManager;

public class HookManager {
   private final List<Hook> hooks = new ArrayList<Hook>();
   private static volatile boolean hooksInitialized = false;
   private static HookManager pendingInstance;

   public HookManager() {
      this.hooks
         .addAll(
            Arrays.asList(
               dev.mark.system.hook.ProfilerHook.INSTANCE,
               new GameRendererInitHook(),
               new GameRendererHook(),
               new WorldRendererHook(),
               new CameraHook(),
               new InGameHudHook(),
               new PlayerListHudHook(),
               new BossBarHudHook(),
               new HeldItemRendererHook(),
               new ChatHudHook(),
               new TextRendererHook()
            )
         );
      pendingInstance = this;
      ClientTickEvents.END_CLIENT_TICK.register((EndTick)minecraftclient -> {
         tryInitializeHooks();
      });
   }

   private static void tryInitializeHooks() {
      if (!hooksInitialized && pendingInstance != null && MinecraftClient.getInstance().getResourceManager() != null) {
         new CustomResourceManager();
         pendingInstance.initHook();
         hooksInitialized = true;
         pendingInstance = null;
      }
   }

   public void initHook() {
      this.hooks.stream().filter(hook -> {
         return hook.getType() == HookType.Init;
      }).forEach(Hook::hook);
   }

   public void unHook() {
      this.hooks.forEach(Hook::unHook);
   }
}
