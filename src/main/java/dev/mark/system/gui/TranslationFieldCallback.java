package dev.mark.system.gui;

import net.minecraft.client.MinecraftClient;
import dev.mark.system.util.ChatUtil;

import dev.mark.system.util.ResultCallback;
import dev.mark.system.gui.screen.CustomChatScreen;
public class TranslationFieldCallback implements ResultCallback {
   final CustomChatScreen mi;

   public TranslationFieldCallback(CustomChatScreen CustomChatScreen) {
      this.mi = CustomChatScreen;
   }

   @Override
   public void onResult(String s, String s1, String s2) {
      MinecraftClient.getInstance().execute(() -> {
         CustomChatScreen.access$000(this.mi).setText(s);
         CustomChatScreen.access$100(this.mi).setCursor(s.length(), false);
         ChatUtil.sendSuccess("Переведено (" + s1 + " → " + s2 + ")");
         this.mi.ajB = false;
      });
   }

   @Override
   public void onError(String s) {
      MinecraftClient.getInstance().execute(() -> {
         ChatUtil.sendError(s);
         this.mi.ajB = false;
      });
   }
}
