package dev.mark.system.hud;

public class StaffListEntry extends ListEntry<String> {
   public final String name;
   public final String displayName;
   public boolean online;

   public StaffListEntry(String s, String s1, boolean flag, String s2) {
      super(s, s2);
      this.name = s;
      this.displayName = s1;
      this.online = flag;
   }

   public void a(boolean flag) {
      this.online = flag;
   }

   @Override
   public void a() {
   }
}
