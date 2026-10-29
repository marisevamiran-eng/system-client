package dev.mark.system.module.client;

import dev.mark.system.module.Module;

import dev.mark.system.core.ClientMain;
import dev.mark.system.core.KeyBindManager;
import dev.mark.system.enums.Category;
import dev.mark.system.setting.ActionKeySetting;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.FilePickerSetting;
import dev.mark.system.core.NativeBootstrap;
import dev.mark.system.core.NativeBootstrapAlt;
import dev.mark.system.core.NativeGuard;
import dev.mark.system.core.NativeGuardAlt;
import dev.mark.system.util.StringValidationNative;

public class PanicModule extends Module {
   private boolean anh = false;
   ClientMain NH = ClientMain.getInstance();
   private BooleanSetting clearLoaderSetting = new BooleanSetting("Очистить лоадер", "Очистить лоадер при панике", false);
   private FilePickerSetting Zd = new FilePickerSetting("Путь к лоадеру", "Путь к лоадеру");
   private BooleanSetting clearTracesSetting = new BooleanSetting("Очищать", "Очищать следы при панике", false);
   private BooleanSetting clearRecentSetting = new BooleanSetting("Очищать рецент", "Очищать недавние файлы", true);
   private BooleanSetting clearRegistrySetting = new BooleanSetting("Очищать реестр", "Очищать следы в реестре", true);
   private BooleanSetting clearTempSetting = new BooleanSetting("Очищать темп", "Очищать временные файлы", true);
   private ActionKeySetting restoreModulesKeySetting = new ActionKeySetting(
      "Восстановление модулей", "Бинд для восстановления модулей после паники", -1, this::b
   );
   private boolean P = false;

   public PanicModule() {
      super("Паник", "Отключает все функции до перезапуска", Category.CLIENT);
      this.addSettings(
         this.restoreModulesKeySetting,
         this.clearLoaderSetting,
         this.Zd,
         this.clearTracesSetting,
         this.clearRecentSetting,
         this.clearRegistrySetting,
         this.clearTempSetting
      );
      this.U(this.clearTracesSetting.getName(), this.clearRecentSetting.getName(), true);
      this.U(this.clearTracesSetting.getName(), this.clearRegistrySetting.getName(), true);
      this.U(this.clearTracesSetting.getName(), this.clearTempSetting.getName(), true);
      this.U(this.clearLoaderSetting.getName(), this.Zd.getName(), true);
      KeyBindManager.getInstance().d(this.restoreModulesKeySetting);
   }

   @Override
   public void onEnable() {
      this.anh = true;
      if (this.clearTracesSetting.getValue()) {
         if (this.clearRecentSetting.getValue()) {
            NativeBootstrapAlt.a();
         }

         if (this.clearRegistrySetting.getValue()) {
            NativeBootstrap.a();
         }

         if (this.clearTempSetting.getValue()) {
         }
      }

      if (this.clearLoaderSetting.getValue() && this.Zd.hasValue()) {
         String s = this.Zd.getValue();
         this.P = StringValidationNative.a(s);
         NativeGuardAlt.a(s);
         if (this.P) {
            StringValidationNative.c(s);
         }
      }

      try {
         this.NH.getConfigSyncManager().g();
      } catch (Exception exception) {
      } finally {
         this.NH.getConfigSyncManager().a();
      }

      this.getClient().execute(() -> {
         this.getClient().setScreen(null);
      });
      new Thread(this::a, "L").start();
      System.gc();
   }

   @Override
   public void onDisable() {
   }

   private void a() {
      try {
         Thread.sleep(1000L);
         return;
      } catch (Exception exception) {
      } finally {
         this.NH.getModuleManager().clearModules();
      }
   }

   private void b() {
      if (this.anh) {
         new Thread(() -> {
            try {
               if (this.P) {
                  NativeGuard.a();
                  this.P = false;
               }

               this.NH.getConfigSyncManager().b();
               this.NH.modulesLoaded = false;
               this.NH.getModuleManager().init();
               this.anh = false;
            } catch (Exception exception) {
            }
         }, "OC").start();
      }
   }

   public boolean c() {
      return this.anh;
   }
}
