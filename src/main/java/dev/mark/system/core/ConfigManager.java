package dev.mark.system.core;

import dev.mark.system.data.ConfigInfo;
import dev.mark.system.config.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import dev.mark.system.gui.component.CategoryButton;
import net.minecraft.client.MinecraftClient;

public class ConfigManager {
   private ModuleManager moduleManager;
   private CloudApiClient configClient;
   private Config currentConfig;
   private List<ConfigInfo> availableConfigs = new ArrayList<ConfigInfo>();
   private String activeConfigName;
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final String CONFIG_DIR = "system";
   private static final String CONFIG_FILE = "config.json";
   private static String[] jg7WxrrVqT2NbtdR = new String[3];

   public ConfigManager(ModuleManager modulemanager) {
      this.moduleManager = modulemanager;
      this.currentConfig = new Config();
      this.currentConfig.a();
      this.configClient = new CloudApiClient(AuthConfig.getHost(), AuthConfig.getPort());

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         try {
            this.saveConfig();
         } catch (Exception e) {
            System.err.println("Failed to save config on shutdown: " + e.getMessage());
         }
      }, "ConfigSaveThread"));
      
      this.loadConfig();
   }

   private File getConfigDir() {
      MinecraftClient mc = MinecraftClient.getInstance();
      File gameDir = mc.runDirectory;
      File configDir = new File(gameDir, CONFIG_DIR);
      if (!configDir.exists()) {
         configDir.mkdirs();
      }
      return configDir;
   }

   private File getConfigFile() {
      return new File(getConfigDir(), CONFIG_FILE);
   }

   public void loadConfig() {
      try {
         File configFile = getConfigFile();
         if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
               Config loadedConfig = GSON.fromJson(reader, Config.class);
               if (loadedConfig != null) {
                  this.currentConfig = loadedConfig;
                  this.applyConfig(this.currentConfig);
               }
            }
         }
      } catch (Exception e) {
         System.err.println("Failed to load config: " + e.getMessage());
         e.printStackTrace();
      }
   }

   public void saveConfig() {
      try {
         File configDir = getConfigDir();
         if (!configDir.exists()) {
            configDir.mkdirs();
         }
         
         File configFile = getConfigFile();
         this.collectConfig(this.currentConfig);
         
         try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(this.currentConfig, writer);
         }
      } catch (Exception e) {
         System.err.println("Failed to save config: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private void applyConfig(Config config) {
      if (this.moduleManager != null && config != null) {
         this.moduleManager.loadConfig(this);
      }
   }

   private void collectConfig(Config config) {
      if (this.moduleManager != null && config != null) {
         this.moduleManager.saveConfig(this);
      }
   }

   public Config a() {
      return this.currentConfig;
   }

   private void b(Config config, Config config1) {
   }

   public void c(Config config) {
      this.currentConfig = config;
      this.applyConfig(config);
   }

   private void d(Config config) {
   }

   private void e(Config config) {
   }

   private void f(Config config) {
   }

   private void g(Config config) {
   }

   public boolean h(String s, String s1) {
      return false;
   }

   public boolean i(String s, String s1) {
      return false;
   }

   public boolean j(String s) {
      return false;
   }

   public void k(String s) {
   }

   public boolean l() {
      return false;
   }

   public void m(String s) {
   }

   public String n(String s) {
      return null;
   }

   public String o(String s, String s1) {
      return null;
   }

   public void p() {
      this.saveConfig();
   }

   public boolean CategoryButton() {
      return false;
   }

   public boolean r() {
      return false;
   }

   public void s() {
   }

   public void t() {
   }

   private void u(Config config) {
   }

   private void v(Config config) {
   }

   public void w(Config config) {
   }

   public Config x() {
      return this.currentConfig;
   }

   public List<ConfigInfo> y() {
      return this.availableConfigs;
   }

   public String z() {
      return null;
   }

   public void A(String s) {
   }

   static {
      Loader.init(ConfigManager.class);
      C();
   }

   private static String B(char[] achar, long i, int j) {
      return null;
   }

   private static void C() {
   }

   public static void guard() {
   }

   public boolean q() {
      return true;
   }
}
