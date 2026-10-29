package dev.mark.system.hook;

import dev.mark.system.enums.HookType;
import java.util.function.Function;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import dev.mark.system.render.CustomTextRenderer;
import dev.mark.system.util.UnsafeFieldAccessor;

public class TextRendererHook extends Hook {
   private TextRenderer originalTextRenderer;

   public TextRendererHook() {
      super(HookType.Init);
   }

   @Override
   public void hook() {
      this.originalTextRenderer = this.mc.textRenderer;
      UnsafeFieldAccessor unsafefieldaccessor = new UnsafeFieldAccessor(this.originalTextRenderer, TextRenderer.class, Function.class, 0);
      Function function = (Function)unsafefieldaccessor.getValue();
      UnsafeFieldAccessor unsafefieldaccessor1 = new UnsafeFieldAccessor(this.originalTextRenderer, TextRenderer.class, 7);
      boolean flag = unsafefieldaccessor1.getBoolean();
      CustomTextRenderer customtextrenderer = new CustomTextRenderer(function, flag);
      new UnsafeFieldAccessor<CustomTextRenderer>(this.mc, MinecraftClient.class, TextRenderer.class).check(customtextrenderer);
   }

   @Override
   public void unHook() {
      new UnsafeFieldAccessor<TextRenderer>(this.mc, MinecraftClient.class, TextRenderer.class).check(this.originalTextRenderer);
   }
}
