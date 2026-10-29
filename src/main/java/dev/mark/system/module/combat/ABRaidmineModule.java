package dev.mark.system.module.combat;

import dev.mark.system.module.Module;

import dev.mark.system.module.util.RaidminePresetManager;
import dev.mark.system.data.ItemCollection;
import dev.mark.system.command.ABCommand;
import dev.mark.system.data.ABItem;
import dev.mark.system.enums.Category;
import dev.mark.system.event.ChatMessageEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import dev.mark.system.setting.ActionKeySetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.core.ApiResponse;
import dev.mark.system.data.StatEntry;
import dev.mark.system.gui.component.TabButton;
import dev.mark.system.render.CornerBracketRenderer;

public class ABRaidmineModule extends Module {
   private SliderSetting refreshDelaySetting = new SliderSetting("Refresh Delay", "Delay between refreshes (ms)", 1000.0, 500.0, 5000.0, 100.0);
   private SliderSetting buyDelaySetting = new SliderSetting("Buy Delay", "Delay before confirm click (ms)", 300.0, 100.0, 1000.0, 50.0);
   private SliderSetting scanDelaySetting = new SliderSetting(
      "Scan Delay", "Delay after refresh before scanning (ms)", 600.0, 300.0, 1500.0, 50.0
   );
   private ActionKeySetting viewKeySetting = new ActionKeySetting("View Key", "Hover over item and press to view name", 66, () -> {
   });
   private ScheduledExecutorService Xe;
   private boolean Nv = false;
   private boolean KQ = false;
   private boolean YQ = false;
   private ItemCollection axj;
   private RaidminePresetManager kZ;
   private ABCommand abO;
   private static final int fN = 0;
   private static final int axC = 44;

   public ABRaidmineModule() {
      super("AB Raidmine", "Automatic auction item purchasing", Category.PLAYER);
      this.addSettings(this.refreshDelaySetting, this.scanDelaySetting, this.buyDelaySetting, this.viewKeySetting);
      this.axj = new ItemCollection();
      this.kZ = new RaidminePresetManager(this);
      this.abO = new ABCommand(this);
   }

   @Override
   public void onEnable() {
      if (this.isNotInWorld()) {
         this.setEnabled(false);
      } else {
         this.Nv = true;
         this.KQ = false;
         if (this.axj == null || this.axj.c() == null) {
            this.axj = new ItemCollection();
         }

         int i = this.axj.c().size();
         this.sendChatMessage("§7Loaded items: §f" + i);
         this.sendChatMessage("§7Scan range: slots 0-44");
         this.Xe = Executors.newSingleThreadScheduledExecutor();
         this.d();
         this.Xe.scheduleAtFixedRate(() -> {
            if (this.Nv && !this.isNotInWorld()) {
               this.getClient().execute(this::f);
            }
         }, this.refreshDelaySetting.getLongValue(), this.refreshDelaySetting.getLongValue(), TimeUnit.MILLISECONDS);
      }
   }

   @Override
   public void onDisable() {
      this.Nv = false;
      this.KQ = false;
      this.YQ = false;
      if (this.Xe != null && !this.Xe.isShutdown()) {
         this.Xe.shutdown();

         try {
            if (!this.Xe.awaitTermination(1L, TimeUnit.SECONDS)) {
               this.Xe.shutdownNow();
            }

            return;
         } catch (InterruptedException interruptedexception) {
            this.Xe.shutdownNow();
            Thread.currentThread().interrupt();
         }
      }
   }

   @Override
   public void onChatMessage(ChatMessageEvent chatmessageevent) {
      this.abO.handleMessage(chatmessageevent);
   }

   @Override
   public void onEndTick() {
      if (!this.isNotInWorld()) {
         int i = this.viewKeySetting.getKeyCode();
         boolean flag = this.a(i);
         if (flag && !this.YQ) {
            this.b();
         }

         this.YQ = flag;
      }
   }

   private boolean a(int i) {
      long j = this.getClient().getWindow().getHandle();
      return GLFW.glfwGetKey(j, i) == 1;
   }

   private void b() {
      if (!this.isNotInWorld()) {
         if (this.getScreen() instanceof HandledScreen handledscreen) {
            try {
               Field field = HandledScreen.class.getDeclaredField("focusedSlot");
               field.setAccessible(true);
               Slot slot = (Slot)field.get(handledscreen);
               if (slot != null && !slot.getStack().isEmpty()) {
                  this.c(slot.getStack());
               } else {
                  this.sendChatMessage("§cHover cursor over an item!");
               }
            } catch (Exception exception) {
               String s = exception.getMessage();
               this.sendChatMessage("§cError: " + s);
            }
         } else {
            this.sendChatMessage("§cOpen GUI with items!");
         }
      }
   }

