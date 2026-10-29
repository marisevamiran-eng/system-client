package dev.mark.system.gui;

public enum InputFieldType {
   tg("E", "Введите никнейм..."),
   ahz("D", "Введите ключ конфигурации...");

   private final String eg;
   private final String aeV;

   public static InputFieldType a(String s) {
      return Enum.<InputFieldType>valueOf(InputFieldType.class, s);
   }

   private InputFieldType(String s1, String s2) {
      this.eg = s1;
      this.aeV = s2;
   }

   public String b() {
      return this.eg;
   }

   public String c() {
      return this.aeV;
   }

   private static InputFieldType[] d() {
      return new InputFieldType[]{tg, ahz};
   }
}
