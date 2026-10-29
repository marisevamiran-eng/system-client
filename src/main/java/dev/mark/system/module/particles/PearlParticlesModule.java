package dev.mark.system.module.particles;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.math.Vec3d;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.module.util.ParticleEffects;

public class PearlParticlesModule extends ParticleModule {
   private ColorSetting colorSetting = new ColorSetting("Color", "Color частиц следа", new Color(120, 179, 255));
   private SliderSetting spawnRateSetting = new SliderSetting("Частота спавна", "Сколько частиц спавнить за тик", 10.0, 1.0, 10.0, 1.0);
   private SliderSetting sizeSetting = new SliderSetting("Size", "Size партиклов", 0.3F, 0.05F, 0.5, 0.01F);
   private SliderSetting lifetimeSetting = new SliderSetting("Время жизни", "Время жизни партиклов в secундах", 0.5, 0.5, 3.0, 0.1F);
   private SliderSetting spreadSetting = new SliderSetting("Разброс", "Разброс частиц в стороны", 0.2F, 0.01F, 0.2F, 0.01F);
   private SliderSetting gravitySetting = new SliderSetting("Гравитация", "Сила гравитации для частиц", 0.008F, 0.0, 0.03F, 0.001F);
   private SliderSetting fadeSpeedSetting = new SliderSetting("Скорость исчезновения", "Контролирует скорость исчезновения частиц", 2.0, 0.3F, 2.0, 0.1F);
   private BooleanSetting colorAnimationSetting = new BooleanSetting("Анимация Colorов", "Радужная анимация цвета", false);
   private final Map<Integer, Vec3d> CL = new HashMap<Integer, Vec3d>();

   public PearlParticlesModule() {
      super("ПеарлПартислез", "Создает след из частиц за эндер-пёрлом", Category.PARTICLES);
      this.addSettings(
         this.colorSetting,
         this.spawnRateSetting,
         this.sizeSetting,
         this.lifetimeSetting,
         this.spreadSetting,
         this.gravitySetting,
         this.fadeSpeedSetting,
         this.colorAnimationSetting
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.CL.clear();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.CL.clear();
   }

   @Override
   protected int b() {
      return 1000;
   }

   @Override
   protected int c() {
      return 100;
   }

   @Override
   protected long e() {
      return (long)(this.lifetimeSetting.getValue() * 1000.0);
   }

   @Override
   protected float f() {
      return this.sizeSetting.getFloatValue();
   }

   @Override
   protected int g() {
      return 2;
   }

   @Override
   protected float i() {
      return this.fadeSpeedSetting.getFloatValue();
   }

   @Override
   protected boolean j() {
      return this.colorAnimationSetting.getValue();
   }

   @Override
   protected Color k() {
      return this.colorSetting.getColor();
   }

   @Override
   protected void a() {
      this.CL.entrySet().removeIf(entry -> {
         return !(this.getWorld().getEntityById(entry.getKey()) instanceof EnderPearlEntity);
      });

      for (Entity entity : this.getClientWorld().getEntities()) {
         if (entity instanceof EnderPearlEntity enderpearlentity) {
            Vec3d vec3d = enderpearlentity.getPos();
            if (this.CL.containsKey(enderpearlentity.getId())) {
               ParticleEffects.b(this, vec3d, (int)this.spawnRateSetting.getValue(), this.spreadSetting.getFloatValue(), this.gravitySetting.getFloatValue(), 0.0F);
            }

            this.CL.put(enderpearlentity.getId(), vec3d);
         }
      }
   }
}
