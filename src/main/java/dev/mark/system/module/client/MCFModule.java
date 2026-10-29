package dev.mark.system.module.client;

import dev.mark.system.module.Module;

import dev.mark.system.command.FriendCommand;
import dev.mark.system.core.FriendManager;
import dev.mark.system.enums.Category;
import dev.mark.system.event.ChatMessageEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import dev.mark.system.setting.ActionKeySetting;

public class MCFModule extends Module {
   private final ActionKeySetting buttonSetting = new ActionKeySetting("Key", "Key для добавления/удаления из друзей", 2, this::a);
   private final FriendCommand vp = new FriendCommand();

   public MCFModule() {
      super("МСФ", "Добавление в друзья по нажатию кнопки. Команды: .friend", Category.CLIENT);
      this.addSettings(this.buttonSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onChatMessage(ChatMessageEvent chatmessageevent) {
      this.vp.handleMessage(chatmessageevent);
   }

   private void a() {
      if (!this.isNotInWorld()) {
         Entity entity = this.getClient().targetedEntity;
         if (entity != null) {
            if (entity instanceof PlayerEntity playerentity) {
               if (playerentity != this.getPlayer()) {
                  String s = playerentity.getName().getString();
                  FriendManager friendmanager = FriendManager.getInstance();
                  friendmanager.toggleFriend(s);
               }
            }
         }
      }
   }
}
