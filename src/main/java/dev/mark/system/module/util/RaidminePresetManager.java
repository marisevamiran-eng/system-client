package dev.mark.system.module.util;

import dev.mark.system.data.ItemCollection;
import dev.mark.system.data.ABItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dev.mark.system.module.combat.ABRaidmineModule;

public class RaidminePresetManager {
   private final ABRaidmineModule axi;
   private final Map<String, ItemCollection> arL;
   private String abT;

   public RaidminePresetManager(ABRaidmineModule abraidminemodule) {
      this.axi = abraidminemodule;
      this.arL = new HashMap<String, ItemCollection>();
      this.abT = null;
   }

   public void a(String s) {
      ItemCollection pmx = this.axi.q();
      if (pmx != null && pmx.c() != null) {
         ItemCollection pmxCopy = new ItemCollection();
         ArrayList arraylist = new ArrayList();

         for (ABItem abitem : pmx.c()) {
            arraylist.add(new ABItem(abitem.getName(), abitem.getMaxPrice(), abitem.isPartial()));
         }

         pmxCopy.b(arraylist);
         this.arL.put(s, pmxCopy);
      }
   }

   public void b(String s) {
      ItemCollection pmx = this.arL.get(s);
      if (pmx != null) {
         ItemCollection pmxCopy = new ItemCollection();
         ArrayList arraylist = new ArrayList();

         for (ABItem abitem : pmx.c()) {
            arraylist.add(new ABItem(abitem.getName(), abitem.getMaxPrice(), abitem.isPartial()));
         }

         pmxCopy.b(arraylist);
         this.axi.r(pmxCopy);
      }
   }

   public void c(String s) {
      this.arL.remove(s);
   }

   public boolean d(String s) {
      return this.arL.containsKey(s);
   }

   public ItemCollection e(String s) {
      return this.arL.get(s);
   }

   public List<String> f() {
      return new ArrayList<String>(this.arL.keySet());
   }

   public void g() {
      this.arL.clear();
      this.abT = null;
   }

   public Map<String, ItemCollection> h() {
      return new HashMap<String, ItemCollection>(this.arL);
   }

   public void i(Map<String, ItemCollection> map) {
      this.arL.clear();
      if (map != null) {
         this.arL.putAll(map);
      }
   }

   public String j() {
      return this.abT;
   }

   public void k(String s) {
      this.abT = s;
   }
}
