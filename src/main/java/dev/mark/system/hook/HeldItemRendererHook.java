package dev.mark.system.hook;

import dev.mark.system.enums.HookType;
import net.minecraft.client.render.item.HeldItemRenderer;
import dev.mark.system.render.CustomGameRenderer;
import dev.mark.system.render.CustomHeldItemRenderer;
import dev.mark.system.util.UnsafeFieldAccessor;

public class HeldItemRendererHook extends Hook {
   public HeldItemRendererHook() {
      super(HookType.Init);
   }

   @Override
   public void hook() {
      new UnsafeFieldAccessor<CustomHeldItemRenderer>(this.mc.gameRenderer, CustomGameRenderer.class, HeldItemRenderer.class)
         .check(new CustomHeldItemRenderer(this.mc, this.mc.getEntityRenderDispatcher(), this.mc.getItemRenderer(), this.mc.getItemModelManager()));
   }

   @Override
   public void unHook() {
   }
}
