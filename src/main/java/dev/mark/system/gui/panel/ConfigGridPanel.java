package dev.mark.system.gui.panel;

import dev.mark.system.data.ConfigInfo;
import dev.mark.system.gui.component.CardBase;
import dev.mark.system.core.ClientMain;
import dev.mark.system.core.ConfigManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import org.joml.Matrix4f;

import dev.mark.system.config.Config;
import dev.mark.system.gui.component.ConfigEntryComponent;
import dev.mark.system.gui.component.ResetButton;
import dev.mark.system.render.ScissorStack;
import dev.mark.system.gui.component.ScrollableListComponent;
import dev.mark.system.gui.layout.GridLayoutUtil;
public class ConfigGridPanel extends ScrollableListComponent<CardBase> {
   private final ConfigManager LP;
   private boolean auM = true;
   private boolean amS = false;
   private ResetButton b;
   private final Map<CardBase, Float> ahV = new HashMap<CardBase, Float>();
   private final Map<CardBase, Float> ON = new HashMap<CardBase, Float>();
   private final Map<CardBase, Float> Wy = new HashMap<CardBase, Float>();
   private final Map<CardBase, Float> aag = new HashMap<CardBase, Float>();
   private static final long ya = 1000000000L;
   private long wD = System.nanoTime();
   private static final float RI = 6.0F;

   public ConfigGridPanel(float f, float f1, float f2, float f3) {
      super(f, f1, f2, f3);
      this.LP = ClientMain.getInstance().getConfigManager();
      this.pZ = 286.0F;
      this.Ip = 130.0F;
      this.fh = 3;
      this.apX = 10.0F;
      this.HC = 11.0F;
      this.a();
   }

   public void a() {
      this.b(this.auM);
   }

   private void b(boolean flag) {
      Map map = this.d();
      if (!this.amS) {
         this.amS = true;
         if (this.LP.q()) {
            this.c(true, map);
         }

         new Thread(() -> {
            try {
               this.LP.p();
               MinecraftClient.getInstance().execute(() -> {
                  Map map1 = this.d();
                  this.c(flag, map1);
               });
            } catch (Exception exception) {
            }
         }, "L").start();
      } else {
         this.c(flag, map);
      }
   }

   private void c(boolean flag, Map<String, Integer> map) {
      List list = this.LP.y();
      List list1 = this.e(list, map);
      this.zy.removeIf(cardBase -> {
         if (cardBase instanceof ConfigEntryComponent sdcxxxx) {
            boolean flag1 = sdcxxxx.b();
            if (flag1) {
               this.ahV.remove(cardBase);
               this.ON.remove(cardBase);
               this.Wy.remove(cardBase);
               this.aag.remove(cardBase);
            }

            return flag1;
         } else {
            return false;
         }
      });
      HashMap hashmap = new HashMap();
      HashMap hashmap1 = new HashMap();
      ArrayList arraylist = new ArrayList();

      for (CardBase CardBase : this.zy) {
         if (CardBase instanceof ConfigEntryComponent sdcxxx) {
            if (sdcxxx.A()) {
               arraylist.add(sdcxxx);
               hashmap1.put(sdcxxx.u(), sdcxxx);
            } else {
               hashmap.put(sdcxxx.u(), sdcxxx);
            }
         }
      }

      ArrayList arraylist1 = new ArrayList();
      int i = 0;

      for (Object _ci : list1) {
         ConfigInfo ConfigInfo = (ConfigInfo)_ci;
          if (!hashmap1.containsKey(ConfigInfo.a())) {
            float[] afloat = GridLayoutUtil.a(i++, this.fh, this.pZ, this.Ip, this.apX, this.HC);
            ConfigEntryComponent sdcx = (ConfigEntryComponent)hashmap.get(ConfigInfo.a());
            if (sdcx != null) {
               arraylist1.add(sdcx);
               this.ahV.put(sdcx, afloat[0]);
               this.ON.put(sdcx, afloat[1]);
               if (!this.Wy.containsKey(sdcx)) {
                  this.Wy.put(sdcx, sdcx.getX());
                  this.aag.put(sdcx, sdcx.getY());
               }
            } else {
               ConfigEntryComponent sdcxx = new ConfigEntryComponent(afloat[0], afloat[1], ConfigInfo, flag);
               sdcxx.w(this::k);
               arraylist1.add(sdcxx);
               this.ahV.put(sdcxx, afloat[0]);
               this.ON.put(sdcxx, afloat[1]);
               this.Wy.put(sdcxx, afloat[0]);
               this.aag.put(sdcxx, afloat[1]);
            }
         }
      }

      for (Object _sdcx : arraylist) {
         ConfigEntryComponent sdcx = (ConfigEntryComponent)_sdcx;
          arraylist1.add(sdcx);
         if (!this.ahV.containsKey(sdcx)) {
            this.ahV.put(sdcx, sdcx.getX());
            this.ON.put(sdcx, sdcx.getY());
         }

         if (!this.Wy.containsKey(sdcx)) {
            this.Wy.put(sdcx, sdcx.getX());
            this.aag.put(sdcx, sdcx.getY());
         }
      }

      float[] afloat1 = GridLayoutUtil.a(i, this.fh, this.pZ, this.Ip, this.apX, this.HC);
      if (this.b == null || !this.zy.contains(this.b)) {
         this.b = new ResetButton(afloat1[0], afloat1[1], this::f, flag);
         this.Wy.put(this.b, afloat1[0]);
         this.aag.put(this.b, afloat1[1]);
      }

      arraylist1.add(this.b);
      this.ahV.put(this.b, afloat1[0]);
      this.ON.put(this.b, afloat1[1]);
      if (!this.Wy.containsKey(this.b)) {
         this.Wy.put(this.b, this.b.getX());
         this.aag.put(this.b, this.b.getY());
      }

      this.zy.clear();
      this.zy.addAll(arraylist1);
      this.auM = false;
      this.g();
   }

