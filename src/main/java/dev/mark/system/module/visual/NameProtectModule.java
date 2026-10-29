package dev.mark.system.module.visual;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.TextSetting;
import dev.mark.system.core.FriendManager;

public class NameProtectModule extends Module {
    private BooleanSetting hideFriends;
    private BooleanSetting hidePassword;
    private static final String[] COMMAND_PREFIXES;
    private BooleanSetting hideSelf;
    private TextSetting customName;

    static { COMMAND_PREFIXES = new String[]{"/login ", "/l ", "/register ", "/reg ", "/auth ", "/changepassword ", "/changepass ", "/cp ", "/2fa "}; }

    public NameProtectModule() {
        super("Protect", "Hides your name", Category.VISUAL);
        this.hideSelf = new BooleanSetting("Hide Name", "Hide own nickname", true);
        this.customName = new TextSetting("Custom Name", "Hidden nickname", "t.me/soezproject", "Enter nickname...");
        this.hideFriends = new BooleanSetting("Hide Friends", "Hide friend names", true);
        this.hidePassword = new BooleanSetting("Hide Password", "Hide password in chat", true);
        addSettings(this.hideSelf, this.customName, this.hideFriends, this.hidePassword);
    }

    public TextSetting getCustomName() { return this.customName; }
    public BooleanSetting getHideSelf() { return this.hideSelf; }
    public BooleanSetting getHideFriends() { return this.hideFriends; }
    public BooleanSetting getHidePassword() { return this.hidePassword; }
    public boolean isPasswordHidingEnabled() { return isEnabled() && this.hidePassword.getValue(); }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    public String replaceName(String name) {
        if (!isEnabled()) return name;
        if (this.hideSelf.getValue() && getClient().player != null && name.equals(getClient().player.getName().getString())) {
            String custom = this.customName.getValue();
            return (custom == null || custom.isEmpty()) ? "t.me/soezproject" : custom;
        }
        if (!this.hideFriends.getValue() || !isFriend(name)) return name;
        return "t.me/soezproject";
    }

    public String hidePassword(String msg) {
        String lower = msg.toLowerCase();
        for (String prefix : COMMAND_PREFIXES) if (lower.startsWith(prefix)) return msg.substring(0, prefix.length()) + msg.substring(prefix.length()).replaceAll("\\S", "*");
        return msg;
    }
}
