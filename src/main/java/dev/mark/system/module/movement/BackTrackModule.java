package dev.mark.system.module.movement;

import dev.mark.system.enums.Category;
import dev.mark.system.enums.PacketDirection;
import dev.mark.system.event.PacketEvent;
import dev.mark.system.module.Module;
import dev.mark.system.setting.SliderSetting;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BackTrackModule extends Module {
    private final Queue<QueuedPacket> packetQueue;
    private final SliderSetting delay;
    private Vec3d targetPos;
    private PlayerEntity targetEntity;

    public BackTrackModule() {
        super("BackTrack", "Delays incoming packets for lag compensation", Category.COMBAT);
        this.delay = new SliderSetting("Delay", "Packet Hold Delay", 50.0d, 20.0d, 200.0d, 5.0d, "ms", 0);
        this.packetQueue = new LinkedBlockingQueue<>();
        addSettings(this.delay);
    }

    @Override public void onEnable() { this.packetQueue.clear(); this.targetEntity = null; this.targetPos = null; }
    @Override public void onDisable() { flushPackets(true); this.packetQueue.clear(); this.targetEntity = null; this.targetPos = null; }

    @Override
    public void onEndTick() { if (getScreen() == null && getPlayer().hurtTime > 10) flushPackets(false); }

    @Override
    public void onAttackEntity(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (entity instanceof PlayerEntity target) {
            if (entity != this.targetEntity) { flushPackets(true); this.targetPos = entity.getPos(); }
            this.targetEntity = target;
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketDirection.RECEIVE || event.isCancelled() || getScreen() != null) return;
        if (event.getPacket() instanceof PlayerPositionLookS2CPacket || event.getPacket() instanceof DisconnectS2CPacket) { flushPackets(true); return; }
        if (event.getPacket() instanceof HealthUpdateS2CPacket hp && hp.getHealth() <= 0.0f) { flushPackets(true); return; }
        if (this.targetEntity == null || this.targetPos == null) return;
        // Track position updates via EntityTrackerUpdate (works across MC versions)
        if (event.getPacket() instanceof EntityTrackerUpdateS2CPacket tp && tp.id() == this.targetEntity.getId()) {
            event.setCancelled(true);
            this.packetQueue.add(new QueuedPacket(event.getPacket(), System.currentTimeMillis()));
        }
    }

    private void flushPackets(boolean force) {
        long ct = System.currentTimeMillis();
        long md = (long)this.delay.getValue();
        for (QueuedPacket qp : new LinkedBlockingQueue<>(this.packetQueue)) {
            if (force || ct - qp.getTimestamp() > md) {
                try { if (getNetworkHandler() != null) getNetworkHandler().getConnection().send(qp.getPacket()); } catch (Exception e) {}
                this.packetQueue.remove();
            }
        }
        if (force) { this.targetEntity = null; this.targetPos = null; }
    }

    private static class QueuedPacket { private final Packet<?> packet; private final long timestamp;
        QueuedPacket(Packet<?> p, long t) { this.packet = p; this.timestamp = t; }
        Packet<?> getPacket() { return packet; } long getTimestamp() { return timestamp; } }
}
