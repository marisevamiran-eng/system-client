package dev.mark.system.gui.enums;

public enum ThreeStateEnum {
   MT,
   IH,
   su;

   public static ThreeStateEnum a(String s) {
      return Enum.<ThreeStateEnum>valueOf(ThreeStateEnum.class, s);
   }

   private static ThreeStateEnum[] b() {
      return new ThreeStateEnum[]{MT, IH, su};
   }
}