   private void c(ItemStack itemstack) {
      if (!itemstack.isEmpty()) {
         String s = this.l(itemstack);
         String s1 = itemstack.getItem().toString();
         this.sendChatMessage("§6§l=== ITEM INFO ===");
         this.sendChatMessage("§7ID: §f" + s1);
         this.sendChatMessage("§7Name: §f" + s);
         int i = itemstack.getCount();
         this.sendChatMessage("§7Amount: §f" + i);
      }
   }

   private void d() {
      this.getClient().execute(() -> {
         if ((this.getScreen() == null || !this.e()) && this.getNetworkHandler() != null) {
            this.getNetworkHandler().sendChatCommand("ah");
            this.sendChatMessage("§7Opening auction...");
         }
      });
   }

   private boolean e() {
      if (!(this.getScreen() instanceof HandledScreen handledscreen)) {
         return false;
      } else {
         ScreenHandler screenhandler = handledscreen.getScreenHandler();

         for (Slot slot : screenhandler.slots) {
            ItemStack itemstack = slot.getStack();
            if (itemstack.getItem() == Items.NETHER_STAR) {
               String s = this.l(itemstack);
               if (s.contains("Refresh") || s.contains("refresh")) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private void f() {
      if (!this.KQ) {
         if (this.getScreen() instanceof HandledScreen handledscreen) {
            ScreenHandler screenhandler = handledscreen.getScreenHandler();
            int i = this.g(screenhandler);
            if (i != -1) {
               this.clickSlot(i);
               this.Xe.schedule(() -> {
                  this.getClient().execute(this::h);
               }, this.scanDelaySetting.getLongValue(), TimeUnit.MILLISECONDS);
            }
         }
      }
   }

   private int g(ScreenHandler screenhandler) {
      for (int i = 0; i < screenhandler.slots.size(); i++) {
         Slot slot = (Slot)screenhandler.slots.get(i);
         ItemStack itemstack = slot.getStack();
         if (itemstack.getItem() == Items.NETHER_STAR) {
            String s = this.l(itemstack);
            if (s.contains("Refresh") || s.contains("refresh")) {
               return slot.id;
            }
         }
      }

      return -1;
   }

   private void h() {
      if (!this.KQ) {
         if (this.getScreen() instanceof HandledScreen handledscreen) {
            ScreenHandler screenhandler = handledscreen.getScreenHandler();
            if (!screenhandler.slots.isEmpty()) {
               ArrayList arraylist = new ArrayList();

               for (int i = 0; i <= Math.min(44, screenhandler.slots.size() - 1); i++) {
                  Slot slot = (Slot)screenhandler.slots.get(i);
                  ItemStack itemstack = slot.getStack();
                  if (!itemstack.isEmpty()
                     && itemstack.getItem() != Items.NETHER_STAR
                     && itemstack.getItem() != Items.BARRIER
                     && itemstack.getItem() != Items.GRAY_STAINED_GLASS_PANE
                     && itemstack.getItem() != Items.BLACK_STAINED_GLASS_PANE) {
                     if (itemstack.contains(DataComponentTypes.CUSTOM_NAME)) {
                        Text text = (Text)itemstack.get(DataComponentTypes.CUSTOM_NAME);
                        if (text != null && this.i(text)) {
                           continue;
                        }
                     }

                     String s4 = this.l(itemstack);
                     String s = this.k(s4);
                     Double d0 = this.m(itemstack);
                     if (d0 != null) {
                        ABItem abitem = this.axj.a(s);
                        if (abitem != null && d0 <= abitem.getMaxPrice()) {
                           arraylist.add(new StatEntry(slot.id, s4, s, d0, abitem.getMaxPrice()));
                        }
                     }
                  }
               }

               if (!arraylist.isEmpty()) {
                  arraylist.sort(Comparator.<StatEntry>comparingDouble(entry -> {
                     return entry.auH / entry.tp;
                  }));
                  StatEntry bestEntry = (StatEntry)arraylist.get(0);
                  double d1 = (1.0 - bestEntry.auH / bestEntry.tp) * 100.0;
                  this.sendChatMessage("§6§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                  this.sendChatMessage("§a§l  SUITABLE ITEM FOUND!");
                  this.sendChatMessage("§6§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                  String s1 = bestEntry.ast;
                  this.sendChatMessage("§7Name: §f" + s1);
                  int j = bestEntry.mK;
                  this.sendChatMessage("§7Slot: §e#" + j);
                  String s2 = this.j(bestEntry.auH);
                  this.sendChatMessage("§7Price per unit: §e" + s2);
                  String s3 = this.j(bestEntry.tp);
                  this.sendChatMessage("§7Max price: §e" + s3);
                  this.sendChatMessage(String.format("§7Profitability: §a%.1f%%", d1));
                  this.sendChatMessage("§6§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                  this.KQ = true;
                  this.n(bestEntry.mK);
               }
            }
         }
      }
   }

   private boolean i(Text text) {
      return text.getStyle().isItalic();
   }

   private String j(double d0) {
      if (d0 >= 1000000.0) {
         return String.format("%.1fM", d0 / 1000000.0);
      } else {
         return d0 >= 1000.0 ? String.format("%.1fK", d0 / 1000.0) : String.format("%.0f", d0);
      }
   }

   private String k(String s) {
      String s1 = s.replaceAll("§.", "");
      return s1.trim();
   }

   private String l(ItemStack itemstack) {
      if (itemstack.contains(DataComponentTypes.CUSTOM_NAME)) {
         Text text = (Text)itemstack.get(DataComponentTypes.CUSTOM_NAME);
         if (text != null) {
            return text.getString();
         }
      }

      return itemstack.getName().getString();
   }

   private Double m(ItemStack itemstack) {
      if (!itemstack.contains(DataComponentTypes.LORE)) {
         return null;
      } else {
         LoreComponent lorecomponent = (LoreComponent)itemstack.get(DataComponentTypes.LORE);
         if (lorecomponent != null && lorecomponent.lines() != null) {
            for (Text text : lorecomponent.lines()) {
               String s = text.getString();
               if (s.contains("Cost per unit:") || s.contains("Cost:")) {
                  try {
                     String s1;
                     if (s.contains("Cost per unit:")) {
                        s1 = s.substring(s.indexOf("Cost per unit:") + "Cost per unit:".length()).trim();
                     } else {
                        s1 = s.substring(s.indexOf("Cost:") + "Cost:".length()).trim();
                     }

                     s1 = s1.replaceAll("[^0-9]", "");
                     if (!s1.isEmpty()) {
                        return Double.parseDouble(s1);
                     }
                  } catch (Exception exception) {
                  }
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   private void n(int i) {
      this.sendChatMessage("§bStarting purchase...");
      this.clickSlot(i);
      this.Xe.schedule(() -> {
         this.getClient().execute(this::o);
      }, this.buyDelaySetting.getLongValue(), TimeUnit.MILLISECONDS);
   }

   private void o() {
      if (this.getScreen() instanceof HandledScreen handledscreen) {
         ScreenHandler screenhandler = handledscreen.getScreenHandler();
         int i = this.p(screenhandler);
         if (i != -1) {
            this.clickSlot(i);
            this.sendChatMessage("§a§lPURCHASE COMPLETE!");
            this.Xe.schedule(() -> {
               this.getClient().execute(() -> {
                  this.getClient().setScreen(null);
                  this.Xe.schedule(() -> {
                     this.getClient().execute(this::d);
                  }, 500L, TimeUnit.MILLISECONDS);
                  this.KQ = false;
               });
            }, 300L, TimeUnit.MILLISECONDS);
         } else {
            this.sendChatMessage("§cError: confirm button not found");
            this.getClient().setScreen(null);
            this.Xe.schedule(() -> {
               this.getClient().execute(this::d);
            }, 500L, TimeUnit.MILLISECONDS);
            this.KQ = false;
         }
      } else {
         this.sendChatMessage("§cError: confirmation menu not open");
         this.KQ = false;
      }
   }

   private int p(ScreenHandler screenhandler) {
      for (int i = 0; i < screenhandler.slots.size(); i++) {
         Slot slot = (Slot)screenhandler.slots.get(i);
         ItemStack itemstack = slot.getStack();
         if (itemstack.getItem() == Items.LIME_CONCRETE) {
            String s = this.l(itemstack);
            if (s.contains("Purchase for") || s.contains("purchase for")) {
               return slot.id;
            }
         }
      }

      return -1;
   }

   public ItemCollection q() {
      return this.axj;
   }

   public void r(ItemCollection ItemCollection) {
      this.axj = ItemCollection;
   }

   public RaidminePresetManager s() {
      return this.kZ;
   }
}
