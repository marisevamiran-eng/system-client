package dev.mark.system.gui.component;

import dev.mark.system.hud.ListEntry;
import dev.mark.system.setting.KeyBindSetting;

public class KeyBindListEntry extends ListEntry<KeyBindSetting> {
   public final String auh;
   public final KeyBindSetting Wf;
   public final String adf;

   public KeyBindListEntry(String s, KeyBindSetting keybindsetting, String s1) {
      super(keybindsetting, s1);
      this.auh = s;
      this.Wf = keybindsetting;
      this.adf = null;
   }

   public KeyBindListEntry(String s, KeyBindSetting keybindsetting, String s1, String s2) {
      super(keybindsetting, s2);
      this.auh = s;
      this.Wf = keybindsetting;
      this.adf = s1;
   }

   @Override
   public void a() {
   }
}
