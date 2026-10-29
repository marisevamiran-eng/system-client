package dev.mark.system.internal;

import net.minecraft.client.network.ServerInfo.ResourcePackPolicy;

public class ResourcePackPolicySwitchMap {
   static final int[] $SwitchMap$net$minecraft$client$network$ServerInfo$ResourcePackPolicy = new int[ResourcePackPolicy.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$client$network$ServerInfo$ResourcePackPolicy[ResourcePackPolicy.ENABLED.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror2) {
      }

      try {
         $SwitchMap$net$minecraft$client$network$ServerInfo$ResourcePackPolicy[ResourcePackPolicy.DISABLED.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         $SwitchMap$net$minecraft$client$network$ServerInfo$ResourcePackPolicy[ResourcePackPolicy.PROMPT.ordinal()] = 3;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}
