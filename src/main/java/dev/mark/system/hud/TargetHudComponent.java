package dev.mark.system.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mark.system.core.ClientMain;
import java.awt.Color;
import dev.mark.system.module.visual.NameTagsModule;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltBorderOutline;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.BuiltText;
import dev.mark.system.render.ColorCache;
import dev.mark.system.render.ColorPair;
import dev.mark.system.render.RadiusConfig;
import dev.mark.system.render.RectangleCache;
import dev.mark.system.render.Size;
import dev.mark.system.render.TextCache;

import dev.mark.system.gui.enums.FourStateEnum;
import dev.mark.system.render.ItemRenderHelper;
import dev.mark.system.render.builder.BorderOutlineBuilder;
import dev.mark.system.render.TextureBuilderSingleton;
public class TargetHudComponent extends HudComponent {
   private static final float apq = 199.0F;
   private static final float XA = 60.0F;
   private static final float akr = 60.0F;
   private static final float OR = 50.0F;
   private static final float hc = 13.0F;
   private static final float sg = 5.0F;
   private static final float aqn = 30.0F;
   private static final float bT = 13.0F;
   private static final float xT = 20.0F;
   private static final float hF = 3.0F;
   private static final float Qm = 5.0F;
   private static final float ahF = 2.0F;
   private static final float NO = 2.0F;
   private static final float and = 5.0F;
   private static final float ayo = 10.0F;
   private static final float avR = 8.0F;
   private static final float ajt = 5.0F;
   private static final float SQ = 0.05F;
   private static final float asR = 0.15F;
   private static final float wp = 0.01F;
   private static final float ave = 0.1F;
   private BuiltRectangle dJ;
   private BuiltRectangle Zc;
   private float auB = 199.0F;
   private float tV = 199.0F;
   private float axk = 0.0F;
   private float Fq = 0.0F;
   private int J = 0;
   private int aif = 0;
   private String tj = "";
   private String rz = "";
   private AbstractClientPlayerEntity Di = null;
   private AbstractClientPlayerEntity En = null;
   private long zf = 0L;
   private static final long dH = 5000L;
   private float he = 0.0F;
   private float Fx = 1.0F;
   private float bz = 0.0F;
   private float akL = 0.0F;
   private float abh = 0.0F;
   private float yB = 0.0F;
   private float ajT = 20.0F;
   private final float[] Ig = new float[4];
   private final float[] an = new float[4];
   private boolean yK = false;
   private FourStateEnum agi = FourStateEnum.NR;

   public TargetHudComponent(float f, float f1) {
      super(f, f1, "Target", "target", false);
      this.width = 199.0F;
      this.totalHeight = 60.0F;
      this.a();
   }

   @Override
   public boolean isAlwaysVisible() {
      return true;
   }

   @Override
   protected void onInitialized() {
      this.backgroundRect = RectangleCache.b(199.0F, 60.0F, 8.0F);
      this.dJ = RectangleCache.b(60.0F, 60.0F, 8.0F);
      this.axk = 0.0F;
      this.Zc = null;
   }

   private void a() {
      AttackEntityCallback.EVENT.register((AttackEntityCallback)(playerentity, world, hand, entity, entityhitresult) -> {
         if (playerentity == MinecraftClient.getInstance().player && entity instanceof PlayerEntity) {
            String s = entity.getName().getString();
            if (!s.equals(this.tj)) {
               this.rz = this.tj;
               this.En = this.Di;
               this.bz = this.Fx;
               this.Fx = 0.0F;
               this.yK = false;
            }

            this.tj = s;
            this.zf = System.currentTimeMillis();
            if (entity instanceof AbstractClientPlayerEntity) {
               this.Di = (AbstractClientPlayerEntity)entity;
               this.akL = this.Di.getHealth();
               this.ajT = this.Di.getMaxHealth();
            }
         }

         return ActionResult.PASS;
      });
   }

   @Override
   protected float calculateContentHeight() {
      return this.b() ? 60.0F : 0.0F;
   }

