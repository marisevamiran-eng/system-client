package dev.mark.system.module.player;

import dev.mark.system.enums.Category;
import dev.mark.system.enums.PacketDirection;
import dev.mark.system.event.PacketEvent;
import dev.mark.system.module.Module;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;

public class EventsTrackerModule extends Module {
    private final List<EventData> events;
    private static final Pattern coordsPattern, timePattern, levelPattern;
    private String currentServer;

    static {
        coordsPattern = Pattern.compile("X:\\s*(-?\\d+),\\s*Y:\\s*(-?\\d+),\\s*Z:\\s*(-?\\d+)");
        timePattern = Pattern.compile("появится через (\\d+) минут");
        levelPattern = Pattern.compile("Уровень:\\s*(.+)");
    }

    public EventsTrackerModule() {
        super("EventsTracker", "Server event tracking", Category.PLAYER);
        this.events = new CopyOnWriteArrayList<>(); this.currentServer = null;
    }

    public List<EventData> getEvents() { return this.events; }

    @Override public void onEnable() { this.events.clear(); this.currentServer = getServerAddress(); }
    @Override public void onDisable() { this.events.clear(); this.currentServer = null; }

    @Override
    public void onEndTick() {
        if (getScreen() != null || this.events.isEmpty()) return;
        String server = getServerAddress();
        if (server != null && !server.equals(this.currentServer)) { this.events.clear(); this.currentServer = server; return; }
        this.currentServer = server;
        long ct = System.currentTimeMillis();
        List<EventData> expired = new ArrayList<>();
        for (EventData e : this.events) if (ct > e.expireTime) expired.add(e);
        this.events.removeAll(expired);
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketDirection.RECEIVE) return;
        if (event.getPacket() instanceof GameMessageS2CPacket msg) {
            try {
                Text text = msg.content();
                String raw = text.toString(), clean = text.getString();
                parseEvent(raw, clean); parseAirdrop(clean);
            } catch (Throwable ignored) {}
        }
    }

    private String getServerAddress() { ServerInfo si = MinecraftClient.getInstance().getCurrentServerEntry(); return si != null ? si.address : null; }

    private void parseEvent(String raw, String clean) {
        String coords = org.apache.commons.lang3.StringUtils.substringBetween(raw, "value='/gps ", "'");
        String level = org.apache.commons.lang3.StringUtils.substringBetween(clean, "Уровень лута: ", "\n ║");
        String summoner = org.apache.commons.lang3.StringUtils.substringBetween(clean, "Призван игроком: ", "\n ║");
        if (coords == null) {
            switch (clean) {
                case "Сундук смерти": registerEvent(clean, level, summoner, -155, 64, 205, "overworld", 300, 0); break;
                case "Адская резня": registerEvent(clean, level, summoner, 48, 87, 73, "overworld", 180, 120); break;
            } return;
        }
        String[] parts = coords.split(" "); if (parts.length < 3) return;
        try { int x = Integer.parseInt(parts[0]), y = Integer.parseInt(parts[1]), z = Integer.parseInt(parts[2]);
            switch (clean) {
                case "Мистический сундук": registerEvent(clean, level, summoner, x, y, z, "overworld", 300, 0); break;
                case "Вулкан": registerEvent(clean, level, summoner, x, y, z, "overworld", 300, 120); break;
                case "Метеоритный дождь": case "Маяк убийца": case "Мистический Алтарь": registerEvent(clean, level, summoner, x, y, z, "overworld", 360, 0); break;
                case "Загадочный маяк": registerEvent(clean, level, summoner, x, y, z, "overworld", 60, 180); break;
            }
        } catch (NumberFormatException ignored) {}
    }

    private void parseAirdrop(String msg) {
        if (!msg.contains("Аирдроп") || !msg.contains("координатах")) return;
        Matcher cm = coordsPattern.matcher(msg); if (!cm.find()) return;
        int x = Integer.parseInt(cm.group(1)), y = Integer.parseInt(cm.group(2)), z = Integer.parseInt(cm.group(3));
        String level = null; Matcher lm = levelPattern.matcher(msg); if (lm.find()) level = lm.group(1).trim();
        int duration = 300; Matcher tm = timePattern.matcher(msg); if (tm.find()) duration = Integer.parseInt(tm.group(1)) * 60;
        registerEvent(msg.contains("с боссом") ? "Аирдроп с боссом" : "Аирдроп", level, null, x, y, z, "overworld", duration, 0);
    }

    private void registerEvent(String name, String level, String summoner, int x, int y, int z, String dim, int dur, int delay) {
        for (EventData e : this.events) if (e.eventName.equals(name)) return;
        long ct = System.currentTimeMillis();
        this.events.add(new EventData(name, level, summoner, x, y, z, dim, ct + (dur * 1000L), ct + (dur * 1000L) + (delay * 1000L)));
    }

    public static class EventData { public final String eventName, level, summoner, dimension; public final int x, y, z; public final long expireTime, removeTime;
        public EventData(String en, String l, String s, int x, int y, int z, String d, long et, long rt) { this.eventName = en; this.level = l; this.summoner = s; this.x = x; this.y = y; this.z = z; this.dimension = d; this.expireTime = et; this.removeTime = rt; } }
}
