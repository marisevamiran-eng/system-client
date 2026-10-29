package dev.mark.system.hud;

import com.google.common.base.Suppliers;
import dev.mark.system.config.Config;
import dev.mark.system.core.ClientMain;
import dev.mark.system.data.Position;
import dev.mark.system.font.MSDFFont;
import dev.mark.system.gui.GuiConstants;
import java.awt.Color;
import java.util.HashMap;
import java.util.function.Supplier;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.BuiltText;
import dev.mark.system.render.RectangleCache;
import dev.mark.system.render.TextCache;

import dev.mark.system.core.NativeBootstrapAlt;
public abstract class HudComponent implements GuiConstants {
   private static final Supplier<MSDFFont> iB = Suppliers.memoize(() -> {
      return MSDFFont.g().b("b").c("b").e();
   });
   private static final Supplier<MSDFFont> YX = Suppliers.memoize(() -> {
      return MSDFFont.g().b("bb").c("bb").e();
   });
   private static final Supplier<MSDFFont> gj = Suppliers.memoize(() -> {
      return MSDFFont.g().b("a").c("a").e();
   });
   private static final Supplier<MSDFFont> kJ = Suppliers.memoize(() -> {
      return MSDFFont.g().b("native_bootstrap_alt").c("native_bootstrap_alt").e();
   });
   protected final MSDFFont ew = iB.get();
   protected final MSDFFont aja = YX.get();
   protected final MSDFFont Cj = gj.get();
   protected final MSDFFont Eb = kJ.get();
   protected static final float az = 5.0F;
   protected static final float Pp = 16.0F;
   protected static final float jE = 29.0F;
   protected static final float gT = 5.0F;
   protected static final float zZ = 10.0F;
   protected static final float ZY = 10.0F;
   protected static final float DO = 5.0F;
   protected static final float fL = 245.0F;
   protected float x;
   protected float y;
   protected float savedX;
   protected float savedY;
   protected final float defaultX;
   protected final float defaultY;
   protected float width = 245.0F;
   protected float totalHeight;
   protected boolean dragging = false;
   protected double dragOffsetX;
   protected double dragOffsetY;
   protected boolean initialized = false;
   protected boolean hidden = false;
   protected final String displayName;
   protected final String configKey;
   protected final boolean hasHeader;
   protected BuiltRectangle backgroundRect;
   protected BuiltRectangle headerRect;
   private HudLayoutManager layoutManager;

   public HudComponent(float f, float f1, String s, String s1) {
      this(f, f1, s, s1, true);
   }

   public HudComponent(float f, float f1, String s, String s1, boolean flag) {
      this.configKey = s1;
      this.displayName = s;
      this.hasHeader = flag;
      this.defaultX = f;
      this.defaultY = f1;
      this.x = f;
      this.y = f1;
      this.savedX = f;
      this.savedY = f1;
   }

   protected void initialize() {
      if (!this.initialized) {
         ClientMain clientmain = ClientMain.getInstance();
         boolean flag = clientmain != null && clientmain.isModulesLoaded();
         if (!flag) {
            return;
         }

         Position position = this.z();
         if (this.g(position)) {
            this.x = position.getX();
            this.y = position.getY();
         } else {
            this.x = this.defaultX;
            this.y = this.defaultY;
            this.savePosition();
         }

         this.savedX = this.x;
         this.savedY = this.y;
         this.onInitialized();
         this.initialized = true;
         this.recalculateHeight();
      }
   }

   private boolean g(Position position) {
      if (position == null) {
         return false;
      } else {
         float f = position.getX();
         float f1 = position.getY();
         return Float.isNaN(f) || Float.isNaN(f1) || Float.isInfinite(f) || Float.isInfinite(f1)
            ? false
            : !(f < -5000.0F) && !(f > 20000.0F) && !(f1 < -5000.0F) && !(f1 > 20000.0F);
      }
   }

   protected void onInitialized() {
   }

   protected void recalculateHeight() {
      float f = this.calculateContentHeight();
       if (f == 0.0F && !this.isAlwaysVisible()) {
         this.totalHeight = 0.0F;
      } else {
         if (this.hasHeader) {
            this.totalHeight = 29.0F + f + 10.0F;
         } else {
            this.totalHeight = f;
         }

         this.backgroundRect = RectangleCache.b(this.width, this.totalHeight, 8.0F);
         if (this.hasHeader) {
            this.headerRect = RectangleCache.b(this.width, 29.0F, 8.0F);
         }
      }
   }

   protected abstract float calculateContentHeight();

   public void render(Matrix4f matrix4f, float f) {
      this.initialize();
      if (!this.dragging) {
         this.x = this.savedX;
         this.y = this.savedY;
      }

      if (!this.p()) {
         this.x = this.defaultX;
         this.y = this.defaultY;
         this.savedX = this.defaultX;
         this.savedY = this.defaultY;
         this.savePosition();
      }

      this.recalculateHeight();
      this.clampPosition();
      if (this.backgroundRect != null) {
         this.backgroundRect.a(matrix4f, this.x, this.y, f);
      }

      if (this.hasHeader && this.headerRect != null) {
         this.headerRect.a(matrix4f, this.x, this.y, f);
         BuiltText builttext = TextCache.a(this.ew, this.displayName, 16.0F, Bz);
         float f1 = this.ew.c(this.displayName, 16.0F);
         float f2 = this.x + (this.width - f1) * 0.5F;
         float f3 = this.y + 5.0F;
         builttext.a(matrix4f, f2, f3, f);
      }

      float f4 = this.hasHeader ? this.y + 29.0F + 5.0F : this.y;
      this.renderContent(matrix4f, this.x, f4, f);
   }

