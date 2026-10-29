package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import dev.mark.system.setting.ActionKeySetting;
import dev.mark.system.setting.BooleanSetting;

public class WindChargeModule extends Module {
   private ActionKeySetting throwKeySetting = new ActionKeySetting("Throw Key", "Key for throwing wind charge", 66, this::a);
   private BooleanSetting switchToMaceSetting = new BooleanSetting("Switch to Mace", "Automatically switch to mace after throwing", false);

   public WindChargeModule() {
      super("WindCharge", "Automatic wind charge throw", Category.MOVEMENT);
      this.addSettings(this.throwKeySetting, this.switchToMaceSetting);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   private void a() {
      if (this.hasPlayerAndWorld()) {
         int i = this.getInventory().selectedSlot;
         int j = this.b(Items.WIND_CHARGE);
         int k = this.b(Items.MACE);
         if (j != -1) {
            float f = this.getPlayer().getPitch();
            this.getInventory().selectedSlot = j;
            this.getPlayer().setPitch(90.0F);
            this.getOptions().jumpKey.setPressed(true);
            this.interactItem(this.getMainHand());
            this.getPlayer().setPitch(f);
            if (this.switchToMaceSetting.getValue() && k != -1) {
               this.getInventory().selectedSlot = k;
            } else {
               this.getInventory().selectedSlot = i;
            }

            this.getOptions().jumpKey.setPressed(false);
         }
      }
   }

   private int b(Item item) {
      for (int i = 0; i < 9; i++) {
         if (this.getInventory().getStack(i).getItem() == item) {
            return i;
         }
      }

      return -1;
   }
}
