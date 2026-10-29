package dev.mark.system.hud;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.BuiltText;
import dev.mark.system.render.ColorCache;
import dev.mark.system.render.ColorPair;
import dev.mark.system.render.RectangleCache;
import dev.mark.system.render.Size;

import dev.mark.system.util.StatusEffectUtils;
import dev.mark.system.render.TextureBuilderSingleton;
public class PotionsHudComponent extends ListHudComponent<PotionListEntry> {
   private static final float avs = 20.0F;
   private static final float ED = 20.0F;
   private static final float WH = 7.0F;
   private static final float XD = 13.0F;
   private static final float UU = 4.0F;
   private static final float aoW = 31.0F;
   private static final float YB = 40.0F;
   private static final Color avj = new Color(160, 160, 160);

   public PotionsHudComponent(float f, float f1) {
      super(f, f1, "Potions", "potions", 20.0F, 31.0F);
   }

   @Override
   public boolean isAlwaysVisible() {
      return true;
   }

   @Override
   protected void b() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      if (minecraftclient.player != null) {
         HashSet hashset = new HashSet();
         hashset.add("Speed_0");
         hashset.add("Strength_0");
         hashset.add("Regeneration_0");
         this.entryManager.a(hashset, s -> {
            String s1;
            String s2;
            switch (s) {
               case "Speed_0":
                  s1 = "Speed";
                  s2 = "5:00";
                  break;
               case "Strength_0":
                  s1 = "Strength";
                  s2 = "3:30";
                  break;
               case "Regeneration_0":
                  s1 = "Regeneration";
                  s2 = "0:45";
                  break;
               default:
                  return null;
            }

            return new PotionListEntry(s1, s2, null, null, s);
         });
         float f = this.calculateMaxWidth();
         if (f > this.width) {
            this.width = f;
         }
      }
   }

   @Override
   protected void a() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      if (minecraftclient.player != null) {
         HashSet hashset = new HashSet();
         Collection collection = minecraftclient.player.getStatusEffects();
         HashMap hashmap = new HashMap();

         for (Object _sei : collection) {
         StatusEffectInstance statuseffectinstance = (StatusEffectInstance)_sei;
            RegistryEntry registryentry = statuseffectinstance.getEffectType();
            StatusEffect statuseffect = (StatusEffect)registryentry.value();
            int i = statuseffectinstance.getAmplifier();
            String s1 = statuseffect.getName().getString();
            String s = s1 + "_" + i;
            hashset.add(s);
            hashmap.put(s, statuseffectinstance);
         }

         this.entryManager.a(hashset, s2 -> {
            StatusEffectInstance statuseffectinstance2 = (StatusEffectInstance)hashmap.get(s2);
            if (statuseffectinstance2 != null) {
               StatusEffect statuseffect1 = (StatusEffect)statuseffectinstance2.getEffectType().value();
               int j = statuseffectinstance2.getAmplifier();
               String s3 = StatusEffectUtils.f(statuseffect1, 15);
               if (j > 0) {
                  String s5 = StatusEffectUtils.a(j + 1);
                  s3 = s3 + " " + s5;
               }

               String s4 = StatusEffectUtils.b(statuseffectinstance2.getDuration());
               return new PotionListEntry(s3, s4, statuseffectinstance2.getEffectType(), statuseffectinstance2, s2);
            } else {
               return null;
            }
         });

         for (PotionListEntry PotionListEntry : this.entryManager.e()) {
            StatusEffectInstance statuseffectinstance1 = (StatusEffectInstance)hashmap.get(PotionListEntry.c());
            if (statuseffectinstance1 != null) {
               PotionListEntry.a(StatusEffectUtils.b(statuseffectinstance1.getDuration()));
            }
         }

         float f = this.calculateMaxWidth();
         if (f > this.width) {
            this.width = f;
         }
      }
   }

   private float calculateMaxWidth() {
      float f = 225.0F;

      for (PotionListEntry PotionListEntry : this.entryManager.e()) {
         if (PotionListEntry.d().g() > 0.001F) {
            float f1 = this.ew.c(PotionListEntry.TK, 13.0F);
            float f2 = this.ew.c(PotionListEntry.BP, 13.0F);
            float f3 = Math.max(40.0F, f2 + 8.0F);
            float f4 = 27.0F + f1 + 7.0F + f3;
            f = Math.max(f, f4);
         }
      }

      return f + 20.0F;
   }

   protected void b(Matrix4f matrix4f, float f, float f1, PotionListEntry PotionListEntry, float f2, float f3) {
      if (!(f3 < 0.3F)) {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         float f4 = f + 10.0F;
         if (PotionListEntry.zk != null) {
            StatusEffectSpriteManager statuseffectspritemanager = minecraftclient.getStatusEffectSpriteManager();
            Sprite sprite = statuseffectspritemanager.getSprite(PotionListEntry.zk);
            AbstractTexture abstracttexture = minecraftclient.getTextureManager().getTexture(sprite.getAtlasId());
            int i = ColorCache.d(255, 255, 255, f3);
            ColorPair colorpair = ColorCache.e(i);
            TextureBuilderSingleton.a()
               .a(new Size(20.0F, 20.0F))
               .e(sprite.getMinU(), sprite.getMinV(), sprite.getMaxU() - sprite.getMinU(), sprite.getMaxV() - sprite.getMinV(), abstracttexture)
               .c(colorpair)
               .d(1.0F)
               .a()
               .render(matrix4f, f4, f1 + 1.0F, 0.0F);
         } else {
            BuiltRectangle builtrectangle = RectangleCache.b(20.0F, 20.0F, 4.0F);
            builtrectangle.a(matrix4f, f4, f1 + 1.0F, f3 * 0.3F);
         }

         float f6 = f4 + 20.0F + 7.0F;
         BuiltText builttext1 = this.w(this.Cj, PotionListEntry.TK, 13.0F, avj);
         builttext1.a(matrix4f, f6, f1 + (f2 - 13.0F) * 0.5F, f3);
         float f7 = this.ew.c(PotionListEntry.BP, 13.0F);
         float f8 = Math.max(40.0F, f7 + 8.0F);
         float f9 = f + this.width - 10.0F - f8;
         BuiltText builttext = this.w(this.ew, PotionListEntry.BP, 13.0F, Color.WHITE);
         float f5 = f9 + (f8 - f7) * 0.5F;
         builttext.a(matrix4f, f5, f1 + (f2 - 13.0F) * 0.5F - 1.0F, f3);
      }
   }

   @Override
   protected void d(Matrix4f matrix4f, float f, float f1, PotionListEntry listentry, float f2, float f3) {
      this.b(matrix4f, f, f1, (PotionListEntry)listentry, f2, f3);
   }
}
