package dev.mark.system.core;

import com.google.gson.Gson;
import java.util.function.Function;

public abstract class ApiClient {
   protected String host;
   protected int port;
   protected Gson gson = new Gson();
   private static String[] _4dSoclJj4mePJqzv = new String[1];

   protected ApiClient(String s, int i) {
      this.host = s;
      this.port = i;
   }

   protected <T> T a(Object object, Function<String, T> function, T t) { return null; }

   protected <T> T b(Object object, Function<String, T> function, T t) { return null; }

   protected void c(Object object) {  }

   protected void d(Exception exception) {  }

   static {
      Loader.init(ApiClient.class);
      f();
   }

   private static String e(char[] achar, long i, int j) { return null; }

   private static void f() {  }

   public static void guard() {  }
}
