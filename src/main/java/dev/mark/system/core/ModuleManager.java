package dev.mark.system.core;

import dev.mark.system.data.ConfigInfo;
import dev.mark.system.config.Config;
import dev.mark.system.enums.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import dev.mark.system.module.Module;

public class ModuleManager {
   private final Map<String, Module> modules = new ConcurrentHashMap<String, Module>();
   private final Map<Category, List<Module>> modulesByCategory = new ConcurrentHashMap<Category, List<Module>>();
   private Set<Integer> allowedModuleIds = new HashSet<Integer>();

   public void init() {
      Set set = LicenseManager.a();
      if (set != null && !set.isEmpty()) {
         this.setAllowedModuleIds(set);
      }

      List<Module> list;
      if (this.allowedModuleIds.isEmpty()) {
         list = ModuleRegistry.createAllModules();
      } else {
         list = ModuleRegistry.createModules(this.allowedModuleIds);
      }

      for (Module module : list) {
         this.registerModule(module);
      }

      this.loadConfig(ClientMain.getInstance().getConfigManager());
      ClientMain.getInstance().modulesLoaded = true;
   }

   private void registerModule(Module module) {
      if (module != null) {
         String s = module.getName();
         if (!this.modules.containsKey(s)) {
            this.modules.put(s, module);
            this.modulesByCategory.computeIfAbsent(module.getCategory(), category -> {
               return new ArrayList<Module>();
            }).add(module);
         }
      }
   }

   public void unregisterModule(String s) {
      Module module = this.modules.remove(s);
      if (module != null) {
         this.modulesByCategory.computeIfPresent(module.getCategory(), (category, list) -> {
            synchronized (list) {
               list.remove(module);
               return list.isEmpty() ? null : list;
            }
         });
      }
   }

   public Module getModule(String s) {
      return this.modules.get(s);
   }

   public <T extends Module> T getModule(Class<T> oclass) {
      return this.modules.values().stream().filter(oclass::isInstance).<T>map(oclass::cast).findFirst().orElse(null);
   }

   public List<Module> getAllModules() {
      return new ArrayList<Module>(this.modules.values());
   }

   public List<Module> getEnabledModules() {
      return this.modules.values().stream().filter(Module::isEnabled).collect(Collectors.<Module>toList());
   }

   public List<Module> getModulesByCategory(Category category) {
      List list = this.modulesByCategory.get(category);
      if (list == null) {
         return new ArrayList<Module>();
      } else {
         synchronized (list) {
            return new ArrayList<Module>(list);
         }
      }
   }

   public Map<Category, List<Module>> getCategoryMap() {
      HashMap hashmap = new HashMap();
      this.modulesByCategory.forEach((category, list) -> {
         synchronized (list) {
            hashmap.put(category, new ArrayList<Module>(list));
         }
      });
      return hashmap;
   }

   public int getModuleCount() {
      return this.modules.size();
   }

   public int getEnabledCount() {
      return (int)this.modules.values().stream().filter(Module::isEnabled).count();
   }

   public boolean hasModule(String s) {
      return this.modules.containsKey(s);
   }

   public void enableAll() {
      this.modules.values().forEach(module -> {
         module.setEnabled(true);
      });
   }

   public void disableAll() {
      this.modules.values().forEach(module -> {
         module.setEnabled(false);
      });
   }

   public void clearModules() {
      this.modules.values().forEach(module -> {
         if (module.isEnabled()) {
            module.setEnabled(false);
         }
      });
      this.modules.clear();
      this.modulesByCategory.clear();
   }

   public void toggleAll() {
      this.modules.values().forEach(Module::toggle);
   }

   public List<String> getModuleNames() {
      return new ArrayList<String>(this.modules.keySet());
   }

   public void reset() {
      this.modules.values().forEach(module -> {
         if (module.isEnabled()) {
            module.setEnabled(false);
         }
      });
      this.modules.clear();
      this.modulesByCategory.clear();
   }

   public void setAllowedModuleIds(Set<Integer> set) {
      this.allowedModuleIds = set;
   }

   public void loadConfig(ConfigManager configmanager) {
      try {
         boolean flag = configmanager.r();
         if (!flag) {
            CloudApiClient CloudApiClient = new CloudApiClient(AuthConfig.getHost(), AuthConfig.getPort());
            Config config = CloudApiClient.d((String)null);
            if (config != null) {
               configmanager.w(config);
               configmanager.c(config);
            } else {
               configmanager.w(new Config());
            }
         }

         configmanager.p();
         if (configmanager.z() == null || configmanager.z().isEmpty()) {
            for (ConfigInfo ConfigInfo : configmanager.y()) {
               if (ConfigInfo.d()) {
                  configmanager.A(ConfigInfo.a());
                  break;
               }
            }
         }

         if ((configmanager.z() == null || configmanager.z().isEmpty()) && !configmanager.y().isEmpty()) {
            String s = configmanager.y().get(0).a();
            configmanager.A(s);
            configmanager.m(s);
         }
      } catch (Exception exception) {
      }
   }

   public void saveConfig(ConfigManager configmanager) {
      try {
         Config config = configmanager.x();
         if (config != null) {
             Map<String, dev.mark.system.config.ModuleConfig> moduleConfigs = new HashMap<>();
            for (Module module : this.modules.values()) {
               dev.mark.system.config.ModuleConfig moduleConfig = new dev.mark.system.config.ModuleConfig();
               moduleConfig.a(module.isEnabled());
               moduleConfig.b(module.getSettings());
               moduleConfigs.put(module.getName(), moduleConfig);
            }
            config.o(moduleConfigs);
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public Map<Category, List<Module>> getCategoryMapDirect() {
      return this.modulesByCategory;
   }

   public Set<Integer> getAllowedModuleIds() {
      return this.allowedModuleIds;
   }
}
