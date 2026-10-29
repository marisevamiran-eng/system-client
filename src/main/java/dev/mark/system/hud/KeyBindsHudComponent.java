package dev.mark.system.hud;

import dev.mark.system.core.ClientMain;
import dev.mark.system.core.ModuleManager;
import dev.mark.system.enums.Category;
import java.util.HashSet;
import java.util.List;
import dev.mark.system.module.client.HudModule;
import dev.mark.system.module.Module;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltText;
import dev.mark.system.setting.GroupSetting;
import dev.mark.system.setting.KeyBindSetting;
import dev.mark.system.setting.Setting;
import dev.mark.system.gui.component.KeyBindListEntry;

public class KeyBindsHudComponent extends ListHudComponent<KeyBindListEntry> {
   private static final float uA = 20.0F;
   private static final float aqK = 20.0F;
   private static final float Id = 4.0F;
   private static final float KB = 13.0F;
   private static final float GJ = 31.0F;

   public KeyBindsHudComponent(float f, float f1) {
      super(f, f1, "KeyBinds", "keybinds", 20.0F, 31.0F);
   }

   @Override
   public boolean isAlwaysVisible() {
      return true;
   }

   @Override
   protected void b() {
      HashSet hashset = new HashSet();
      hashset.add("Aura_R");
      this.entryManager.a(hashset, s -> {
         String[] astring = s.split("_");
         String s1 = astring[0];
         String s2 = astring[1];
         return new KeyBindListEntry(s1, null, s2, s);
      });
   }

   @Override
   protected void a() {
      ModuleManager modulemanager = ClientMain.getInstance().getModuleManager();
      HashSet hashset = new HashSet();
      HudModule hudmodule = ClientMain.getInstance().getModuleManager().<HudModule>getModule(HudModule.class);
      boolean flag = hudmodule == null || hudmodule.i().getValue();

      for (Module module : modulemanager.getEnabledModules()) {
         if (flag || !this.a(module)) {
            KeyBindSetting keybindsetting = this.b(module);
            if (keybindsetting != null && keybindsetting.getKeyCode() != -1) {
               String s2 = module.getName();
               int i = keybindsetting.getKeyCode();
               String s1 = s2;
               String s = s1 + "_" + i;
               hashset.add(s);
            }
         }
      }

      this.entryManager.a(hashset, s3 -> {
         String[] astring = s3.split("_");
         String s4 = astring[0];
         Module module1 = modulemanager.getEnabledModules().stream().filter(module2 -> {
            return module2.getName().equals(s4);
         }).findFirst().orElse(null);
         if (module1 != null) {
            KeyBindSetting keybindsetting1 = this.b(module1);
            if (keybindsetting1 != null) {
               return new KeyBindListEntry(s4, keybindsetting1, s3);
            }
         }

         return null;
      });
   }

   private boolean a(Module module) {
      Category category = module.getCategory();
      return category == Category.RENDER || category == Category.VISUAL || category.isSubCategory() && category.getParent() == Category.VISUAL;
   }

   private KeyBindSetting b(Module module) {
      return this.c(module.getVisibleSettings());
   }

   private KeyBindSetting c(List<Setting> list) {
      for (Setting setting : list) {
         if (setting instanceof KeyBindSetting) {
            return (KeyBindSetting)setting;
         }

         if (setting instanceof GroupSetting groupsetting) {
            KeyBindSetting keybindsetting = this.c(groupsetting.getSettings());
            if (keybindsetting != null) {
               return keybindsetting;
            }
         }
      }

      return null;
   }

   protected void d(Matrix4f matrix4f, float f, float f1, KeyBindListEntry KeyBindListEntry, float f2, float f3) {
      if (!(f3 < 0.3F)) {
         BuiltText builttext = this.w(this.Eb, KeyBindListEntry.auh, 13.0F, EaseLinear);
         builttext.a(matrix4f, f + 10.0F, f1 + (f2 - 13.0F) * 0.5F, f3);
         String s = KeyBindListEntry.Wf != null ? KeyBindListEntry.Wf.getKeyName() : KeyBindListEntry.adf;
         float f4 = this.ew.c(s, 13.0F);
         float f5 = Math.max(20.0F, f4 + 8.0F);
         float f6 = f + this.width - 10.0F - f5;
         BuiltText builttext1 = this.w(this.ew, s, 13.0F, Bz);
         float f7 = f6 + (f5 - f4) * 0.5F;
         float f8 = f1 + (f2 - 13.0F) * 0.5F;
         builttext1.a(matrix4f, f7, f8 - 1.0F, f3);
      }
   }

}
