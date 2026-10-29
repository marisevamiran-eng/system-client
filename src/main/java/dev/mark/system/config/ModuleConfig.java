package dev.mark.system.config;

import dev.mark.system.core.Loader;
import dev.mark.system.setting.Setting;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleConfig {
   private boolean enabled;
   private Map<String, JsonObject> settings = new HashMap<String, JsonObject>();

   public boolean isEnabled() { 
      return this.enabled; 
   }

   public Map<String, JsonObject> getSettings() { 
      return this.settings; 
   }

   public void setEnabled(boolean flag) {
      this.enabled = flag;
   }

   public void setSettings(Map<String, JsonObject> map) {
      this.settings = map;
   }

   public void a(boolean flag) {
      this.enabled = flag;
   }

   public void b(List<Setting> settingsList) {
       this.settings = new HashMap<>();
   }

   static {
      Loader.init(ModuleConfig.class);
   }

   public static void guard() {  }
}
