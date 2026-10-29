package dev.mark.system.module.visual;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.setting.ColorSetting;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.KeyBindSetting;
import java.util.Arrays;
import java.util.Collections;

public class KillEffectModule extends Module {
    private final Map<UUID, float[]> playerHealthCache;
    private boolean wasKeyPressed;
    private ListSetting effect;
    private KeyBindSetting testKey;
    private SliderSetting bloodAmount;

    public KillEffectModule() {
        super("KillEffect", "Effects on player kills", Category.VISUAL);
        this.effect = new ListSetting("Effect", "Effect on player kill", Arrays.asList("Lightning", "Blood"), Collections.singletonList("Lightning"), false);
        this.bloodAmount = new SliderSetting("Blood Amount", "Blood particle count", 5.0d, 1.0d, 20.0d, 1.0d);
        this.testKey = new KeyBindSetting("Test", "Press to test effect on yourself", -1);
        this.playerHealthCache = new HashMap<>(); this.wasKeyPressed = false;
        addSettings(this.effect, this.bloodAmount, this.testKey);
    }

    @Override public void onEnable() { this.playerHealthCache.clear(); this.wasKeyPressed = false; }
    @Override public void onDisable() { this.playerHealthCache.clear(); }

    @Override
    public void onEndTick() {
        if (!hasPlayerAndWorld()) { this.playerHealthCache.clear(); return; }
        Vec3d pp = getPlayer().getPos();
        List<PlayerEntity> nearby = getWorld().getEntitiesByClass(PlayerEntity.class,
            new Box(pp.x-10, pp.y-10, pp.z-10, pp.x+10, pp.y+10, pp.z+10), this::isValidTarget);
        for (PlayerEntity p : nearby) {
            UUID uuid = p.getUuid(); float h = p.getHealth(); Vec3d pos = p.getPos();
            if (this.playerHealthCache.containsKey(uuid) && this.playerHealthCache.get(uuid)[0] > 0f && h <= 0f) spawnEffect(pos);
            this.playerHealthCache.put(uuid, new float[]{h, (float)pos.x, (float)pos.y, (float)pos.z});
        }
        this.playerHealthCache.entrySet().removeIf(e -> {
            UUID uuid = e.getKey();
            if (nearby.stream().anyMatch(p -> p.getUuid().equals(uuid))) return false;
            PlayerEntity p = getWorld().getPlayerByUuid(uuid);
            if (p != null || e.getValue()[0] <= 0f) return true;
            spawnEffect(new Vec3d(e.getValue()[1], e.getValue()[2], e.getValue()[3]));
            return true;
        });
    }

    @Override
    public void onRenderAfterEntities(WorldRenderContext context) {
        if (hasPlayerAndWorld()) {
            boolean keyDown = this.testKey.isPressed();
            if (keyDown && !this.wasKeyPressed) spawnEffect(getPlayer().getPos());
            this.wasKeyPressed = keyDown;
        }
    }

    private void spawnEffect(Vec3d pos) {
        String sel = this.effect.getFirst();
        if ("Lightning".equals(sel)) {
            for (int i = 0; i < 20; i++) {
                getWorld().addParticle(ParticleTypes.FLASH, true, true, pos.x, pos.y, pos.z, 0.0, 0.3, 0.0);
            }
            getWorld().playSound(getPlayer(), pos.x, pos.y, pos.z, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 5f, 1f);
        } else if ("Blood".equals(sel)) {
            int count = this.bloodAmount.getIntValue(); Random r = new Random();
            for (int i = 0; i < count; i++)
                getWorld().addParticle(new DustParticleEffect(16711680, 1.5f), true, true,
                    pos.x + (r.nextDouble()-0.5d)*0.5d, pos.y + r.nextDouble()*1.5d, pos.z + (r.nextDouble()-0.5d)*0.5d,
                    (r.nextDouble()-0.5d)*0.2d, r.nextDouble()*0.1d, (r.nextDouble()-0.5d)*0.2d);
        }
    }

    private boolean isValidTarget(PlayerEntity p) { return p != getPlayer() && !isFriendPlayer(p); }
}
