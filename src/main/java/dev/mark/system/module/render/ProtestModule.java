package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import dev.mark.system.setting.BooleanSetting;

public class ProtestModule extends Module {
   private BooleanSetting hideNameSetting = new BooleanSetting("Hide Name", "1", true);
   private BooleanSetting hideFriendsSetting = new BooleanSetting("Hide Friends", "2", true);
   private BooleanSetting hidePasswordSetting = new BooleanSetting("Hide Password", "3", true);
   private static String[] aos = new String[]{"/login ", "/l ", "/register ", "/reg ", "/auth ", "/changepassword ", "/changepass ", "/cp ", "/2fa "};

   public ProtestModule() {
      super("Protest", "Hides your name", Category.VISUAL);
      this.addSetting(this.hideNameSetting);
      this.addSetting(this.hideFriendsSetting);
      this.addSetting(this.hidePasswordSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public String a(String s) {
      if (!this.isEnabled()) {
         return s;
      } else if (this.hideNameSetting.getValue() && this.getClient().player != null && s.equals(this.getClient().player.getGameProfile().getName())) {
         return "SystemPlayer";
      } else {
         return this.hideFriendsSetting.getValue() && this.isFriend(s) ? "SystemFriend" : s;
      }
   }

   public boolean b() {
      return this.isEnabled() && this.hidePasswordSetting.getValue();
   }

   public String c(String s) {
      String s1 = s.toLowerCase();

      for (String s2 : aos) {
         if (s1.startsWith(s2)) {
            String s3 = s.substring(0, s2.length());
            String s4 = s.substring(s2.length());
            String s5 = s4.replaceAll("\\S", "*");
            return s3 + s5;
         }
      }

      return s;
   }

   public BooleanSetting d() {
      return this.hideNameSetting;
   }

   public BooleanSetting e() {
      return this.hideFriendsSetting;
   }

   public BooleanSetting f() {
      return this.hidePasswordSetting;
   }
}
