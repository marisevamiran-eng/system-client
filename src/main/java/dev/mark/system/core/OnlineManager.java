package dev.mark.system.core;

import java.util.concurrent.ScheduledExecutorService;

public class OnlineManager {
   private final ClientMain main = ClientMain.getInstance();
   private final ScheduledExecutorService executor;
   private static String[] itt9256QV9aFdBuT = new String[1];

   public OnlineManager(ScheduledExecutorService scheduledexecutorservice) {
      this.executor = scheduledexecutorservice;
      Thread thread = Thread.ofVirtual().name("OnlineManagerThread").unstarted(this::c);
      Runtime.getRuntime().addShutdownHook(thread);
      this.a();
   }

   public void a() {
   }

   private void b() {
   }

   private void c() {
   }

   private void d() {
   }

   static {
      Loader.init(OnlineManager.class);
      f();
   }

   private static String e(char[] achar, long i, int j) {
      return null;
   }

   private static void f() {
   }

   public static void guard() {
   }
}
