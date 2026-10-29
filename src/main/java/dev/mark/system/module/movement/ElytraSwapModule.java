package dev.mark.system.module.movement;

import dev.mark.system.module.Module;

import dev.mark.system.enums.Category;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import dev.mark.system.setting.ActionKeySetting;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;

public class ElytraSwapModule extends Module {
   private static long alu = 5000L;
   private ListSetting modeSetting = new ListSetting("Mode", "1", List.<String>of("Normal", "Hotbar"), List.<String>of("Normal"), false);
   private BooleanSetting openInventorySetting = new BooleanSetting("Open Inventory", "Open inventory during swap", true);
   private SliderSetting closeInventoryDelaySetting = new SliderSetting(
      "Close Inv. Delay", "Delay before closing inventory (ms)", 50.0, 0.0, 200.0, 10.0
   );
   private SliderSetting swapDelaySetting = new SliderSetting("Swap Delay", "Delay between actions (ms)", 60.0, 10.0, 200.0, 5.0);
   private SliderSetting fireworkDelaySetting = new SliderSetting(
      "Firework Delay", "Delay before launching firework (not in hotbar) (ms)", 100.0, 50.0, 500.0, 25.0
   );
   private SliderSetting useDelaySetting = new SliderSetting("Use Delay", "Delay before using firework (ms)", 50.0, 0.0, 200.0, 10.0);
   private ActionKeySetting swapKeySetting = new ActionKeySetting("Swap Key", "Key for elytra swap", 71, this::i);
   private ActionKeySetting fireworkKeySetting = new ActionKeySetting("Firework Key", "Key for launching firework", 70, this::q);
   private final ScheduledExecutorService EU = Executors.newSingleThreadScheduledExecutor();
   private boolean mu = false;
   private long al = 0L;
   private boolean ow = false;

   public ElytraSwapModule() {
      super("ElytraSwap", "Elytra switching via bind", Category.MOVEMENT);
      this.openInventorySetting.setVisibilitySupplier(() -> {
         return "Normal".equals(this.modeSetting.getFirst());
      });
      this.closeInventoryDelaySetting.setVisibilitySupplier(() -> {
         return "Normal".equals(this.modeSetting.getFirst()) && this.openInventorySetting.getValue();
      });
      this.addSettings(
         this.modeSetting,
         this.openInventorySetting,
         this.closeInventoryDelaySetting,
         this.swapDelaySetting,
         this.fireworkDelaySetting,
         this.useDelaySetting,
         this.swapKeySetting,
         this.fireworkKeySetting
      );
   }

   @Override
   public void onEnable() {
      this.a();
   }

   @Override
   public void onDisable() {
      this.a();
   }

   private void a() {
      this.mu = false;
      this.al = 0L;
      this.ow = false;
   }

   private boolean b() {
      return this.mu || System.currentTimeMillis() - this.al < this.swapDelaySetting.getLongValue();
   }

   private void c() {
      this.mu = true;
      this.al = System.currentTimeMillis();
      this.e(() -> {
         if (this.mu) {
            this.mu = false;
         }
      }, alu);
   }

   private void d() {
      this.mu = false;
   }

   private void e(Runnable runnable, long i) {
      this.EU.schedule(() -> {
         this.getClient().execute(runnable);
      }, i, TimeUnit.MILLISECONDS);
   }

   private void f() {
      if (this.openInventorySetting.getValue()) {
         this.ow = this.getScreen() instanceof InventoryScreen;
         if (!this.ow) {
            this.EU.schedule(() -> {
               this.getClient().execute(() -> {
                  this.getClient().setScreen(new InventoryScreen(this.getPlayer()));
               });
            }, 0L, TimeUnit.MILLISECONDS);
         }
      }
   }

   private void h() {
      if (this.openInventorySetting.getValue() && !this.ow) {
         this.e(() -> {
            if (this.getScreen() instanceof InventoryScreen) {
               this.getScreen().close();
            }

            this.d();
         }, this.closeInventoryDelaySetting.getLongValue());
      } else {
         this.d();
      }
   }

   private void i() {
      if (!this.b()) {
         if (!this.isNotInWorld()) {
            String s = this.modeSetting.getFirst();
            switch (s) {
               case "Normal":
                  this.o();
                  return;
               case "Hotbar":
                  this.p();
            }
         }
      }
   }

   private void o() {
      ItemStack itemstack = this.getPlayer().getEquippedStack(EquipmentSlot.CHEST);
      boolean flag = !itemstack.isEmpty() && Registries.ITEM.getId(itemstack.getItem()).equals(Identifier.of("minecraft", "elytra"));
      int i;
      if (flag) {
         i = this.t();
      } else {
         i = this.r();
      }

      if (i != -1) {
         int j = i;
         this.c();
         this.f();
         this.e(() -> {
            try {
               this.equipArmor(j);
               this.h();
            } catch (Exception exception) {
               this.d();
            }
         }, this.swapDelaySetting.getLongValue());
      }
   }