   private boolean p() {
      return !Float.isNaN(this.x) && !Float.isNaN(this.y) && !Float.isInfinite(this.x) && !Float.isInfinite(this.y);
   }

   protected void clampPosition() {
      float f = HudManager.b().k();
      float f1 = 1920.0F - this.width;
      float f2 = f - this.totalHeight;
      if (f1 < 0.0F) {
         f1 = 0.0F;
      }

      if (f2 < 0.0F) {
         f2 = 0.0F;
      }

      this.x = Math.max(0.0F, Math.min(this.x, f1));
      this.y = Math.max(0.0F, Math.min(this.y, f2));
   }

   protected abstract void renderContent(Matrix4f matrix4f, float f, float f1, float f2);

   public boolean mouseClicked(double d0, double d1, int i) {
      if (i == 0 && this.isHovered(d0, d1)) {
         this.dragging = true;
         this.dragOffsetX = d0 - this.x;
         this.dragOffsetY = d1 - this.y;
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseReleased(double d0, double d1, int i) {
      if (i == 0 && this.dragging) {
         this.dragging = false;
         this.savedX = this.x;
         this.savedY = this.y;
         this.savePosition();
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseDragged(double d0, double d1, int i, double d2, double d3) {
      if (this.dragging) {
         float f = (float)(d0 - this.dragOffsetX);
         float f1 = (float)(d1 - this.dragOffsetY);
         if (!Float.isNaN(f) && !Float.isInfinite(f)) {
            this.x = f;
         }

         if (!Float.isNaN(f1) && !Float.isInfinite(f1)) {
            this.y = f1;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isHovered(double d0, double d1) {
      float h = Math.max(this.totalHeight, 20.0F);
      return d0 >= this.x && d0 <= this.x + this.width && d1 >= this.y && d1 <= this.y + h;
   }

   protected BuiltText w(MSDFFont msdffont, String s, float f, Color color) {
      return TextCache.a(msdffont, s, f, color);
   }

   public void setPosition(float f, float f1) {
      if (!Float.isNaN(f) && !Float.isInfinite(f)) {
         this.x = f;
         this.savedX = f;
      }

      if (!Float.isNaN(f1) && !Float.isInfinite(f1)) {
         this.y = f1;
         this.savedY = f1;
      }

      this.savePosition();
   }

   protected void savePosition() {
      try {
         ClientMain clientmain = ClientMain.getInstance();
         if (clientmain != null && clientmain.isModulesLoaded()) {
            Config config = clientmain.getConfigManager().x();
            if (config != null) {
               if (config.d() == null) {
                  config.q(new HashMap<String, Position>());
               }

               config.d().put(this.configKey, new Position(this.savedX, this.savedY));
               if (clientmain.getConfigSyncManager() != null) {
                  clientmain.getConfigSyncManager().d(config);
               }
            }
         }
      } catch (Exception exception) {
      }
   }

   protected Position z() {
      try {
         ClientMain clientmain = ClientMain.getInstance();
         if (clientmain == null) {
            return null;
         } else {
            Config config = clientmain.getConfigManager().x();
            if (config == null) {
               return null;
            } else if (config.d() == null) {
               config.q(new HashMap<String, Position>());
               return null;
            } else {
               return config.d().get(this.configKey);
            }
         }
      } catch (Exception exception) {
         return null;
      }
   }

   public boolean isAlwaysVisible() {
      return false;
   }

   public MSDFFont B() {
      return this.ew;
   }

   public MSDFFont C() {
      return this.aja;
   }

   public MSDFFont D() {
      return this.Cj;
   }

   public MSDFFont E() {
      return this.Eb;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getSavedX() {
      return this.savedX;
   }

   public float getSavedY() {
      return this.savedY;
   }

   public float getDefaultX() {
      return this.defaultX;
   }

   public float getDefaultY() {
      return this.defaultY;
   }

   public float getWidth() {
      return this.width;
   }

   public float getTotalHeight() {
      return this.totalHeight;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public double O() {
      return this.dragOffsetX;
   }

   public double P() {
      return this.dragOffsetY;
   }

   public boolean Q() {
      return this.initialized;
   }

   public boolean R() {
      return this.hidden;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getConfigKey() {
      return this.configKey;
   }

   public boolean hasHeader() {
      return this.hasHeader;
   }

   public BuiltRectangle V() {
      return this.backgroundRect;
   }

   public BuiltRectangle W() {
      return this.headerRect;
   }

   public void X(float f) {
      this.x = f;
   }

   public void Y(float f) {
      this.y = f;
   }

   public void Z(float f) {
      this.savedX = f;
   }

   public void NativeBootstrapAlt(float f) {
      this.savedY = f;
   }

   public void ab(float f) {
      this.width = f;
   }

   public void ac(float f) {
      this.totalHeight = f;
   }

   public void ad(boolean flag) {
      this.dragging = flag;
   }

   public void ae(double d0) {
      this.dragOffsetX = d0;
   }

   public void af(double d0) {
      this.dragOffsetY = d0;
   }

   public void ag(boolean flag) {
      this.initialized = flag;
   }

   public void ah(boolean flag) {
      this.hidden = flag;
   }

   public void ai(BuiltRectangle builtrectangle) {
      this.backgroundRect = builtrectangle;
   }

   public void aj(BuiltRectangle builtrectangle) {
      this.headerRect = builtrectangle;
   }

   public HudLayoutManager ak() {
      return this.layoutManager;
   }

   public void al(HudLayoutManager hudlayoutmanager) {
      this.layoutManager = hudlayoutmanager;
   }
}
