package dev.mark.system.module.player;

import dev.mark.system.enums.Category;
import dev.mark.system.enums.PacketDirection;
import dev.mark.system.event.PacketEvent;
import dev.mark.system.module.Module;
import dev.mark.system.setting.SliderSetting;
import java.util.Set;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;

public class SafeModeModule extends Module {
    private final SliderSetting chatTimeout;
    private long lastPvpMessageTime;
    private static final String[] PVP_KEYWORDS;
    private long lastCommandTime;
    private static volatile boolean isPvpActive;
    private static final Set<String> SAFE_COMMANDS;
    private static volatile boolean isInPvp;
    private String lastCommand;

    static {
        isInPvp = false; isPvpActive = false;
        PVP_KEYWORDS = new String[]{"pvp", "бой", "combat", "арена", "arena", "дуэль", "duel"};
        SAFE_COMMANDS = Set.of("hub", "an", "lobby", "leave", "spawn");
    }

    public SafeModeModule() {
        super("SafeMode", "Prevents leaving during PvP", Category.PLAYER);
        this.chatTimeout = new SliderSetting("Chat Timeout", "Status duration after chat message", 10.0d, 5.0d, 30.0d, 1.0d, " sec", 0);
        this.lastPvpMessageTime = 0L; this.lastCommand = null; this.lastCommandTime = 0L;
        addSettings(this.chatTimeout);
    }

    @Override public void onEnable() { isInPvp = false; isPvpActive = false; this.lastPvpMessageTime = 0L; this.lastCommand = null; this.lastCommandTime = 0L; }
    @Override public void onDisable() { isInPvp = false; isPvpActive = false; this.lastPvpMessageTime = 0L; this.lastCommand = null; this.lastCommandTime = 0L; }

    @Override
    public void onEndTick() {
        if (isEnabled() && hasPlayerAndWorld()) {
            boolean pvpDetected = checkScoreboard();
            boolean recent = this.lastPvpMessageTime > 0 && System.currentTimeMillis() - this.lastPvpMessageTime < (long)(this.chatTimeout.getValue() * 1000.0d);
            boolean wasInPvp = isInPvp;
            isInPvp = pvpDetected || recent;
            if (wasInPvp && !isInPvp) { isPvpActive = false; this.lastCommand = null; }
            if (getClient().currentScreen instanceof GameMenuScreen) return;
            isPvpActive = false;
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (!isEnabled()) return;
        if (event.getType() == PacketDirection.RECEIVE) {
            if (event.getPacket() instanceof GameMessageS2CPacket msg) {
                try { if (containsPvpKeyword(msg.content().getString())) this.lastPvpMessageTime = System.currentTimeMillis(); }
                catch (Throwable ignored) {}
            }
            return;
        }
        if (isInPvp && event.getType() == PacketDirection.SEND) {
            if (event.getPacket() instanceof CommandExecutionC2SPacket cmdPacket) {
                String cmd = cmdPacket.command().toLowerCase();
                for (String sc : SAFE_COMMANDS) {
                    if (cmd.equals(sc) || cmd.startsWith(sc + " ")) {
                        long ct = System.currentTimeMillis();
                        if (sc.equals(this.lastCommand) && ct - this.lastCommandTime < 5000) { this.lastCommand = null; return; }
                        event.setCancelled(true); this.lastCommand = sc; this.lastCommandTime = ct;
                        sendConfirmMessage("§c[SafeMove] §fYou are in PvP! Type /" + sc + " again to confirm.");
                        return;
                    }
                }
            }
        }
    }

    private void sendConfirmMessage(String msg) { if (getPlayer() != null) getPlayer().sendMessage(Text.literal(msg), false); }

    private boolean checkScoreboard() {
        if (getWorld() == null) return false;
        net.minecraft.scoreboard.Scoreboard sb = getWorld().getScoreboard();
        if (sb == null) return false;
        // Check sidebar objective name for PvP keywords
        for (net.minecraft.scoreboard.ScoreboardObjective obj : sb.getObjectives()) {
            if (obj.getDisplayName() != null && containsPvpKeyword(obj.getDisplayName().getString())) return true;
        }
        return false;
    }

    private boolean containsPvpKeyword(String t) { if (t == null || t.isEmpty()) return false; String l = t.toLowerCase(); for (String k : PVP_KEYWORDS) if (l.contains(k)) return true; return false; }
    public static boolean shouldBlockLeave() { return isPvpActive; }
    public static boolean isInPvp() { return isInPvp; }
    public static void setPvpActive(boolean a) { isPvpActive = a; }
}
