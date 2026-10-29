package dev.mark.system.module.client;

import dev.mark.system.module.Module;

import dev.mark.system.core.ClientMain;
import dev.mark.system.enums.Category;
import net.minecraft.client.MinecraftClient;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.gui.screen.ClickGuiScreen;

public class ClickGuiModule extends Module {
   private final BooleanSetting blurGuiSetting = new BooleanSetting("Blur GUI", "Background blur when ClickGUI is open", true);
   private final BooleanSetting blurCustomInventorySetting = new BooleanSetting("Blur Custom Inv", "Background blur in custom inventory", true);

   public ClickGuiModule() {
      super("ClickGui", "Open/close menu", Category.CLIENT);
      this.keyBindSetting.setDefaultKeyCode(344);
      this.keyBindSetting.setKeyCode(344);
      this.addSettings(this.blurGuiSetting, this.blurCustomInventorySetting);
   }

   public void a() {
      if (this.keyBindSetting.getKeyCode() == -1) {
         this.keyBindSetting.setKeyCode(344);
      }
   }

   @Override
   public void toggle() {
      if (!ClientMain.getInstance().getModuleManager().<PanicModule>getModule(PanicModule.class).c()) {
         MinecraftClient minecraftclient = MinecraftClient.getInstance();
         if (minecraftclient.currentScreen == null) {
            minecraftclient.setScreen(new ClickGuiScreen());
         } else {
            if (minecraftclient.currentScreen instanceof ClickGuiScreen) {
               minecraftclient.setScreen(null);
            }
         }
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public BooleanSetting b() {
      return this.blurGuiSetting;
   }

   public BooleanSetting c() {
      return this.blurCustomInventorySetting;
   }
}