   private void p() {
      ItemStack itemstack = this.getPlayer().getEquippedStack(EquipmentSlot.CHEST);
      boolean flag = !itemstack.isEmpty() && Registries.ITEM.getId(itemstack.getItem()).equals(Identifier.of("minecraft", "elytra"));
      int i;
      if (flag) {
         i = this.u();
      } else {
         i = this.s();
      }

      if (i != -1) {
         int j = i;
         this.c();
         int k = this.getInventory().selectedSlot;
         this.e(() -> {
            try {
               this.getInventory().selectedSlot = j;
               this.interactItem(this.getMainHand());
               this.e(() -> {
                  try {
                     this.getInventory().selectedSlot = k;
                  } finally {
                     this.d();
                  }
               }, this.swapDelaySetting.getLongValue());
            } catch (Exception exception) {
               this.d();
            }
         }, this.swapDelaySetting.getLongValue());
      }
   }

   private void q() {
      if (!this.b()) {
         if (!this.isNotInWorld()) {
            ItemStack itemstack = this.getPlayer().getEquippedStack(EquipmentSlot.CHEST);
            boolean flag = !itemstack.isEmpty() && Registries.ITEM.getId(itemstack.getItem()).equals(Identifier.of("minecraft", "elytra"));
            if (flag) {
               int i = this.v();
               if (i != -1) {
                  int j = this.getInventory().selectedSlot;
                  boolean flag1 = i < 9;
                  this.c();
                  if (flag1) {
                     this.e(() -> {
                        try {
                           this.getInventory().selectedSlot = i;
                           this.e(() -> {
                              try {
                                 this.interactItem(this.getMainHand());
                                 this.e(() -> {
                                    try {
                                       this.getInventory().selectedSlot = j;
                                    } finally {
                                       this.d();
                                    }
                                 }, this.swapDelaySetting.getLongValue());
                              } catch (Exception exception1) {
                                 this.d();
                              }
                           }, this.useDelaySetting.getLongValue());
                        } catch (Exception exception) {
                           this.d();
                        }
                     }, 0L);
                  } else {
                     this.e(() -> {
                        try {
                           this.swapSlots(i, 8);
                           this.e(() -> {
                              try {
                                 this.getInventory().selectedSlot = 8;
                                 this.e(() -> {
                                    try {
                                       this.interactItem(this.getMainHand());
                                       this.e(() -> {
                                          try {
                                             this.getInventory().selectedSlot = j;
                                             this.swapSlots(i, 8);
                                          } finally {
                                             this.d();
                                          }
                                       }, this.swapDelaySetting.getLongValue());
                                    } catch (Exception exception2) {
                                       this.d();
                                    }
                                 }, this.useDelaySetting.getLongValue());
                              } catch (Exception exception1) {
                                 this.d();
                              }
                           }, this.fireworkDelaySetting.getLongValue());
                        } catch (Exception exception) {
                           this.d();
                        }
                     }, 0L);
                  }
               }
            }
         }
      }
   }

   private int r() {
      for (int i = 0; i < this.getPlayer().getInventory().size(); i++) {
         if (this.getPlayer().getInventory().getStack(i).getItem() == Items.ELYTRA) {
            return i;
         }
      }

      return -1;
   }

   private int s() {
      for (int i = 0; i < 9; i++) {
         if (this.getPlayer().getInventory().getStack(i).getItem() == Items.ELYTRA) {
            return i;
         }
      }

      return -1;
   }

   private int t() {
      for (int i = 0; i < this.getPlayer().getInventory().size(); i++) {
         ItemStack itemstack = this.getPlayer().getInventory().getStack(i);
         EquippableComponent equippablecomponent = (EquippableComponent)itemstack.get(DataComponentTypes.EQUIPPABLE);
         if (equippablecomponent != null && equippablecomponent.slot() == EquipmentSlot.CHEST) {
            return i;
         }
      }

      return -1;
   }

   private int u() {
      for (int i = 0; i < 9; i++) {
         ItemStack itemstack = this.getPlayer().getInventory().getStack(i);
         EquippableComponent equippablecomponent = (EquippableComponent)itemstack.get(DataComponentTypes.EQUIPPABLE);
         if (equippablecomponent != null && equippablecomponent.slot() == EquipmentSlot.CHEST) {
            return i;
         }
      }

      return -1;
   }

   private int v() {
      for (int i = 0; i < this.getPlayer().getInventory().size(); i++) {
         if (this.getPlayer().getInventory().getStack(i).getItem() == Items.FIREWORK_ROCKET) {
            return i;
         }
      }

      return -1;
   }
}
