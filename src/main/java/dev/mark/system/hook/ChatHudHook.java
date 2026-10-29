package dev.mark.system.hook;

import dev.mark.system.enums.HookType;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.render.Camera;
import dev.mark.system.render.CustomChatHud;
import dev.mark.system.render.CustomInGameHud;
import dev.mark.system.util.UnsafeFieldAccessor;

public class ChatHudHook extends Hook {
   public ChatHudHook() {
      super(HookType.Init);
   }

   @Override
   public void hook() {
      new UnsafeFieldAccessor<CustomChatHud>(this.mc.inGameHud, CustomInGameHud.class, ChatHud.class).check(new CustomChatHud(this.mc));
   }

   @Override
   public void unHook() {
      new UnsafeFieldAccessor<Camera>(this.mc.gameRenderer, GameRendererHook.class, Camera.class).check(new Camera());
   }
}
