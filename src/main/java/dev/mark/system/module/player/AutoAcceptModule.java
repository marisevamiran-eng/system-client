package dev.mark.system.module.player;

import dev.mark.system.module.Module;

import dev.mark.system.core.FriendManager;
import dev.mark.system.enums.Category;
import dev.mark.system.enums.PacketDirection;
import dev.mark.system.event.PacketEvent;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.setting.TextSetting;

import dev.mark.system.data.SmallStateData;
public class AutoAcceptModule extends Module {
   private final BooleanSetting onlyFriendsSetting = new BooleanSetting("Only Friends", "Accept TP only from friends", true);
   private final SliderSetting delaySetting = new SliderSetting("Delay", "Delay before accepting TP", 0.0, 0.0, 40.0, 1.0, " ticks", 0);
   private final TextSetting commandSetting = new TextSetting("Command", "Command to accept TP", "tpaccept", "/command");
   private int jL = 0;
   private boolean Im = false;

   public AutoAcceptModule() {
      super("AutoAccept", "Automatically accepts teleport requests", Category.PLAYER);
      this.addSettings(this.onlyFriendsSetting, this.delaySetting, this.commandSetting);
   }

   @Override
   public void onEnable() {
      this.Im = false;
      this.jL = 0;
   }

   @Override
   public void onDisable() {
      this.Im = false;
      this.jL = 0;
   }

   @Override
   public void onEndTick() {
      if (!this.isNotInWorld() && this.Im) {
         if (this.jL > 0) {
            this.jL--;
         } else {
            String s = this.commandSetting.getValue().trim();
            if (!s.isEmpty() && this.getNetworkHandler() != null) {
               this.getNetworkHandler().sendChatCommand(s);
            }

            this.Im = false;
         }
      }
   }

   @Override
   public void onPacket(PacketEvent packetevent) {
      if (packetevent.getType() == PacketDirection.RECEIVE) {
         if (packetevent.getPacket() instanceof GameMessageS2CPacket gamemessages2cpacket) {
            GameMessageS2CPacket gamemessages2cpacket1 = gamemessages2cpacket;

            Text text1;
            try {
               text1 = gamemessages2cpacket1.content();
            } catch (Throwable throwable1) {
               throw new RuntimeException(throwable1.toString(), throwable1);
            }

            Text text = text1;
            gamemessages2cpacket1 = gamemessages2cpacket;

            boolean flag1;
            try {
               flag1 = gamemessages2cpacket1.overlay();
            } catch (Throwable throwable) {
               throw new RuntimeException(throwable.toString(), throwable);
            }

            boolean flag = flag1;
            if (!flag) {
               String s = text.getString().toLowerCase();
               if (this.b(s)) {
                  if (!this.onlyFriendsSetting.getValue() || this.c(s)) {
                     this.jL = (int)this.delaySetting.getValue();
                     this.Im = true;
                  }
               }
            }
         }
      }
   }

   private boolean b(String s) {
      return (s.contains("teleport") || s.contains("tpa") || s.contains("tpask"))
         && (s.contains("request") || s.contains("has requested") || s.contains("wants SmallStateData"));
   }

   private boolean c(String s) {
      for (String s1 : FriendManager.getInstance().getFriends()) {
         if (s.contains(s1.toLowerCase())) {
            return true;
         }
      }

      return false;
   }
}
