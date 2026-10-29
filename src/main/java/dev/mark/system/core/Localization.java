package dev.mark.system.core;

import dev.mark.system.enums.Language;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.mark.system.data.SmallStateData;
public class Localization {
   private static final Localization INSTANCE = new Localization();
   private volatile Language Hz = Language.RUSSIAN;
   private final Map<String, String> azN = new ConcurrentHashMap<String, String>();
   private final List<Runnable> uY = new CopyOnWriteArrayList<Runnable>();
   private volatile boolean akT = false;

   private Localization() {
   }

   public static Localization a() {
      return INSTANCE;
   }

   public void b() {
      try {
         AuthApiClient AuthApiClient = new AuthApiClient(AuthConfig.getHost(), AuthConfig.getPort());
         Map map = AuthApiClient.a("en");
         if (map != null && !map.isEmpty()) {
            this.azN.clear();
            this.azN.putAll(map);
            this.akT = true;
         }
      } catch (Exception exception) {
         PrintStream printstream = System.err;
         String s = exception.getMessage();
         printstream.println("Failed to load translations: " + s);
      }
   }

   public String c(String s) {
      if (s == null || s.isEmpty()) {
         return s;
      } else {
         String translation = this.azN.getOrDefault(s, null);
         if (translation != null) {
            return translation;
         }
         
         if (this.Hz == Language.ENGLISH) {

            return switch (s) {
               case "Сомбат" -> "Combat";
               case "Мовемент" -> "Movement";
               case "Плайер" -> "Player";
               case "Рендер" -> "Render";
               case "Визуал" -> "Visual";
               case "Слиент" -> "Client";

               case "Клавиша включения" -> "Toggle Key";
               case "Здесь будет список друзей" -> "Friends list will be here";
               case "Здесь пока нет избранных модулей" -> "No favorite modules yet";

               case "Аура" -> "Aura";
               case "АимАссист" -> "AimAssist";
               case "ТриггерБот" -> "TriggerBot";
               case "ЗхиелдБреакер" -> "ShieldBreaker";
               case "ШифтТап" -> "ShiftTap";
               case "АнкорТап" -> "AnchorTap";

               case "Блинк" -> "Blink";
               case "БоатФлй" -> "BoatFly";
               case "ЕлйтраЗшап" -> "ElytraSwap";
               case "ФрееКам" -> "FreeCam";
               case "ИнвМове" -> "InvMove";
               case "НоДжампДелай" -> "NoJumpDelay";
               case "НоПузх" -> "NoPush";
               case "НоЗлош" -> "NoSlow";
               case "НоШеб" -> "NoWeb";
               case "Спид" -> "Speed";
               case "Спайдер" -> "Spider";
               case "Спринт" -> "Sprint";
               case "ВиндЧардж" -> "WindCharge";

               case "АутоАксепт" -> "AutoAccept";
               case "АутоКарт" -> "AutoCart";
               case "АутоХелсПот" -> "AutoHealthPot";
               case "АутоЛив" -> "AutoLeave";
               case "АутоРеспавн" -> "AutoRespawn";
               case "АутоСвап" -> "AutoSwap";
               case "АутоТул" -> "AutoTool";
               case "АутоТотем" -> "AutoTotem";
               case "ЧестСтилер" -> "ChestStealer";
               case "ЕмердженсиСтоп" -> "EmergencyStop";
               case "ФастЮз" -> "FastUse";
               case "ФейкЛаг" -> "FakeLag";
               case "ИтемСкроллер" -> "ItemScroller";
               case "КейПерл" -> "KeyPearl";
               case "ЛагРейндж" -> "LagRange";
               case "МейсСвап" -> "MaceSwap";
               case "МСФ" -> "MCF";
               case "НоАрморСтенд" -> "NoArmorStand";
               case "НоЗерверРотате" -> "NoServerRotate";
               case "Пакет" -> "Packet";

               case "БлокЕСП" -> "BlockESP";
               case "БлокАутлайн" -> "BlockOutline";
               case "КристалОпт" -> "CrystalOpt";
               case "КристалТап" -> "CrystalTap";
               case "ХитбоксТвикс" -> "HitboxTweaks";
               case "ИтемЕСП" -> "ItemESP";
               case "ИтемРадиус" -> "ItemRadius";
               case "НеймТагс" -> "NameTags";
               case "ПеарлТрасер" -> "PearlTracer";
               case "ПлайерСхамз" -> "PlayerChams";
               case "ПлайерЕЗП" -> "PlayerESP";
               case "ПлайерОутлинез" -> "PlayerOutlines";
               case "ПлайерЗсалер" -> "PlayerScaler";
               case "ШоуИнвизибл" -> "ShowInvisible";
               case "ТаргетЕСП" -> "TargetESP";
               case "Трейсерс" -> "Tracers";
               case "Траджекторис" -> "Trajectories";
               case "Шайпоинтз" -> "Waypoints";
               case "ДжампРесет" -> "JumpReset";

               case "АспектРейшо" -> "AspectRatio";
               case "КамТвикс" -> "CamTweaks";
               case "Кейп" -> "Cape";
               case "ЧатТвикс" -> "ChatTweaks";
               case "КлинХуд" -> "CleanHud";
               case "КастомФог" -> "CustomFog";
               case "КастомТайм" -> "CustomTime";
               case "ФейкПлейер" -> "FakePlayer";
               case "ФуллБрайт" -> "FullBright";
               case "ХендТвикс" -> "HandTweaks";
               case "Хитбокс" -> "Hitbox";
               case "Худ" -> "Hud";
               case "НоРендер" -> "NoRender";
               case "Паррот" -> "Parrot";
               case "Протест" -> "Protest";
               case "СвингАнимейшн" -> "SwingAnimation";
               case "ТабТвикс" -> "TabTweaks";
               case "ВьюМодел" -> "ViewModel";
               case "ВорлдТинт" -> "WorldTint";
               case "Зоом" -> "Zoom";

               case "АБ Raidmine" -> "AB Raidmine";
               case "АнтиБот" -> "AntiBot";
               case "АукшнХелпер" -> "AuctionHelper";
               case "КликГуи" -> "ClickGui";
               case "КастомТема" -> "CustomTheme";
               case "Панис" -> "Panic";
               case "Саундс" -> "Sounds";
               case "Транзлатор" -> "Translator";
               case "АирСтак" -> "AirStuck";

               case "ХитПартиклс" -> "HitParticles";
               case "ДжампПартиклс" -> "JumpParticles";
               case "Партикл" -> "Particle";
               case "Партислез" -> "Particles";
               case "ПеарлПартислез" -> "PearlParticles";
               case "Трейлс" -> "Trails";
               
               default -> s;
            };
         }
         return s;
      }
   }

   public void d() {
      this.Hz = this.Hz.next();
      this.f();
   }

   public void e(Language language) {
      if (this.Hz != language) {
         this.Hz = language;
         this.f();
      }
   }

   private void f() {
      for (Runnable runnable : this.uY) {
         try {
            runnable.run();
         } catch (Exception exception) {
            PrintStream printstream = System.err;
            String s = exception.getMessage();
            printstream.println("er 1 : " + s);
         }
      }
   }

   public Language g() {
      return this.Hz;
   }

   public void h(Runnable runnable) {
      if (runnable != null) {
         this.uY.add(runnable);
      }
   }

   public void i(Runnable runnable) {
      if (runnable != null) {
         this.uY.remove(runnable);
      }
   }
}
