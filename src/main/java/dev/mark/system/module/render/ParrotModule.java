package dev.mark.system.module.render;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.util.Arrays;
import java.util.Collections;
import dev.mark.system.setting.ListSetting;

public class ParrotModule extends Module {
   private ListSetting sideSetting = new ListSetting(
      "Side", "Choose shoulder for the parrot", Arrays.<String>asList("Right", "Left"), Collections.<String>singletonList("Right"), false
   );
   private ListSetting typeSetting = new ListSetting(
      "Parrot Color",
      "Parrot color",
      Arrays.<String>asList("Blue", "Green", "Red-Blue", "Yellow-Blue"),
      Collections.<String>singletonList("Yellow-Blue"),
      false
   );

   public ParrotModule() {
      super("Parrot", "Renders a parrot on your shoulder", Category.VISUAL);
      this.addSettings(this.sideSetting, this.typeSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public ListSetting a() {
      return this.sideSetting;
   }

   public ListSetting b() {
      return this.typeSetting;
   }
}
