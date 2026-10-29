package dev.mark.system.gui.enums;

public enum IndexedTriState {
   azP(0),
   TR(1),
   arm(2);

   private final int akA;

   public static IndexedTriState a(String s) {
      return Enum.<IndexedTriState>valueOf(IndexedTriState.class, s);
   }

   private IndexedTriState(int j) {
      this.akA = j;
   }

   public int b() {
      return this.akA;
   }

   private static IndexedTriState[] c() {
      return new IndexedTriState[]{azP, TR, arm};
   }
}
