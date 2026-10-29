package dev.mark.system.enums;

public enum PacketDirection {
   SEND,
   RECEIVE;

   public static PacketDirection a(String s) {
      return Enum.<PacketDirection>valueOf(PacketDirection.class, s);
   }

   private static PacketDirection[] b() {
      return new PacketDirection[]{SEND, RECEIVE};
   }
}
