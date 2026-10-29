package dev.mark.system.gui.layout;

import dev.mark.system.core.ClientMain;
import dev.mark.system.core.ModuleManager;
import dev.mark.system.core.SoundManager;
import dev.mark.system.enums.Category;
import dev.mark.system.enums.SoundType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import dev.mark.system.module.Module;
import dev.mark.system.gui.animation.PageTransitionAnimation;
import dev.mark.system.gui.component.CategoryButton;

public class ModuleCategoryManager {
   public static final String AC = "All";
   private final Map<String, List<Module>> QG = new HashMap<String, List<Module>>();
   private final PageTransitionAnimation amf = new PageTransitionAnimation();
   private final List<CategoryButton> qy = new ArrayList<CategoryButton>();
   private String ym = "All";
   private String Io = "All";
   private Consumer<String> bU;
   private Runnable Lk;

   public ModuleCategoryManager() {
      this.a();
      this.amf.a();
   }

   private void a() {
      ModuleManager modulemanager = ClientMain.getInstance().getModuleManager();

      for (Category category : Category.values()) {
         List list = modulemanager.getModulesByCategory(category);
         this.QG.put(category.getDisplayName(), list);
      }
   }

   public void b(CategoryButton categoryButton) {
      this.qy.add(categoryButton);
   }

   public void c(String s) {
      if (this.ym.equals(s)) {
         if (this.e(s)) {
            this.d();
         } else {
            if (this.Lk != null) {
               this.Lk.run();
            }
         }
      } else {
         if (!this.e(this.ym)) {
            this.Io = this.ym;
         }

         this.amf.a_2(() -> {
            this.ym = s;
            this.f();
            if (this.bU != null) {
               this.bU.accept(this.ym);
            }
         });
         SoundManager.getInstance().c(SoundType.CATEGORY_SWITCH);
      }
   }

   public void d() {
      if (!this.Io.isEmpty() && !this.Io.equals(this.ym)) {
         this.c(this.Io);
      }
   }

   private boolean e(String s) {
      return s.equals("Favourites") || s.equals("Friends") || s.equals("Configs");
   }

   private void f() {
      for (CategoryButton categoryButton : this.qy) {
         categoryButton.b(categoryButton.a().equals(this.ym));
      }
   }

   public List<Module> g(String s) {
      return "All".equals(s) ? this.h() : this.QG.getOrDefault(s, Collections.<Module>emptyList());
   }

   public List<Module> h() {
      ArrayList arraylist = new ArrayList();

      for (Category category : Category.values()) {
         arraylist.addAll(this.QG.getOrDefault(category.getDisplayName(), Collections.<Module>emptyList()));
      }

      return arraylist;
   }

   public void i(String s) {
      this.ym = s;
      this.f();
   }

   public PageTransitionAnimation j() {
      return this.amf;
   }

   public List<CategoryButton> k() {
      return this.qy;
   }

   public String l() {
      return this.ym;
   }

   public void m(Consumer<String> consumer) {
      this.bU = consumer;
   }

   public void n(Runnable runnable) {
      this.Lk = runnable;
   }
}
