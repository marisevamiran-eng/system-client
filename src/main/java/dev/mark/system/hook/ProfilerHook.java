package dev.mark.system.hook;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mark.system.core.ClientMain;
import dev.mark.system.enums.HookType;
import java.util.Queue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.util.profiler.TickTimeTracker;
import dev.mark.system.util.UnsafeFieldAccessor;

public class ProfilerHook extends Hook {
   public static ProfilerHook INSTANCE = new ProfilerHook();

   @Override
   public void hook() {
      RenderSystem.recordRenderCall(ClientMain::new);
   }

   public ProfilerHook() {
      super(HookType.Init);
   }

   @Override
   public void unHook() {
   }
}
