package dev.mark.system.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import dev.mark.system.module.Module;
import dev.mark.system.module.combat.ABRaidmineModule;
import dev.mark.system.module.combat.AimAssistModule;
import dev.mark.system.module.combat.AnchorTapModule;
import dev.mark.system.module.combat.AntiBotModule;
import dev.mark.system.module.combat.AuraModule;
import dev.mark.system.module.combat.AutoCartModule;
import dev.mark.system.module.combat.CrystalOptModule;
import dev.mark.system.module.combat.CrystalTapModule;
import dev.mark.system.module.combat.HitboxModule;
import dev.mark.system.module.combat.MaceSwapModule;
import dev.mark.system.module.combat.ShieldBreakerModule;
import dev.mark.system.module.combat.TriggerBotModule;
import dev.mark.system.module.combat.AutoTotemModule;
import dev.mark.system.module.movement.AirStuckModule;
import dev.mark.system.module.movement.AutoHealthPotModule;
import dev.mark.system.module.movement.BlinkModule;
import dev.mark.system.module.movement.BoatFlyModule;
import dev.mark.system.module.movement.ElytraSwapModule;
import dev.mark.system.module.movement.EmergencyStopModule;
import dev.mark.system.module.movement.FakeLagModule;
import dev.mark.system.module.movement.FastUseModule;
import dev.mark.system.module.movement.InvMoveModule;
import dev.mark.system.module.movement.JumpResetModule;
import dev.mark.system.module.movement.LagRangeModule;
import dev.mark.system.module.movement.NoJumpDelayModule;
import dev.mark.system.module.movement.NoPushModule;
import dev.mark.system.module.movement.NoSlowModule;
import dev.mark.system.module.movement.NoWebModule;
import dev.mark.system.module.movement.ShiftTapModule;
import dev.mark.system.module.movement.SpeedModule;
import dev.mark.system.module.movement.SpiderModule;
import dev.mark.system.module.movement.SprintModule;
import dev.mark.system.module.movement.WindChargeModule;
import dev.mark.system.module.player.AutoAcceptModule;
import dev.mark.system.module.player.AutoLeaveModule;
import dev.mark.system.module.player.AutoRespawnModule;
import dev.mark.system.module.player.AutoSwapModule;
import dev.mark.system.module.player.AutoToolModule;
import dev.mark.system.module.player.ChestStealerModule;
import dev.mark.system.module.player.FreeCamModule;
import dev.mark.system.module.player.ItemScrollerModule;
import dev.mark.system.module.player.KeyPearlModule;
import dev.mark.system.module.player.NoArmorStandModule;
import dev.mark.system.module.player.NoServerRotateModule;
import dev.mark.system.module.player.PacketModule;
import dev.mark.system.module.render.AspectRatioModule;
import dev.mark.system.module.render.BlockOutlineModule;
import dev.mark.system.module.render.CamTweaksModule;
import dev.mark.system.module.render.CapeModule;
import dev.mark.system.module.render.ChatTweaksModule;
import dev.mark.system.module.render.CustomFogModule;
import dev.mark.system.module.render.CustomTimeModule;
import dev.mark.system.module.render.FakePlayerModule;
import dev.mark.system.module.render.FullBrightModule;
import dev.mark.system.module.render.HandTweaksModule;
import dev.mark.system.module.render.HitboxTweaksModule;
import dev.mark.system.module.render.NoRenderModule;
import dev.mark.system.module.render.ParrotModule;
import dev.mark.system.module.render.ProtestModule;
import dev.mark.system.module.render.SwingAnimationModule;
import dev.mark.system.module.render.TabTweaksModule;
import dev.mark.system.module.render.TargetESPModule;
import dev.mark.system.module.render.ViewModelModule;
import dev.mark.system.module.render.WorldTintModule;
import dev.mark.system.module.render.ZoomModule;
import dev.mark.system.module.visual.BlockESPModule;
import dev.mark.system.module.visual.ItemESPModule;
import dev.mark.system.module.visual.ItemRadiusModule;
import dev.mark.system.module.visual.NameTagsModule;
import dev.mark.system.module.visual.PearlTracerModule;
import dev.mark.system.module.visual.PlayerChamsModule;
import dev.mark.system.module.visual.PlayerESPModule;
import dev.mark.system.module.visual.PlayerOutlinesModule;
import dev.mark.system.module.visual.PlayerScalerModule;
import dev.mark.system.module.visual.ShowInvisibleModule;
import dev.mark.system.module.visual.TracersModule;
import dev.mark.system.module.visual.TrajectoriesModule;
import dev.mark.system.module.visual.WaypointsModule;
import dev.mark.system.module.particles.HitParticlesModule;
import dev.mark.system.module.particles.JumpParticlesModule;
import dev.mark.system.module.particles.ParticleModule;
import dev.mark.system.module.particles.ParticlesModule;
import dev.mark.system.module.particles.PearlParticlesModule;
import dev.mark.system.module.particles.TrailsModule;
import dev.mark.system.module.client.AuctionHelperModule;
import dev.mark.system.module.client.CleanHudModule;
import dev.mark.system.module.client.ClickGuiModule;
import dev.mark.system.module.client.CustomThemeModule;
import dev.mark.system.module.client.HudModule;
import dev.mark.system.module.client.MCFModule;
import dev.mark.system.module.client.PanicModule;
import dev.mark.system.module.client.SoundsModule;
import dev.mark.system.module.client.TranslatorModule;
import dev.mark.system.module.combat.AutoAnchorModule;
import dev.mark.system.module.combat.AutoCrystalModule;
import dev.mark.system.module.combat.WebPlacerModule;
import dev.mark.system.module.movement.BackTrackModule;
import dev.mark.system.module.player.EventsTrackerModule;
import dev.mark.system.module.player.NoFriendDamageModule;
import dev.mark.system.module.player.NoInteractModule;
import dev.mark.system.module.player.SafeModeModule;
import dev.mark.system.module.render.AmbientModule;
import dev.mark.system.module.render.ItemColorModule;
import dev.mark.system.module.render.ShulkerPreviewModule;
import dev.mark.system.module.render.TNTTimerModule;
import dev.mark.system.module.visual.BackToolsModule;
import dev.mark.system.module.visual.CrystalTweaksModule;
import dev.mark.system.module.visual.HeadOrbsModule;
import dev.mark.system.module.visual.HitColorModule;
import dev.mark.system.module.visual.KillEffectModule;
import dev.mark.system.module.visual.NameProtectModule;
import dev.mark.system.module.visual.SwordBlockModule;

