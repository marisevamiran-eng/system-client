package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.setting.BooleanSetting;

public class ChatTweaksModule extends Module {
   private BooleanSetting infiniteChatSetting = new BooleanSetting("Infinite Chat", "Increases chat history limit", true);
   private BooleanSetting noClearChatSetting = new BooleanSetting("No Clear Chat", "Prevents automatic chat clearing", true);
   private BooleanSetting copyRightClickSetting = new BooleanSetting("Copy RMB", "Allows copying messages with right click", true);
   private BooleanSetting groupDuplicatesSetting = new BooleanSetting("Group Duplicates", "Combines identical messages into one", true);

   public ChatTweaksModule() {
      super("ChatTweaks", "Chat utility", Category.VISUAL);
      this.addSettings(this.noClearChatSetting, this.infiniteChatSetting, this.copyRightClickSetting, this.groupDuplicatesSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public BooleanSetting a() {
      return this.infiniteChatSetting;
   }

   public BooleanSetting b() {
      return this.noClearChatSetting;
   }

   public BooleanSetting c() {
      return this.copyRightClickSetting;
   }

   public BooleanSetting d() {
      return this.groupDuplicatesSetting;
   }
}
