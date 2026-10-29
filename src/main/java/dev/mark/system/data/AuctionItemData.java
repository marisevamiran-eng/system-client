package dev.mark.system.data;

import dev.mark.system.core.Loader;

import com.google.gson.annotations.SerializedName;

public class AuctionItemData {
   @SerializedName("название")
   private String name;
   @SerializedName("максимальная_цена")
   private double maxPricePerItem;
   @SerializedName("частичное_совпадение")
   private boolean partialMatch;

   public AuctionItemData() {
      this.partialMatch = false;
   }

   public AuctionItemData(String s, double d0, boolean flag) {
      this.name = s;
      this.maxPricePerItem = d0;
      this.partialMatch = flag;
   }

   public String a() { return null; }

   public double b() { return 0; }

   public boolean c() { return false; }

   public void d(String s) {  }

   public void e(double d0) {  }

   public void f(boolean flag) {  }

   static {
      Loader.init(AuctionItemData.class);
   }

   public static void guard() {  }
}