public class ModuleRegistry {
   private static final Map<Integer, Supplier<Module>> registry = new LinkedHashMap<Integer, Supplier<Module>>();

   private static void register(int i, Supplier<Module> supplier) {
      registry.put(i, supplier);
   }

   public static Module createModule(int i) {
      Supplier supplier = registry.get(i);
      return supplier != null ? (Module)supplier.get() : null;
   }

   public static List<Module> createAllModules() {
      ArrayList arraylist = new ArrayList();

      for (Supplier supplier : registry.values()) {
         try {
            arraylist.add((Module)supplier.get());
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }

      return arraylist;
   }

   public static Set<Integer> parseModuleIds(String s) {
      HashSet hashset = new HashSet();
      if (s != null && !s.isEmpty()) {
         String[] astring = s.split(",");

         for (String s1 : astring) {
            try {
               int i = Integer.parseInt(s1.trim());
               if (registry.containsKey(i)) {
                  hashset.add(i);
               }
            } catch (NumberFormatException numberformatexception) {
            }
         }

         return hashset;
      } else {
         return hashset;
      }
   }

   public static List<Module> createModules(Set<Integer> set) {
      ArrayList arraylist = new ArrayList();

      for (Integer integer : set) {
         Module module = createModule(integer);
         if (module != null) {
            arraylist.add(module);
         }
      }

      return arraylist;
   }

   static {
      register(1, HudModule::new);
      register(2, SoundsModule::new);
      register(3, MCFModule::new);
      register(4, PanicModule::new);
      register(5, CleanHudModule::new);
      register(69, CustomThemeModule::new);
      register(6, TranslatorModule::new);
      register(7, ClickGuiModule::new);
      register(10, AuraModule::new);
      register(11, AimAssistModule::new);
      register(12, TriggerBotModule::new);
      register(14, HitboxModule::new);
      register(13, AnchorTapModule::new);
      register(15, CrystalTapModule::new);
      register(16, ShieldBreakerModule::new);
      register(17, AutoTotemModule::new);
      register(18, MaceSwapModule::new);
      register(19, AntiBotModule::new);
      register(100, AutoCartModule::new);
      register(21, InvMoveModule::new);
      register(22, ElytraSwapModule::new);
      register(23, EmergencyStopModule::new);
      register(24, KeyPearlModule::new);
      register(25, FreeCamModule::new);
      register(26, NoJumpDelayModule::new);
      register(27, JumpResetModule::new);
      register(28, SprintModule::new);
      register(29, WindChargeModule::new);
      register(30, AirStuckModule::new);
      register(31, NoWebModule::new);
      register(32, SpiderModule::new);
      register(33, SpeedModule::new);
      register(34, BoatFlyModule::new);
      register(35, FakeLagModule::new);
      register(36, LagRangeModule::new);
      register(37, BlinkModule::new);
      register(38, NoSlowModule::new);
      register(85, ShiftTapModule::new);
      register(99, AuctionHelperModule::new);
      register(40, NoPushModule::new);
      register(41, FastUseModule::new);
      register(42, AutoHealthPotModule::new);
      register(43, CrystalOptModule::new);
      register(44, AutoRespawnModule::new);
      register(45, NoArmorStandModule::new);
      register(46, AutoToolModule::new);
      register(47, AutoSwapModule::new);
      register(48, ABRaidmineModule::new);
      register(49, AutoLeaveModule::new);
      register(83, ItemScrollerModule::new);
      register(95, AutoAcceptModule::new);
      register(97, NoServerRotateModule::new);
      register(98, ChestStealerModule::new);
      register(50, NoRenderModule::new);
      register(51, WorldTintModule::new);
      register(52, CustomTimeModule::new);
      register(53, CustomFogModule::new);
      register(54, TargetESPModule::new);
      register(55, CamTweaksModule::new);
      register(56, HitboxTweaksModule::new);
      register(57, HandTweaksModule::new);
      register(58, ChatTweaksModule::new);
      register(59, TabTweaksModule::new);
      register(60, ViewModelModule::new);
      register(61, SwingAnimationModule::new);
      register(62, AspectRatioModule::new);
      register(63, CapeModule::new);
      register(64, ProtestModule::new);
      register(65, FakePlayerModule::new);
      register(66, ParrotModule::new);
      register(67, FullBrightModule::new);
      register(68, ZoomModule::new);
      register(94, BlockOutlineModule::new);
      register(70, NameTagsModule::new);
      register(71, PlayerESPModule::new);
      register(72, PlayerChamsModule::new);
      register(73, TracersModule::new);
      register(74, ItemESPModule::new);
      register(75, BlockESPModule::new);
      register(76, PearlTracerModule::new);
      register(77, ShowInvisibleModule::new);
      register(78, WaypointsModule::new);
      register(79, PlayerScalerModule::new);
      register(80, PlayerOutlinesModule::new);
      register(81, TrajectoriesModule::new);
      register(82, ItemRadiusModule::new);
      register(90, ParticlesModule::new);
      register(91, JumpParticlesModule::new);
      register(92, HitParticlesModule::new);
      register(93, PearlParticlesModule::new);
      register(96, TrailsModule::new);
      register(101, AutoAnchorModule::new);
      register(102, AutoCrystalModule::new);
      register(103, WebPlacerModule::new);
      register(104, BackTrackModule::new);
      register(105, EventsTrackerModule::new);
      register(106, NoFriendDamageModule::new);
      register(107, NoInteractModule::new);
      register(108, SafeModeModule::new);
      register(109, AmbientModule::new);
      register(110, ItemColorModule::new);
      register(111, ShulkerPreviewModule::new);
      register(112, TNTTimerModule::new);
      register(113, BackToolsModule::new);
      register(114, CrystalTweaksModule::new);
      register(115, HeadOrbsModule::new);
      register(116, HitColorModule::new);
      register(117, KillEffectModule::new);
      register(118, NameProtectModule::new);
      register(119, SwordBlockModule::new);
                                                                                                                                                                                       }
}