   private boolean b() {
      return this.hidden ? true : !this.tj.isEmpty() && System.currentTimeMillis() - this.zf < 5000L;
   }

   @Override
   public void render(Matrix4f matrix4f, float f) {
      this.initialize();
      this.recalculateHeight();
      if (this.hidden) {
         this.tj = "SуstemPlayer";
         this.akL = 20.0F;
         this.abh = 20.0F;
         this.yB = 20.0F;
         this.ajT = 20.0F;
         this.he = 1.0F;
         this.Fx = 1.0F;
         this.yK = false;
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient.player != null) {
            this.Di = minecraftclient.player;
            this.aif = 4;
            this.J = 4;
         }
      } else {
         this.d();
      }

      this.c();

       if (this.hidden || this.he > 0.001F) {
         float f1 = this.he * f;
         this.backgroundRect.a(matrix4f, this.x, this.y, f1);
         this.dJ.a(matrix4f, this.x, this.y, f1);
         if (this.bz > 0.001F && this.En != null) {
            this.j(matrix4f, f1 * this.bz, this.En);
            this.k(matrix4f, f1 * this.bz, this.rz);
         }

         if (this.Fx > 0.001F && this.Di != null) {
            this.j(matrix4f, f1 * this.Fx, this.Di);
            this.k(matrix4f, f1 * this.Fx, this.tj);
         }

         this.m(matrix4f, f1);
         this.l(matrix4f, f1);
      } else if (this.he <= 0.001F) {
         if (!this.b()) {
            this.tj = "";
            this.Di = null;
            this.rz = "";
            this.En = null;
         }
      }
   }

   private void c() {
      if (this.tj.isEmpty()) {
         this.auB = 199.0F;
      } else {
         float f = this.aja.c(this.tj, 13.0F);
         float f1 = 65.0F + f + 30.0F;
         this.auB = Math.max(199.0F, f1);
      }

      float f2 = this.auB - this.tV;
      if (Math.abs(f2) > 0.5F) {
         this.tV += f2 * 0.1F;
      } else {
         this.tV = this.auB;
      }

      this.width = this.tV;
      this.backgroundRect = RectangleCache.b(this.tV, 60.0F, 8.0F);
   }

   private void d() {
      boolean flag = this.b();
      if (flag) {
         this.he += 0.05F;
         if (this.he > 1.0F) {
            this.he = 1.0F;
         }
      } else {
         this.he -= 0.05F;
         if (this.he < 0.0F) {
            this.he = 0.0F;
         }
      }

      if (this.bz > 0.001F) {
         this.bz -= 0.1F;
         if (this.bz < 0.0F) {
            this.bz = 0.0F;
            this.En = null;
            this.rz = "";
         }
      }

      if (this.Fx < 1.0F) {
         this.Fx += 0.1F;
         if (this.Fx > 1.0F) {
            this.Fx = 1.0F;
         }
      }

      if (this.Di != null) {
         if (!this.Di.isDead() && !(this.Di.getHealth() <= 0.0F)) {
            this.yK = false;
            this.akL = this.f(this.Di);
         } else {
            this.yK = true;
            this.akL = 0.0F;
         }

         this.ajT = this.g(this.Di);
      }

      float f = this.akL - this.abh;
      if (Math.abs(f) > 0.01F) {
         this.abh += f * 0.15F;
      } else if (Math.abs(f) > 1.0E-4F) {
         this.abh = this.akL;
      }

      float f1 = this.abh - this.yB;
      if (Math.abs(f1) > 0.01F) {
         this.yB += f1 * 0.15F;
      } else if (Math.abs(f1) > 1.0E-4F) {
         this.yB = this.abh;
      }

      this.e();
   }

   private void e() {
      if (this.Di != null && !this.yK) {
         int j = 0;

         for (int k = 0; k < 4; k++) {
            ItemStack itemstack = this.Di.getInventory().getStack(36 + k);
            boolean flag1 = !itemstack.isEmpty();
            this.an[k] = flag1 ? 1.0F : 0.0F;
            if (flag1) {
               j++;
            }
         }

         if (j != this.J) {
            this.J = j;
            if (j == 0) {
               this.agi = FourStateEnum.azA;
            } else if (this.aif == 0) {
               this.agi = FourStateEnum.gS;
               this.aif = j;
            } else {
               this.agi = FourStateEnum.azA;
            }
         }

         switch (this.agi) {
            case NR:
               for (int i1 = 0; i1 < 4; i1++) {
                  float f1 = this.an[i1];
                  if (this.Ig[i1] < f1) {
                     this.Ig[i1] = this.Ig[i1] + 0.05F;
                     if (this.Ig[i1] > f1) {
                        this.Ig[i1] = f1;
                     }
                  } else if (this.Ig[i1] > f1) {
                     this.Ig[i1] = this.Ig[i1] - 0.05F;
                     if (this.Ig[i1] < f1) {
                        this.Ig[i1] = f1;
                     }
                  }
               }
               break;
            case azA:
               boolean flag3 = true;

               for (int k1 = 0; k1 < 4; k1++) {
                  if (this.Ig[k1] > 0.001F) {
                     this.Ig[k1] = this.Ig[k1] - 0.05F;
                     if (this.Ig[k1] < 0.0F) {
                        this.Ig[k1] = 0.0F;
                     }

                     flag3 = false;
                  }
               }

               if (flag3) {
                  if (this.J == 0) {
                     this.agi = FourStateEnum.NR;
                     this.aif = 0;
                  } else {
                     this.agi = FourStateEnum.gS;
                     this.aif = this.J;
                  }
               }
               break;
            case gS:
               for (int l = 0; l < 4; l++) {
                  this.Ig[l] = 0.0F;
               }

               if (Math.abs(this.Fq - this.axk) < 0.5F) {
                  this.agi = FourStateEnum.go;
               }
               break;
            case go:
               boolean flag2 = true;

               for (int j1 = 0; j1 < 4; j1++) {
                  float f2 = this.an[j1];
                  if (this.Ig[j1] < f2) {
                     this.Ig[j1] = this.Ig[j1] + 0.05F;
                     if (this.Ig[j1] > f2) {
                        this.Ig[j1] = f2;
                     }

                     flag2 = false;
                  }
               }

               if (flag2) {
                  this.agi = FourStateEnum.NR;
               }
         }
      } else {
         this.J = 0;
         this.agi = FourStateEnum.azA;
         boolean flag = true;

         for (int i = 0; i < 4; i++) {
            if (this.Ig[i] > 0.001F) {
               this.Ig[i] = this.Ig[i] - 0.05F;
               if (this.Ig[i] < 0.0F) {
                  this.Ig[i] = 0.0F;
               }

               flag = false;
            }
         }

         if (flag) {
            this.agi = FourStateEnum.NR;
            this.aif = 0;
         }
      }

      if (this.aif > 0) {
         this.axk = 10.0F + 20.0F * this.aif + 3.0F * (this.aif - 1);
      } else {
         this.axk = 0.0F;
      }

      float f = this.axk - this.Fq;
      if (Math.abs(f) > 0.5F) {
         this.Fq += f * 0.1F;
      } else {
         this.Fq = this.axk;
      }
   }

   private float f(PlayerEntity playerentity) {
      float f = playerentity.getHealth();
      float f1 = playerentity.getAbsorptionAmount();
      NameTagsModule nametagsmodule = ClientMain.getInstance().getModuleManager().<NameTagsModule>getModule(NameTagsModule.class);
      if (nametagsmodule != null && nametagsmodule.o()) {
         String s = nametagsmodule.t().getFirst();
         if (nametagsmodule.q().equals(s)) {
            float f2 = this.h(playerentity, ScoreboardDisplaySlot.BELOW_NAME);
            if (f2 > 0.0F) {
               return f2;
            }
         } else if (nametagsmodule.r().equals(s)) {
            float f3 = this.i(playerentity);
            if (f3 > 0.0F) {
               return f3 + f1;
            }
         }

         return f + f1;
      } else {
         return f + f1;
      }
   }

   private float g(PlayerEntity playerentity) {
      float f = playerentity.getMaxHealth();
      float f1 = playerentity.getAbsorptionAmount();
      NameTagsModule nametagsmodule = ClientMain.getInstance().getModuleManager().<NameTagsModule>getModule(NameTagsModule.class);
      if (nametagsmodule != null && nametagsmodule.o()) {
         String s = nametagsmodule.t().getFirst();
         return nametagsmodule.q().equals(s) ? Math.max(f, 20.0F) : Math.max(f, 20.0F) + f1;
      } else {
         return f + f1;
      }
   }

   private float h(PlayerEntity playerentity, ScoreboardDisplaySlot scoreboarddisplayslot) {
      try {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient.world == null) {
            return 0.0F;
         }

         Scoreboard scoreboard = minecraftclient.world.getScoreboard();
         if (scoreboard == null) {
            return 0.0F;
         }

         ScoreboardObjective scoreboardobjective = scoreboard.getObjectiveForSlot(scoreboarddisplayslot);
         if (scoreboardobjective == null) {
            return 0.0F;
         }

         ReadableScoreboardScore readablescoreboardscore = scoreboard.getScore(playerentity, scoreboardobjective);
         if (readablescoreboardscore != null) {
            return readablescoreboardscore.getScore();
         }
      } catch (Exception exception) {
      }

      return 0.0F;
   }

   private float i(PlayerEntity playerentity) {
      try {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient.world == null) {
            return 0.0F;
         }

         Scoreboard scoreboard = minecraftclient.world.getScoreboard();
         if (scoreboard == null) {
            return 0.0F;
         }

         for (ScoreboardObjective scoreboardobjective : scoreboard.getObjectives()) {
            if (scoreboardobjective != null) {
               String s = scoreboardobjective.getName().toLowerCase();
               String s1 = scoreboardobjective.getDisplayName().getString().toLowerCase();
               if (s.contains("health") || s.contains("EaseLinear") || s.contains("heart") || s1.contains("♥") || s1.contains("❤") || s1.contains("health")) {
                  ReadableScoreboardScore readablescoreboardscore = scoreboard.getScore(playerentity, scoreboardobjective);
                  if (readablescoreboardscore != null) {
                     return readablescoreboardscore.getScore();
                  }
               }
            }
         }

         ScoreboardObjective scoreboardobjective1 = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.LIST);
         if (scoreboardobjective1 != null) {
            ReadableScoreboardScore readablescoreboardscore1 = scoreboard.getScore(playerentity, scoreboardobjective1);
            if (readablescoreboardscore1 != null) {
               return readablescoreboardscore1.getScore();
            }
         }
      } catch (Exception exception) {
      }

      return 0.0F;
   }

   private void j(Matrix4f matrix4f, float f, AbstractClientPlayerEntity abstractclientplayerentity) {
      if (abstractclientplayerentity != null && !(f <= 0.001F)) {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         Identifier identifier = abstractclientplayerentity.getSkinTextures().texture();
         float f1 = this.x + 5.0F;
         float f2 = this.y + 5.0F;
         int i = ColorCache.d(255, 255, 255, f);
         ColorPair colorpair = ColorCache.e(i);
         TextureBuilderSingleton.a()
            .a(new Size(50.0F, 50.0F))
            .e(0.125F, 0.125F, 0.125F, 0.125F, minecraftclient.getTextureManager().getTexture(identifier))
            .c(colorpair)
            .d(1.0F)
            .b(new RadiusConfig(4.0F, 4.0F, 4.0F, 4.0F))
            .a()
            .render(matrix4f, f1, f2, 0.0F);
      }
   }

   private void k(Matrix4f matrix4f, float f, String s) {
      if (!(f <= 0.001F)) {
         float f1 = this.x + 60.0F + 5.0F;
         float f2 = this.y + 13.0F;
         BuiltText builttext = TextCache.a(this.aja, s, 13.0F, Color.WHITE);
         builttext.a(matrix4f, f1, f2, f);
      }
   }

   private void l(Matrix4f matrix4f, float f) {
      if (!(this.Fq < 0.5F)) {
         float f1 = 34.0F;
         this.Zc = RectangleCache.b(this.Fq, f1, 8.0F);
         float f2 = this.x + (this.tV - this.Fq) / 2.0F;
         float f3 = this.y + 60.0F + 5.0F;
         this.Zc.a(matrix4f, f2, f3, f);
         if (this.Di != null) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float f4 = f2 + 5.0F;
            float f5 = f3 + 5.0F;

            for (int i = 3; i >= 0; i--) {
               ItemStack itemstack = this.Di.getInventory().getStack(36 + i);
               float f6 = this.Ig[i];
               if (f6 > 0.001F && !itemstack.isEmpty()) {
                  ItemRenderHelper.a(matrix4f, itemstack, f4, f5, 20.0F, f * f6);
                  float f7 = f5 + 20.0F + 2.0F;
                  ItemRenderHelper.b(matrix4f, f4, f7, itemstack, 20.0F, 2.0F, f * f6);
                  f4 += 23.0F;
               }
            }

            RenderSystem.disableBlend();
         }
      }
   }

   private void m(Matrix4f matrix4f, float f) {
      if (this.Di != null) {
         float f1 = this.x + 60.0F + 5.0F;
         float f2 = this.y + 13.0F + 13.0F + 8.0F;
         float f3 = Math.max(0.0F, Math.min(1.0F, this.yB / this.ajT));
         if (f3 > 0.001F) {
            float f4 = this.auB - 60.0F - 5.0F - 5.0F - 50.0F;
            float f5 = f4 * f3;
            BuiltRectangle builtrectangle = RectangleCache.b(f5, 10.0F, 4.0F);
            builtrectangle.a(matrix4f, f1, f2, f);
            BuiltBorderOutline builtborderoutline = new BorderOutlineBuilder().a(f5, 10.0F).b(4.0F).c(1.5F).d(0.5F).a();
            builtborderoutline.a(matrix4f, f1, f2, f);
         }

         float f6 = this.auB - 60.0F - 5.0F - 5.0F - 50.0F;
         float f7 = f1 + f6 + 5.0F;
         String s;
         if (this.yK) {
            s = "0.0";
         } else {
            NameTagsModule nametagsmodule = ClientMain.getInstance().getModuleManager().<NameTagsModule>getModule(NameTagsModule.class);
            s = this.n(this.abh, this.ajT, nametagsmodule);
         }

         BuiltText builttext = TextCache.a(this.aja, s, 13.0F, Color.WHITE);
         builttext.a(matrix4f, f7, f2 - 3.5F, f);
      }
   }

   private String n(float f, float f1, NameTagsModule nametagsmodule) {
      String s = nametagsmodule != null ? nametagsmodule.s().getFirst() : null;
      if ("Hearts".equals(s)) {
         int i = Math.round(f / 2.0F);
         int j = Math.round(f1 / 2.0F);
         return i + "/" + j;
      } else {
         return String.format("%.1f", f);
      }
   }

   @Override
   protected void renderContent(Matrix4f matrix4f, float f, float f1, float f2) {
   }

   @Override
   public boolean isHovered(double d0, double d1) {
       if (this.hidden || this.he >= 0.5F) {
         return d0 >= this.x && d0 <= this.x + this.tV && d1 >= this.y && d1 <= this.y + 60.0F;
      }
      return false;
   }

   @Override
   public boolean mouseClicked(double d0, double d1, int i) {
       if (this.hidden || this.he >= 0.5F) {
         if (i == 0 && this.isHovered(d0, d1)) {
            this.dragging = true;
            this.dragOffsetX = d0 - this.x;
            this.dragOffsetY = d1 - this.y;
            return true;
         }
      }
      return false;
   }
}
