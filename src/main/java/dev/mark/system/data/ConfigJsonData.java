package dev.mark.system.data;

import dev.mark.system.core.Loader;

import com.google.gson.annotations.SerializedName;

public class ConfigJsonData {
   @SerializedName("name")
   private String name;
   @SerializedName("jsonData")
   private String jsonData;

   public byte[] a() { return null; }

   public String b() { return null; }

   public String c() { return null; }

   static {
      Loader.init(ConfigJsonData.class);
   }

   public static void guard() {  }
}
