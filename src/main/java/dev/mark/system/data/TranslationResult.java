package dev.mark.system.data;

public class TranslationResult {
   private final String text;
   private final String detectedLanguage;
   private final String targetLanguage;

   public TranslationResult(String s, String s1, String s2) {
      this.text = s;
      this.detectedLanguage = s1;
      this.targetLanguage = s2;
   }

   public String text() { return this.text; }
   public String detectedLanguage() { return this.detectedLanguage; }
   public String targetLanguage() { return this.targetLanguage; }
   public String getText() { return this.text; }
   public String getDetectedLanguage() { return this.detectedLanguage; }
   public String getTargetLanguage() { return this.targetLanguage; }
}
