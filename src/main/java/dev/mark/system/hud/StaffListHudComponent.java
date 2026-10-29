package dev.mark.system.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.BuiltText;
import dev.mark.system.render.ColorCache;
import dev.mark.system.render.RectangleCache;
import dev.mark.system.config.SettingEntry;
import dev.mark.system.render.CornerBracketRenderer;

public class StaffListHudComponent extends ListHudComponent<StaffListEntry> {
   private static final float jK = 20.0F;
   private static final float ato = 60.0F;
   private static final float Xg = 4.0F;
   private static final float iC = 13.0F;
   private static final float sL = 31.0F;
   private static final Pattern amD = Pattern.compile("^\\w{3,16}$");
   private static final Color abt = ColorCache.b(150, 150, 150);
   private static final Color eE = ColorCache.b(255, 170, 0);
   private static final Color aoC = ColorCache.b(127, 85, 0);
   private static final Color JK = ColorCache.b(255, 85, 85);
   private static final Color asz = ColorCache.b(127, 42, 42);
   private static final Color adO = ColorCache.b(85, 170, 255);
   private static final Color ot = ColorCache.b(42, 85, 127);
   private static final Color eC = ColorCache.b(85, 255, 85);
   private static final Color auo = ColorCache.b(42, 127, 42);
   private static final Color atx = ColorCache.b(170, 85, 255);
   private static final Color alr = ColorCache.b(85, 42, 127);
   private static final Color gI = ColorCache.b(200, 200, 200);
   private static final Color avl = ColorCache.b(100, 100, 100);

   public StaffListHudComponent(float f, float f1) {
      super(f, f1, "Staff List", "stafflist", 20.0F, 31.0F);
   }

   @Override
   public boolean isAlwaysVisible() {
      return true;
   }

   @Override
   protected void b() {
      HashSet hashset = new HashSet();
      hashset.add("AdminExample");
      hashset.add("ModerExample");
      hashset.add("HelperExample");
      this.entryManager.a(hashset, s -> {
         boolean flag = true;
         if (s.equals("AdminExample")) {
            return new StaffListEntry("SуstemPlayer", "Admin", flag, s);
         } else if (s.equals("ModerExample")) {
            return new StaffListEntry("ModerPlayer", "Moder", flag, s);
         } else if (s.equals("HelperExample")) {
            return new StaffListEntry("HelperPlayer", "Helper", flag, s);
         } else {
            return null;
         }
      });
   }

