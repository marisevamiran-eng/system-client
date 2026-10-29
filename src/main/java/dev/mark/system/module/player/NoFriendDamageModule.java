package dev.mark.system.module.player;

import dev.mark.system.enums.Category;
import dev.mark.system.enums.PacketDirection;
import dev.mark.system.event.PacketEvent;
import dev.mark.system.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class NoFriendDamageModule extends Module {

    public NoFriendDamageModule() { super("NoFriendDamage", "Disables damage to friends", Category.PLAYER); }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketDirection.SEND || getScreen() != null) return;
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet) {
            Entity entity = getWorld().getEntityById(getEntityId(packet));
            if (entity instanceof PlayerEntity && isFriendPlayer((PlayerEntity) entity))
                event.setCancelled(true);
        }
    }

    private int getEntityId(PlayerInteractEntityC2SPacket packet) {
        try { java.lang.reflect.Field f = PlayerInteractEntityC2SPacket.class.getDeclaredField("entityId"); f.setAccessible(true); return f.getInt(packet); }
        catch (Exception e) { return -1; }
    }
}
