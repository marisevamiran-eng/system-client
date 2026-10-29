package dev.mark.system.config;

import dev.mark.system.core.Loader;

import dev.mark.system.data.AuctionItemData;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ItemListConfig {
   @SerializedName("предметы")
   private List<AuctionItemData> items;

   public ItemListConfig() {
      this.items = new ArrayList<AuctionItemData>();
   }

   public ItemListConfig(List<AuctionItemData> list) {
      this.items = new ArrayList<AuctionItemData>(list);
   }

   public List<AuctionItemData> a() { return null; }

   public void b(List<AuctionItemData> list) {  }

   static {
      Loader.init(ItemListConfig.class);
   }

   public static void guard() {  }
}
