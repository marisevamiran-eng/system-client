package dev.mark.system.data;

import dev.mark.system.core.ClientMain;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WaypointManager {
   private static WaypointManager instance;
   private final List<WaypointData> waypoints = new CopyOnWriteArrayList<WaypointData>();

   private WaypointManager() {
   }

   public static WaypointManager a() {
      if (instance == null) {
         instance = new WaypointManager();
      }

      return instance;
   }

   public void b(WaypointData g) {
      if (g != null) {
         this.waypoints.add(g);
         this.i();
      }
   }

   public void c(String s) {
      this.waypoints.removeIf(g -> {
         return g.e().equalsIgnoreCase(s);
      });
      this.i();
   }

   public void d(WaypointData g) {
      this.waypoints.remove(g);
      this.i();
   }

   public WaypointData e(String s) {
      return this.waypoints.stream().filter(g -> {
         return g.e().equalsIgnoreCase(s);
      }).findFirst().orElse(null);
   }

   public List<WaypointData> f(String s) {
      return this.waypoints.stream().filter(g -> {
         return g.g().equals(s);
      }).toList();
   }

   public void g() {
      this.waypoints.clear();
      this.i();
   }

   public boolean h(String s) {
      return this.waypoints.stream().anyMatch(g -> {
         return g.e().equalsIgnoreCase(s);
      });
   }

   private void i() {
      try {
         ClientMain clientmain = ClientMain.getInstance();
         if (clientmain != null && clientmain.getConfigSyncManager() != null && clientmain.getConfigManager() != null) {
            if (ClientMain.getInstance().getConfigSyncManager().k()) {
               return;
            }

            clientmain.getConfigSyncManager().d(clientmain.getConfigManager().x());
         }
      } catch (Exception exception) {
      }
   }

   public List<WaypointData> j() {
      return this.waypoints;
   }
}
