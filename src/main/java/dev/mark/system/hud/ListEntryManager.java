package dev.mark.system.hud;

import dev.mark.system.gui.animation.ProgressAnimation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;

import dev.mark.system.gui.animation.easing.EasingFunctions;
public class ListEntryManager<T extends ListEntry<?>> {
   private final LinkedHashMap<String, T> xL = new LinkedHashMap<String, T>();
   private final ProgressAnimation akG;
   private final float aoo;
   private final float VM;

   public ListEntryManager(float f, float f1) {
      this.aoo = f;
      this.VM = f1;
      this.akG = new ProgressAnimation();
      this.akG.a();
   }

   public void a(Set<String> set, Function<String, T> function) {
      for (String s : set) {
         if (!this.xL.containsKey(s)) {
            this.xL.put(s, (T)function.apply(s));
         }

         this.xL.get(s).d().e(true);
      }

      ArrayList arraylist = new ArrayList();

      for (Entry entry : this.xL.entrySet()) {
         if (!set.contains(entry.getKey())) {
            ((ListEntry)entry.getValue()).d().e(false);
            if (!((ListEntry)entry.getValue()).d().c()) {
               arraylist.add((String)entry.getKey());
            }
         }
      }

      arraylist.forEach(this.xL::remove);
      this.b();
      boolean flag = this.xL.values().stream().anyMatch(listentry -> {
         return listentry.d().f() || listentry.d().g() > 0.001F;
      });
      this.akG.f(flag);
   }

   private void b() {
      float f = 0.0F;

      for (ListEntry listentry : this.xL.values()) {
         float f1 = listentry.d().g();
         if (f1 > 0.001F) {
            listentry.d().i(f);
            f += (this.aoo + this.VM) * f1;
         }
      }
   }

   public void c() {
      this.akG.b();

      for (ListEntry listentry : this.xL.values()) {
         listentry.d().b();
         listentry.a();
      }
   }

   public float d(float f, float f1) {
      if (this.xL.isEmpty()) {
         return 0.0F;
      } else {
         float f2 = 0.0F;
         boolean flag = true;

         for (ListEntry listentry : this.xL.values()) {
            float f3 = listentry.d().g();
            if (f3 > 0.001F) {
               if (!flag) {
                  f2 += this.VM * f3;
               }

               f2 += this.aoo * f3;
               flag = false;
            }
         }

         if (f2 == 0.0F) {
            return 0.0F;
         } else {
            float f4 = this.akG.h();
            float f5 = f1 + f2;
            return f + (f5 - f) * EasingFunctions.a(f4);
         }
      }
   }

   public Collection<T> e() {
      return this.xL.values();
   }

   public boolean f() {
      return this.xL.isEmpty();
   }

   public ProgressAnimation g() {
      return this.akG;
   }
}