   @Override
   protected void a() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      if (minecraftclient.player != null && minecraftclient.getNetworkHandler() != null && minecraftclient.world != null) {
         HashSet hashset = new HashSet();
         HashMap hashmap = new HashMap();
         List list = this.getOnlinePlayers();

         for (PlayerListEntry playerlistentry : minecraftclient.getNetworkHandler().getPlayerList()) {
            if (!minecraftclient.isInSingleplayer() && playerlistentry.getScoreboardTeam() != null) {
               String s = playerlistentry.getProfile().getName();
               if (amD.matcher(s).matches()) {
                  Team team = playerlistentry.getScoreboardTeam();
                  String s1 = Formatting.strip(team.getPrefix().getString()).toLowerCase();
                  if (this.b(s1)) {
                     boolean flag = playerlistentry.getGameMode() == GameMode.SPECTATOR;
                     String s2 = this.c(team.getPrefix().getString());
                     hashset.add(s);
                     hashmap.put(s, new SettingEntry(s, s2, flag));
                  }
               }
            }
         }

         for (Team team1 : minecraftclient.world.getScoreboard().getTeams()) {
            if (!team1.getPrefix().getString().isEmpty() && !minecraftclient.isInSingleplayer()) {
               String s3 = team1.getPlayerList().toString().replace("[", "").replace("]", "");
               if (!s3.isEmpty() && !list.contains(s3)) {
                  String s4 = Formatting.strip(team1.getPrefix().getString()).toLowerCase();
                  if (this.b(s4)) {
                     String s5 = this.c(team1.getPrefix().getString());
                     hashset.add(s3);
                     hashmap.put(s3, new SettingEntry(s3, s5, true));
                  }
               }
            }
         }

         this.entryManager.a(hashset, s6 -> {
            SettingEntry flx = (SettingEntry)hashmap.get(s6);
            return flx != null ? new StaffListEntry(flx.As, flx.nz, flx.oO, s6) : null;
         });

         for (StaffListEntry stafflistentry : this.entryManager.e()) {
            SettingEntry SettingEntry = (SettingEntry)hashmap.get(stafflistentry.c());
            if (SettingEntry != null) {
               stafflistentry.a(SettingEntry.oO);
            }
         }
      }
   }

   private List<String> getOnlinePlayers() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      ArrayList arraylist = new ArrayList();
      if (minecraftclient.player != null && minecraftclient.getNetworkHandler() != null) {
         for (PlayerListEntry playerlistentry : minecraftclient.getNetworkHandler().getPlayerList()) {
            String s = playerlistentry.getProfile().getName();
            if (amD.matcher(s).matches()) {
               arraylist.add(s);
            }
         }

         return arraylist;
      } else {
         return arraylist;
      }
   }

   private boolean b(String s) {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      return minecraftclient.getCurrentServerEntry() != null && minecraftclient.getCurrentServerEntry().address.contains("mcfunny")
         ? s.contains("helper") || s.contains("хелпер") || s.contains("moder") || s.contains("модер")
         : s.contains("helper")
            || s.contains("хелпер")
            || s.contains("moder")
            || s.contains("модер")
            || s.contains("admin")
            || s.contains("админ")
            || s.contains("owner")
            || s.contains("curator")
            || s.contains("куратор")
            || s.contains("поддержка")
            || s.contains("сотрудник")
            || s.contains("зам")
            || s.contains("стажёр");
   }

   private String c(String s) {
      String s1 = Formatting.strip(s).toLowerCase().trim();
      if (s1.contains("owner")) {
         return "Owner";
      } else if (s1.contains("admin") || s1.contains("админ")) {
         return "Admin";
      } else if (s1.contains("moder") || s1.contains("модер")) {
         return "Moder";
      } else if (s1.contains("helper") || s1.contains("хелпер")) {
         return "Helper";
      } else if (s1.contains("curator") || s1.contains("куратор")) {
         return "Curator";
      } else if (s1.contains("поддержка")) {
         return "Support";
      } else if (s1.contains("сотрудник")) {
         return "Staff";
      } else if (s1.contains("зам")) {
         return "Deputy";
      } else {
         return s1.contains("стажёр") ? "Trainee" : "Staff";
      }
   }

   protected void d(Matrix4f matrix4f, float f, float f1, StaffListEntry stafflistentry, float f2, float f3) {
      if (!(f3 < 0.3F)) {
         String s1 = stafflistentry.online ? " [V]" : "";
         String s2 = stafflistentry.name;
         String s = s2 + s1;
         Color color = EaseLinear;
         BuiltText builttext = this.w(this.Eb, s, 13.0F, color);
         builttext.a(matrix4f, f + 10.0F, f1 + (f2 - 13.0F) * 0.5F, f3);
         Color color1 = this.e(stafflistentry.displayName, stafflistentry.online);
         float f4 = this.ew.c(stafflistentry.displayName, 13.0F);
         float f5 = Math.max(60.0F, f4 + 8.0F);
         float f6 = f + this.width - 10.0F - f5;
         BuiltRectangle builtrectangle = RectangleCache.b(f5, f2, 4.0F);
         float f7 = stafflistentry.online ? f3 * 0.6F : f3;
         builtrectangle.a(matrix4f, f6, f1, f7);
         BuiltText builttext1 = this.w(this.ew, stafflistentry.displayName, 13.0F, color1);
         float f8 = f6 + (f5 - f4) * 0.5F;
         float f9 = f1 + (f2 - 13.0F) * 0.5F;
         builttext1.a(matrix4f, f8, f9 - 1.0F, f3);
      }
   }

   private Color e(String s, boolean flag) {
      String s1 = s.toLowerCase();
      switch (s1) {
         case "owner":
            if (flag) {
               return aoC;
            }

            return eE;
         case "admin":
            if (flag) {
               return asz;
            }

            return JK;
         case "moder":
            if (flag) {
               return ot;
            }

            return adO;
         case "helper":
            if (flag) {
               return auo;
            }

            return eC;
         case "curator":
            if (flag) {
               return alr;
            }

            return atx;
         default:
            return flag ? avl : gI;
      }
   }

}