   private Map<String, Integer> d() {
      HashMap hashmap = new HashMap();
      int i = 0;

      for (CardBase CardBase : this.zy) {
         if (CardBase instanceof ConfigEntryComponent ConfigEntryComponent) {
            hashmap.put(ConfigEntryComponent.u(), i++);
         }
      }

      return hashmap;
   }

   private List<ConfigInfo> e(List<ConfigInfo> list, Map<String, Integer> map) {
      ArrayList arraylist = new ArrayList(list);
      arraylist.sort((Object _ci1, Object _ci2) -> {
         ConfigInfo configInfo1 = (ConfigInfo)_ci1;
         ConfigInfo configInfo2 = (ConfigInfo)_ci2;
         Integer integer = (Integer)map.get(configInfo1.a());
         Integer integer1 = (Integer)map.get(configInfo2.a());
         if (integer != null && integer1 != null) {
            return Integer.compare(integer, integer1);
         } else if (integer != null) {
            return -1;
         } else {
            return integer1 != null ? 1 : configInfo1.a().compareTo(configInfo2.a());
         }
      });
      return arraylist;
   }

   private void f() {
      int i = this.g_count();
      int j = i + 1;
      String s = "Config " + j;
      String s1 = "Новая конфигурация";
      ConfigInfo configInfoNew = new ConfigInfo();
      configInfoNew.g(s);
      configInfoNew.h(s1);
      configInfoNew.j(false);
      ConfigEntryComponent configEntryNew = this.h(configInfoNew);
      configEntryNew.C(true);
      new Thread(() -> {
         try {
            if (i > 0) {
               this.LP.t();
            }

            boolean flag = this.LP.h(s, s1);
            if (flag) {
               this.LP.j(s);
               this.LP.m(s);
               MinecraftClient.getInstance().execute(() -> {
                  configEntryNew.C(false);
               });
            }
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }, "C").start();
   }

   public int g_count() {
      int i = 0;

      for (CardBase CardBase : this.zy) {
         if (CardBase instanceof ConfigEntryComponent ConfigEntryComponent && !ConfigEntryComponent.A()) {
            i++;
         }
      }

      return i;
   }

   private ConfigEntryComponent h(ConfigInfo ConfigInfo) {
      int i = this.g_count();
      float[] afloat = GridLayoutUtil.a(i, this.fh, this.pZ, this.Ip, this.apX, this.HC);
      ConfigEntryComponent ConfigEntryComponent = new ConfigEntryComponent(afloat[0], afloat[1], ConfigInfo, false);
      ConfigEntryComponent.w(this::k);
      int j = this.zy.size() - 1;
      if (j < 0) {
         j = 0;
      }

      this.zy.add(j, ConfigEntryComponent);
      this.ahV.put(ConfigEntryComponent, afloat[0]);
      this.ON.put(ConfigEntryComponent, afloat[1]);
      this.Wy.put(ConfigEntryComponent, afloat[0]);
      this.aag.put(ConfigEntryComponent, afloat[1]);
      float[] afloat1 = GridLayoutUtil.a(i + 1, this.fh, this.pZ, this.Ip, this.apX, this.HC);
      this.ahV.put(this.b, afloat1[0]);
      this.ON.put(this.b, afloat1[1]);
      this.g();
      return ConfigEntryComponent;
   }

   @Override
   protected void b(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3) {
      this.j(f2);
      if (this.NQ != null) {
         this.NQ.onAfterCardsRender(matrix4f, f, f1, i, j, f2, f3);
      }

      ScissorStack.a(0.0F, 0.0F, this.qd, this.aem, this.KL, this.aiE + f * this.KL, this.RT + f1 * this.KL);

      for (CardBase CardBase : new ArrayList<CardBase>(this.zy)) {
         Float f4 = this.Wy.get(CardBase);
         Float f5 = this.aag.get(CardBase);
         float f6 = f4 != null ? f4 : CardBase.getX();
         float f7 = (f5 != null ? f5 : CardBase.getY()) - this.UA;
         if (GridLayoutUtil.c(f1 + f7, this.Ip, f1, this.aem)) {
            CardBase.it = f + f6;
            CardBase.atW = f1 + f7;
            float f8 = f3;
            if (CardBase instanceof ConfigEntryComponent ConfigEntryComponent && ConfigEntryComponent.A()) {
               f8 = f3 * ConfigEntryComponent.x();
            }

            if (this.fk != null) {
               this.fk.render(CardBase, matrix4f, f + f6, f1 + f7, i, j, f2, f8);
            } else {
               CardBase.render(matrix4f, f + f6, f1 + f7, i, j, f2, f8);
            }
         }
      }

      ScissorStack.b();
      if (this.rZ != null) {
         this.rZ.onAfterCardsRender(matrix4f, f, f1, i, j, f2, f3);
      }
   }

   private void j(float f7) {
      long i = System.nanoTime();
      float f = (float)(i - this.wD) / 1.0E9F;
      this.wD = i;
      f = Math.min(f, 0.05F);

      for (CardBase CardBase : new ArrayList<CardBase>(this.zy)) {
         Float f1 = this.ahV.get(CardBase);
         Float f2 = this.ON.get(CardBase);
         if (f1 != null && f2 != null) {
            float f3 = this.Wy.getOrDefault(CardBase, CardBase.getX());
            float f4 = this.aag.getOrDefault(CardBase, CardBase.getY());
            float f5 = f1 - f3;
            float f6 = f2 - f4;
            if (!(Math.abs(f5) > 0.5F) && !(Math.abs(f6) > 0.5F)) {
               CardBase.aP = f1;
               CardBase.hn = f2;
               this.Wy.put(CardBase, f1);
               this.aag.put(CardBase, f2);
            } else {
               f3 += f5 * f * 6.0F;
               f4 += f6 * f * 6.0F;
               this.Wy.put(CardBase, f3);
               this.aag.put(CardBase, f4);
            }
         }
      }
   }

   public void k() {
      this.b(true);
   }

   @Override
   public boolean f(int i, int j, int k) {
      if (i == 259) {
         for (CardBase ctx : this.zy) {
            if (ctx instanceof ConfigEntryComponent sdcx && sdcx.n()) {
               return true;
            }
         }
      }

      for (CardBase ctx : this.zy) {
         if (ctx instanceof ConfigEntryComponent ConfigEntryComponent && ConfigEntryComponent.f(i, j, k)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean h(char c0, int i) {
      for (CardBase CardBase : this.zy) {
         if (CardBase instanceof ConfigEntryComponent ConfigEntryComponent && ConfigEntryComponent.h(c0, i)) {
            return true;
         }
      }

      return false;
   }

   public boolean l() {
      return this.zy.size() <= 1;
   }

   public void m(String s) {
      if (s != null && !s.trim().isEmpty()) {
         String s3 = s.toLowerCase();

         for (CardBase CardBase : this.zy) {
            if (CardBase instanceof ConfigEntryComponent ConfigEntryComponent) {
               String s1 = ConfigEntryComponent.u().toLowerCase();
               String s2 = ConfigEntryComponent.v().toLowerCase();
               boolean flag = s1.contains(s3) || s2.contains(s3);
               CardBase.setVisible(flag);
            } else if (CardBase instanceof ResetButton) {
               CardBase.setVisible(true);
            }
         }
      } else {
         for (CardBase ctx : this.zy) {
            ctx.setVisible(true);
         }
      }
   }

   public void n(String s) {
      if (s != null && !s.trim().isEmpty()) {
         new Thread(() -> {
            try {
               String s2 = this.LP.o(s.trim(), null);
               if (s2 != null && !s2.isEmpty()) {
                  this.LP.t();
                  this.LP.j(s2);
                  this.LP.m(s2);
                  MinecraftClient.getInstance().execute(this::k);
               }
            } catch (Exception exception) {
               exception.printStackTrace();
            }
         }, "I").start();
      }
   }
}
