package dev.mark.system.hud;

import dev.mark.system.gui.animation.HoverStateAnimator;
import dev.mark.system.gui.animation.ProgressAnimation;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.RectangleCache;

import dev.mark.system.render.ItemRenderHelper;
import dev.mark.system.core.CloudApiClient;
public class ArmorHudComponent extends HudComponent {
   private static final float DH = 5.0F;
   private static final float ac = 8.0F;
   private static final float CS = 28.0F;
   private static final float PM = 2.0F;
   private static final float ahR = 26.0F;
   private static final float Qo = 2.0F;
   private final MinecraftClient mc = MinecraftClient.getInstance();
   private final ProgressAnimation il = new ProgressAnimation();
   private final Map<Integer, HoverStateAnimator> QB = new HashMap<Integer, HoverStateAnimator>();
   private boolean atI = false;
   private float apV = 0.0F;

   public ArmorHudComponent(float f, float f1) {
      super(f, f1, "", "armor");

      for (int i = 0; i < 4; i++) {
         HoverStateAnimator HoverStateAnimator = new HoverStateAnimator();
         HoverStateAnimator.a();
         this.QB.put(i, HoverStateAnimator);
      }
   }

   @Override
   public boolean isAlwaysVisible() {
      return true;
   }

   @Override
   protected float calculateContentHeight() {
      return 0.0F;
   }

   @Override
   protected void renderContent(Matrix4f matrix4f, float f, float f1, float f2) {
   }

   @Override
   public void render(Matrix4f matrix4f, float f) {
      this.initialize();
      if (this.mc.player != null) {
         if (!this.atI) {
            this.il.a();
            this.atI = true;
         }

         if (this.hidden) {
            this.a(matrix4f, f);
         } else {
            ItemStack[] aitemstack = new ItemStack[4];
            boolean flag = false;

            for (int i = 0; i < 4; i++) {
               aitemstack[i] = this.mc.player.getInventory().getStack(36 + i);
               if (!aitemstack[i].isEmpty()) {
                  flag = true;
               }
            }

            for (int l = 0; l < 4; l++) {
               HoverStateAnimator HoverStateAnimator = this.QB.get(l);
               HoverStateAnimator.e(!aitemstack[l].isEmpty());
               HoverStateAnimator.b();
            }

            this.il.f(flag);
            this.il.b();
            float f8 = this.il.h();
            if (!(f8 < 0.001F)) {
               float f9 = 5.0F;

               for (int j = 0; j < 4; j++) {
                  float f1 = this.QB.get(j).g();
                  if (f1 > 0.001F) {
                     f9 += 33.0F * f1;
                  }
               }

               if (f8 > 0.999F || this.il.g()) {
                  this.apV = f9;
               }

               float f10 = this.apV;
               float f11 = 42.0F;
               this.width = f10;
               this.totalHeight = f11;
               BuiltRectangle builtrectangle = RectangleCache.b(f10, f11, 8.0F);
               builtrectangle.a(matrix4f, this.x, this.y, f * f8);
               float f2 = this.x + 5.0F;
               float f3 = this.y + 5.0F;

               for (int k = 3; k >= 0; k--) {
                  ItemStack itemstack = aitemstack[k];
                  float f4 = this.QB.get(k).g();
                  if (f4 > 0.001F) {
                     float f5;
                     if (this.il.g()) {
                        float f6 = Math.max(0.0F, (f8 - 0.3F) / 0.7F);
                        f5 = f * f4 * f6;
                     } else if (!flag) {
                        float f12 = Math.min(1.0F, f8 / 0.7F);
                        f5 = f * f4 * f12;
                     } else {
                        f5 = f * f4;
                     }

                     if (!itemstack.isEmpty() && f5 > 0.001F) {
                        ItemRenderHelper.a(matrix4f, itemstack, f2 + 0.3F, f3, 28.0F, f5);
                        float f13 = f3 + 28.0F + 2.0F;
                        float f7 = f2 + 1.0F;
                        ItemRenderHelper.b(matrix4f, f7, f13, itemstack, 26.0F, 2.0F, f5);
                     }

                     if (flag) {
                        f2 += 33.0F * f4;
                     } else {
                        f2 += 33.0F;
                     }
                  }
               }
            }
         }
      }
   }

   private void a(Matrix4f matrix4f, float f) {
      float f1 = 137.0F;
      float f2 = 42.0F;
      this.width = f1;
      this.totalHeight = f2;
      this.apV = f1;
      BuiltRectangle builtrectangle = RectangleCache.b(f1, f2, 8.0F);
      builtrectangle.a(matrix4f, this.x, this.y, f);
      float f3 = this.x + 5.0F;
      float f4 = this.y + 5.0F;

      for (int i = 3; i >= 0; i--) {
         ItemStack itemstack = this.mc.player.getInventory().getStack(36 + i);
         if (!itemstack.isEmpty()) {
            ItemRenderHelper.a(matrix4f, itemstack, f3 + 0.3F, f4, 28.0F, f);
            float f5 = f4 + 28.0F + 2.0F;
            float f6 = f3 + 1.0F;
            ItemRenderHelper.b(matrix4f, f6, f5, itemstack, 26.0F, 2.0F, f);
         } else {
            BuiltRectangle builtrectangle1 = RectangleCache.b(28.0F, 28.0F, 4.0F);
            builtrectangle1.a(matrix4f, f3, f4, f * 0.3F);
         }

         f3 += 33.0F;
      }
   }

   private float b() {
      return this.hidden ? 137.0F : this.apV;
   }

   private float c() {
      return 42.0F;
   }

   @Override
   public boolean mouseClicked(double d0, double d1, int i) {
      if (i == 0) {
         float f = this.b();
         float f1 = this.c();
         if (d0 >= this.x && d0 <= this.x + f && d1 >= this.y && d1 <= this.y + f1) {
            this.dragging = true;
            this.dragOffsetX = d0 - this.x;
            this.dragOffsetY = d1 - this.y;
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isHovered(double d0, double d1) {
      float f = this.b();
      float f1 = this.c();
      return d0 >= this.x && d0 <= this.x + f && d1 >= this.y && d1 <= this.y + f1;
   }
}
