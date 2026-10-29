package dev.mark.system.gui.model;

import dev.mark.system.gui.Component;
import java.util.ArrayList;
import java.util.List;
import dev.mark.system.setting.ActionKeySetting;
import dev.mark.system.setting.ActionSetting;
import dev.mark.system.setting.BlockListSetting;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.ColorSetting;
import dev.mark.system.setting.FilePickerSetting;
import dev.mark.system.setting.GroupSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.RangeSetting;
import dev.mark.system.setting.Setting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.data.WaypointData;
import dev.mark.system.gui.Component;
import dev.mark.system.gui.component.ColorSwatchButton;
import dev.mark.system.gui.component.DropdownSelector;
import dev.mark.system.gui.component.DualRangeSlider;
import dev.mark.system.gui.component.EditButton;
import dev.mark.system.gui.component.FilePickerComponent;
import dev.mark.system.gui.component.GroupOpenButton;
import dev.mark.system.gui.component.KeyBindButton;
import dev.mark.system.gui.component.KeyBindButtonComponent;
import dev.mark.system.gui.component.SliderComponent;
import dev.mark.system.gui.component.ToggleSwitch;

public class SettingComponentFactory {
   private static final float Mp = 286.0F;
   private static final float LN = 10.0F;

   public static List<SettingComponentEntry> a(List<Setting> list, float f, float f1, Runnable runnable) {
      ArrayList arraylist = new ArrayList();
      float f2 = f;

      for (Setting setting : list) {
         Component component = b(setting, f1, runnable);
         if (component != null) {
            component.setPosition(component.getX(), f2);
            arraylist.add(new SettingComponentEntry(setting.getDisplayName(), component, f2, setting));
            if (setting.isVisible()) {
               f2 += component.getHeight() + 10.0F;
            }
         }
      }

      return arraylist;
   }

   private static Component b(Setting setting, float f, Runnable runnable) {
      if (setting instanceof BooleanSetting booleansetting) {
         return c(booleansetting, f);
      } else if (setting instanceof RangeSetting rangesetting) {
         return d(rangesetting, f);
      } else if (setting instanceof SliderSetting slidersetting) {
         return e(slidersetting, f);
      } else if (setting instanceof ListSetting listsetting) {
         return f(listsetting, f);
      } else if (setting instanceof GroupSetting groupsetting) {
         return g(groupsetting, f, runnable);
      } else if (setting instanceof ActionKeySetting actionkeysetting) {
         return i(actionkeysetting, f, runnable);
      } else if (setting instanceof ColorSetting colorsetting) {
         return h(colorsetting, f, runnable);
      } else if (setting instanceof FilePickerSetting filepickersetting) {
         return j(filepickersetting, f);
      } else if (setting instanceof ActionSetting actionsetting) {
         return k(actionsetting, f);
      } else {
         return setting instanceof BlockListSetting blocklistsetting ? l(blocklistsetting, f, runnable) : null;
      }
   }

   private static ToggleSwitch c(BooleanSetting booleansetting, float f) {
      float f1 = 236.0F - f;
      ToggleSwitch ToggleSwitch = new ToggleSwitch(f1, 0.0F);
      ToggleSwitch.a(booleansetting.getValue());
      ToggleSwitch.c(() -> {
         booleansetting.setValue(ToggleSwitch.d());
      });
      return ToggleSwitch;
   }

   private static DualRangeSlider d(RangeSetting rangesetting, float f) {
      float f1 = 276.0F - f;
      DualRangeSlider DualRangeSlider = new DualRangeSlider(
         f1,
         0.0F,
         (float)rangesetting.getMin(),
         (float)rangesetting.getMax(),
         (float)rangesetting.getValueLow(),
         (float)rangesetting.getValueHigh(),
         (float)rangesetting.getStep(),
         rangesetting.getDecimalPlaces()
      );
      DualRangeSlider.l(() -> {
         rangesetting.setRange(DualRangeSlider.j(), DualRangeSlider.k());
      });
      return DualRangeSlider;
   }

   private static SliderComponent e(SliderSetting slidersetting, float f) {
      float f1 = 276.0F - f;
      SliderComponent SliderComponent = new SliderComponent(
         f1,
         0.0F,
         (float)slidersetting.getMin(),
         (float)slidersetting.getMax(),
         (float)slidersetting.getValue(),
         (float)slidersetting.getStep(),
         slidersetting.getDecimalPlaces()
      );
      SliderComponent.h(() -> {
         slidersetting.setValue(SliderComponent.g());
      });
      return SliderComponent;
   }

   private static DropdownSelector f(ListSetting listsetting, float f) {
      float f1 = 276.0F - f;
      DropdownSelector DropdownSelector = new DropdownSelector(f1, 0.0F, listsetting.getAvailableValues(), listsetting.getFirst());
      DropdownSelector.s(s -> {
         listsetting.setSelectedValues(List.<String>of(s));
      });
      return DropdownSelector;
   }

   private static GroupOpenButton g(GroupSetting groupsetting, float f, Runnable runnable) {
      float f1 = 256.0F - f;
      return new GroupOpenButton(f1, 0.0F, runnable);
   }

   private static ColorSwatchButton h(ColorSetting colorsetting, float f, Runnable runnable) {
      float f1 = 256.0F - f;
      ColorSwatchButton ColorSwatchButton = new ColorSwatchButton(f1, 0.0F, colorsetting.getColor());
      ColorSwatchButton.a(runnable);
      return ColorSwatchButton;
   }

   private static KeyBindButtonComponent i(ActionKeySetting actionkeysetting, float f, Runnable runnable) {
      float f1 = 276.0F - f;
      float f2 = f1 - 50.0F;
      return new KeyBindButtonComponent(f2, 0.0F, actionkeysetting);
   }

   private static FilePickerComponent j(FilePickerSetting filepickersetting, float f) {
      float f1 = 176.0F - f;
      return new FilePickerComponent(f1, 0.0F, filepickersetting);
   }

   private static KeyBindButton k(ActionSetting actionsetting, float f) {
      float f1 = 256.0F - f;
      KeyBindButton KeyBindButton = new KeyBindButton(f1, 0.0F);
      KeyBindButton.b(actionsetting::invoke);
      return KeyBindButton;
   }

   private static EditButton l(BlockListSetting blocklistsetting, float f, Runnable runnable) {
      float f1 = 256.0F - f;
      EditButton EditButton = new EditButton(f1, 0.0F);
      EditButton.a(runnable);
      return EditButton;
   }
}
