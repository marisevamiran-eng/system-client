package dev.mark.system.net;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.ChatHudLine;
import dev.mark.system.data.DeletableMessageSignature;

@Environment(EnvType.CLIENT)
public class ChatSnapshot {
   public final List<ChatHudLine> messages;
   public final List<String> messageHistory;
   public final List<DeletableMessageSignature> removalQueue;

   public ChatSnapshot(List<ChatHudLine> list, List<String> list1, List<DeletableMessageSignature> list2) {
      this.messages = list;
      this.messageHistory = list1;
      this.removalQueue = list2;
   }
}
