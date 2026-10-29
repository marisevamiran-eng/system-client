package dev.mark.system.render;

import net.minecraft.client.render.FrameGraphBuilder.Profiler;
import dev.mark.system.render.CustomWorldRenderer;

public class WorldRendererProfilerAdapter implements Profiler {
   final net.minecraft.util.profiler.Profiler val$profiler;

   WorldRendererProfilerAdapter(CustomWorldRenderer customworldrenderer, net.minecraft.util.profiler.Profiler profiler) {
      this.val$profiler = profiler;
   }

   public void push(String s) {
      this.val$profiler.push(s);
   }

   public void pop(String s) {
      this.val$profiler.pop();
   }
}
