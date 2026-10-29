package dev.mark.system.gui.model;

import dev.mark.system.setting.Setting;
import dev.mark.system.gui.Component;
import dev.mark.system.gui.component.DropdownSelector;

public class SettingComponentEntry {
   private final String eO;
   private final Component anL;
   private final float rl;
   private final DropdownSelector WK;
   private final Setting ara;

   public SettingComponentEntry(String s, Component component, float f, Setting setting) {
      this(s, component, f, component instanceof DropdownSelector ? (DropdownSelector)component : null, setting);
   }

   public SettingComponentEntry(String s, Component component, float f, DropdownSelector DropdownSelector, Setting setting) {
      this.eO = s;
      this.anL = component;
      this.rl = f;
      this.WK = DropdownSelector;
      this.ara = setting;
   }

   public String a() {
      return this.eO;
   }

   public Component b() {
      return this.anL;
   }

   public float c() {
      return this.rl;
   }

   public DropdownSelector d() {
      return this.WK;
   }

   public Setting e() {
      return this.ara;
   }
}
