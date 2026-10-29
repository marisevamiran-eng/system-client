package dev.mark.system.gui;

import net.minecraft.client.MinecraftClient;
import dev.mark.system.util.ChatUtil;

import dev.mark.system.util.ResultCallback;
import dev.mark.system.gui.screen.CustomChatScreen;
public class TranslationChatCallback implements ResultCallback {
   final MinecraftClient yN;
   final CustomChatScreen jJ;

   public TranslationChatCallback(CustomChatScreen CustomChatScreen, MinecraftClient minecraftclient) {
      this.jJ = CustomChatScreen;
      this.yN = minecraftclient;
   }

   @Override
   public void onResult(String s, String s1, String s2) {
      this.yN.execute(() -> {
         ChatUtil.sendMessage("§7[§b" + s1 + " → " + s2 + "§7] §f" + s);
         this.jJ.ajB = false;
      });
   }

   @Override
   public void onError(String s) {
      this.yN.execute(() -> {
         ChatUtil.sendError(s);
         this.jJ.ajB = false;
      });
   }
}
