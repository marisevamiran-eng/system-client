package dev.mark.system.hook;

import dev.mark.system.enums.HookType;
import net.minecraft.client.render.Camera;
import dev.mark.system.render.CustomGameRenderer;
import dev.mark.system.util.UnsafeFieldAccessor;

public class CameraHook extends Hook {
   public CameraHook() {
      super(HookType.Init);
   }

   @Override
   public void hook() {
      new UnsafeFieldAccessor<CustomCamera>(this.mc.gameRenderer, CustomGameRenderer.class, Camera.class).check(new CustomCamera());
   }

   @Override
   public void unHook() {
   }
}
