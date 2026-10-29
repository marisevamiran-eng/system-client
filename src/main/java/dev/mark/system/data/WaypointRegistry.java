package dev.mark.system.data;

import dev.mark.system.core.ClientMain;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WaypointRegistry {
   private static WaypointRegistry uo;
   private final List<WaypointData> bv = new CopyOnWriteArrayList<WaypointData>();

   private WaypointRegistry() {
   }

   public static WaypointRegistry a() {
      if (uo == null) {
         uo = new WaypointRegistry();
      }

      return uo;
   }

   public void b(WaypointData g) {
      if (g != null) {
         this.bv.add(g);
         this.i();
      }
   }

   public void c(String s) {
      this.bv.removeIf(g -> {
         return g.e().equalsIgnoreCase(s);
      });
      this.i();
   }

   public void d(WaypointData g) {
      this.bv.remove(g);
      this.i();
   }

   public WaypointData e(String s) {
      return this.bv.stream().filter(g -> {
         return g.e().equalsIgnoreCase(s);
      }).findFirst().orElse(null);
   }

   public List<WaypointData> f(String s) {
      return this.bv.stream().filter(g -> {
         return g.g().equals(s);
      }).toList();
   }

   public void g() {
      this.bv.clear();
      this.i();
   }

   public boolean h(String s) {
      return this.bv.stream().anyMatch(g -> {
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
      return this.bv;
   }
}
