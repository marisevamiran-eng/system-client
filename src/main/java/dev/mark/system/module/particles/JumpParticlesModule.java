package dev.mark.system.module.particles;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.module.util.ParticleEffects;
import dev.mark.system.render.builder.Line3dBuilder;
import dev.mark.system.render.particle.JumpWaveParticle;

public class JumpParticlesModule extends ParticleModule {
   private ListSetting modeSetting = new ListSetting(
      "Mode", "Choose between particles and waves", Arrays.<String>asList("Particles", "Waves"), List.<String>of("Particles"), false
   );
   private SliderSetting countSetting = new SliderSetting("Count", "Particle count", 30.0, 5.0, 50.0, 1.0);
   private SliderSetting sizeSetting = new SliderSetting("Size", "Particle size", 0.3F, 0.05F, 0.5, 0.01F);
   private SliderSetting lifetimeSetting = new SliderSetting("Lifetime", "Particle lifetime", 2.0, 0.5, 5.0, 0.1F);
   private SliderSetting speedSetting = new SliderSetting("Speed", "Particle spread speed", 0.1F, 0.05F, 0.5, 0.01F);
   private BooleanSetting colorAnimationSetting = new BooleanSetting("Color Animation", "Rainbow animation", false);
   private ColorSetting colorSetting = new ColorSetting("Color", "Particle color", new Color(100, 200, 255));
   public ColorSetting waveColorSetting = new ColorSetting("Wave Color", "Wave effect color", new Color(85, 170, 255));
   public SliderSetting durationSetting = new SliderSetting("Duration", "Wave duration in ms", 1000.0, 300.0, 1500.0, 100.0);
   public SliderSetting radiusSetting = new SliderSetting("Radius", "Maximum wave radius", 8.0, 3.0, 10.0, 1.0);
   public SliderSetting lineWidthSetting = new SliderSetting("Line Width", "Wave line width", 2.0, 0.5, 3.0, 0.5);
   public BooleanSetting glowSetting = new BooleanSetting("Glow", "Wave glow effect", false);
   public SliderSetting appearanceSetting = new SliderSetting("Appearance", "Wave appearance duration (ms)", 150.0, 50.0, 500.0, 50.0);
   public SliderSetting disappearanceSetting = new SliderSetting("Disappearance", "Wave disappearance duration (ms)", 300.0, 100.0, 800.0, 50.0);
   private static final float ayJ = 1.0F;
   private final List<JumpWaveParticle> jn = new ArrayList<JumpWaveParticle>();
   public final Line3dBuilder apz = new Line3dBuilder();
   private boolean ds = false;
   private boolean mB = false;

   public JumpParticlesModule() {
      super("ЖумпПартислез", "Создает эффектные частицы или волны при прыжке", Category.PARTICLES);
      this.b();
      this.addSettings(
         this.modeSetting,
         this.countSetting,
         this.sizeSetting,
         this.lifetimeSetting,
         this.speedSetting,
         this.colorAnimationSetting,
         this.colorSetting,
         this.waveColorSetting,
         this.durationSetting,
         this.radiusSetting,
         this.lineWidthSetting,
         this.glowSetting,
         this.appearanceSetting,
         this.disappearanceSetting
      );
   }

   protected void initVisibility() {
      for (Object object : new Object[]{
         this.countSetting, this.sizeSetting, this.lifetimeSetting, this.speedSetting, this.colorAnimationSetting, this.colorSetting
      }) {
         if (object instanceof SliderSetting slidersetting) {
            slidersetting.setVisibilitySupplier(() -> {
               return "Частицы".equals(this.modeSetting.getFirst());
            });
         }

         if (object instanceof BooleanSetting booleansetting) {
            booleansetting.setVisibilitySupplier(() -> {
               return "Частицы".equals(this.modeSetting.getFirst());
            });
         }

         if (object instanceof ColorSetting colorsetting) {
            colorsetting.setVisibilitySupplier(() -> {
               return "Частицы".equals(this.modeSetting.getFirst());
            });
         }
      }

      for (Object object1 : new Object[]{
         this.waveColorSetting,
         this.durationSetting,
         this.radiusSetting,
         this.lineWidthSetting,
         this.glowSetting,
         this.appearanceSetting,
         this.disappearanceSetting
      }) {
         if (object1 instanceof SliderSetting slidersetting1) {
            slidersetting1.setVisibilitySupplier(() -> {
               return "Волны".equals(this.modeSetting.getFirst());
            });
         }

         if (object1 instanceof BooleanSetting booleansetting1) {
            booleansetting1.setVisibilitySupplier(() -> {
               return "Волны".equals(this.modeSetting.getFirst());
            });
         }

         if (object1 instanceof ColorSetting colorsetting1) {
            colorsetting1.setVisibilitySupplier(() -> {
               return "Волны".equals(this.modeSetting.getFirst());
            });
         }
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.ds = this.getPlayer() != null && this.getPlayer().isOnGround();
      this.mB = false;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.jn.clear();
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
      return 0;
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
      boolean flag = this.getPlayer().isOnGround();
      if (this.ds && this.getClient().options.jumpKey.isPressed()) {
         this.mB = true;
      }

      if (this.ds && !flag && this.mB) {
         Vec3d vec3d = this.getPlayer().getPos();
         if ("Частицы".equals(this.modeSetting.getFirst())) {
            ParticleEffects.c(this, vec3d, (int)this.countSetting.getValue(), this.speedSetting.getFloatValue(), 0.95F);
         } else if ("Волны".equals(this.modeSetting.getFirst())) {
            BlockPos blockpos = BlockPos.ofFloored(vec3d.x, vec3d.y - 0.1, vec3d.z);
            this.jn.forEach(JumpWaveParticle::a);
            this.jn.add(new JumpWaveParticle(this, blockpos, System.currentTimeMillis()));
         }
      }

      if (flag) {
         this.mB = false;
      }

      this.ds = flag;
   }

   @Override
   public void onRenderAfterEntities(WorldRenderContext worldrendercontext) {
      if ("Волны".equals(this.modeSetting.getFirst())) {
         this.c(worldrendercontext);
      }
   }

   private void c(WorldRenderContext worldrendercontext) {
      if (!this.jn.isEmpty() && this.getWorld() != null) {
         Iterator iterator = this.jn.iterator();

         while (iterator.hasNext()) {
            JumpWaveParticle JumpWaveParticle = (JumpWaveParticle)iterator.next();
            if (JumpWaveParticle.b()) {
               iterator.remove();
            } else {
               JumpWaveParticle.c(worldrendercontext);
            }
         }
      }
   }
}
