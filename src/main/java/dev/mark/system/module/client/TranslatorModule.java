package dev.mark.system.module.client;

import dev.mark.system.module.Module;

import dev.mark.system.data.TranslationResult;
import dev.mark.system.enums.Category;
import java.lang.Character.UnicodeBlock;
import java.util.List;
import java.util.Map;
import dev.mark.system.net.TranslationClient;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.util.ChatUtil;
import dev.mark.system.core.UpdateDownloader;
import dev.mark.system.util.ArProcessor;
import dev.mark.system.util.ResultCallback;

public class TranslatorModule extends Module {
   private static final Map<String, String> anW = Map.<String, String>of(
      "Russian", "ArProcessor", "English", "en", "German", "de", "French", "fr", "Spanish", "UpdateDownloader", "Ukrainian", "uk", "Chinese", "zh"
   );
   private ListSetting languageSetting = new ListSetting(
      "Язык перевода",
      "На какой язык переводить (для СКМ по сообщению)",
      List.<String>of("Russian", "English", "German", "French", "Spanish", "Ukrainian", "Chinese"),
      List.<String>of("Russian"),
      false
   );

   public TranslatorModule() {
      super("Транзлатор", "Перевод сообщений. СКМ по сообщению / Ctrl+T", Category.CLIENT);
      this.addSettings(this.languageSetting);
   }

   @Override
   public void onEnable() {
      ChatUtil.sendMessage("§7СКМ §f(средняя кнопка мыши) §7по сообщению в чате — перевести");
      ChatUtil.sendMessage("§7Ctrl+T §fв поле ввода — русский<->английский");
   }

   @Override
   public void onDisable() {
   }

   public String c() {
      String s = this.languageSetting.getFirst();
      return anW.getOrDefault(s, "ArProcessor");
   }

   public static String d(String s) {
      return s.replaceAll("§.", "").trim();
   }

   private static boolean e(String s) {
      int i = 0;
      int j = 0;

      for (char c0 : s.toCharArray()) {
         if (UnicodeBlock.of(c0) == UnicodeBlock.CYRILLIC) {
            i++;
         } else if (Character.isLetter(c0)) {
            j++;
         }
      }

      return i > j;
   }

   public void f(String s, ResultCallback ResultCallback) {
      String s1 = d(s);
      if (s1.isEmpty()) {
         ResultCallback.onError("Пустой текст");
      } else {
         boolean flag = e(s1);
         String s2 = flag ? "ArProcessor" : "en";
         String s3 = flag ? "en" : "ArProcessor";
         new Thread(() -> {
            String s7 = TranslationClient.a(s1, s2, s3);
            if (s7 != null && !s7.isEmpty()) {
               ResultCallback.onResult(s7, s2, s3);
            } else {
               ResultCallback.onError("Не удалось перевести");
            }
         }).start();
      }
   }

   public void g(String s, ResultCallback ResultCallback) {
      String s1 = d(s);
      if (s1.isEmpty()) {
         ResultCallback.onError("Пустой текст");
      } else {
         String s2 = this.c();
         new Thread(() -> {
            TranslationResult translationresult = TranslationClient.d(s1, "autodetect", s2);
            if (translationresult == null || translationresult.getText() == null) {
               ResultCallback.onError("Не удалось перевести");
            } else if (!s2.equalsIgnoreCase(translationresult.getDetectedLanguage()) && !s1.equals(translationresult.getText())) {
               String s7 = translationresult.getDetectedLanguage() != null ? translationresult.getDetectedLanguage() : "?";
               ResultCallback.onResult(translationresult.getText(), s7, s2);
            } else {
               String s5 = s2.equals("en") ? "ArProcessor" : "en";
               String s6 = TranslationClient.a(s1, s2, s5);
               if (s6 != null) {
                  ResultCallback.onResult(s6, s2, s5);
               } else {
                  ResultCallback.onError("Не удалось перевести");
               }
            }
         }).start();
      }
   }

   public ListSetting h() {
      return this.languageSetting;
   }
}
