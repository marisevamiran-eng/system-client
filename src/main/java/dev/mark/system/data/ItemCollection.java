package dev.mark.system.data;

import com.google.gson.annotations.SerializedName;
import dev.mark.system.data.ABItem;
import java.util.ArrayList;
import java.util.List;
import dev.mark.system.render.CornerBracketRenderer;

public class ItemCollection {
   @SerializedName("предметы")
   private List<ABItem> kW = new ArrayList<ABItem>();

   public ItemCollection() {
      this.kW = new ArrayList<ABItem>();
   }

   public ABItem a(String s) {
      if (s != null && this.kW != null) {
         for (ABItem abitem : this.kW) {
            if (abitem.getName() != null) {
               if (abitem.isPartial()) {
                  if (s.contains(abitem.getName())) {
                     return abitem;
                  }
               } else if (abitem.getName().equals(s)) {
                  return abitem;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public void b(List<ABItem> list) {
      this.kW = list;
   }

   public List<ABItem> c() {
      return this.kW;
   }
}
