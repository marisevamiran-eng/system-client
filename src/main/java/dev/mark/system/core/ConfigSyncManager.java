package dev.mark.system.core;

import dev.mark.system.data.ConfigInfo;
import com.google.gson.Gson;
import dev.mark.system.config.Config;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class ConfigSyncManager {
   private final Gson gson = new Gson();
   private final ScheduledExecutorService executor;
   private final CloudApiClient configClient;
   private final ClientMain main = ClientMain.getInstance();
   private ScheduledFuture<?> debouncedTask;
   private String lastSavedHash = "";
   private volatile boolean dirty = false;
   private volatile boolean locked = false;
   private long lastSaveTimestamp = System.currentTimeMillis();
   private static final long DEBOUNCE_SECONDS = 30L;
   private static final long PERIODIC_CHECK_MINUTES = 5L;
   private static final long MAX_DIRTY_MILLIS = 300000L;
   private static String[] UBvoyX7A4BwqQtqf = new String[2];

   public ConfigSyncManager(ScheduledExecutorService scheduledexecutorservice) {
      this.executor = scheduledexecutorservice;
      this.configClient = new CloudApiClient(AuthConfig.getHost(), AuthConfig.getPort());
      this.c();
   }

   public void a() {  }

   public void b() {  }

   private void c() {  }

   public void d(Config config) {
       this.dirty = true;

       if (this.main != null && this.main.getConfigManager() != null) {
         this.main.getConfigManager().saveConfig();
      }
   }

   private void e() {  }

   private void f() {  }

   public void g() {  }

   private String h(ConfigManager configmanager) { return null; }

   private boolean i() { return false; }

   private String j(Config config) { return null; }

   public boolean k() { return false; }

   private static boolean l(String s, ConfigInfo ConfigInfo) { return false; }

   private void m() {  }

   static {
      Loader.init(ConfigSyncManager.class);
      o();
   }

   private static String n(char[] achar, long i, int j) { return null; }

   private static void o() {  }

   public static void guard() {  }
}
