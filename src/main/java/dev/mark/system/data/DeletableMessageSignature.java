package dev.mark.system.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.message.MessageSignatureData;

@Environment(EnvType.CLIENT)
public class DeletableMessageSignature {
   private final MessageSignatureData signature;
   private final int deletableAfter;

   public DeletableMessageSignature(MessageSignatureData messagesignaturedata, int i) {
      this.signature = messagesignaturedata;
      this.deletableAfter = i;
   }

   public MessageSignatureData signature() { return this.signature; }
   public int deletableAfter() { return this.deletableAfter; }
}
