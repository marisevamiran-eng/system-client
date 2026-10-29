package dev.mark.system.shader;

import dev.mark.system.core.Loader;

import com.google.gson.annotations.SerializedName;

public class ShaderData {
   @SerializedName("name")
   private String name;
   @SerializedName("jsonData")
   private String jsonData;
   @SerializedName("fshData")
   private String fshData;
   @SerializedName("vshData")
   private String vshData;

   public byte[] getJsonBytes() { return null; }

   public byte[] getFshBytes() { return null; }

   public byte[] getVshBytes() { return null; }

   public String getName() { return null; }

   public String e() { return null; }

   public String f() { return null; }

   public String g() { return null; }

   static {
      Loader.init(ShaderData.class);
   }

   public static void guard() {  }
}
