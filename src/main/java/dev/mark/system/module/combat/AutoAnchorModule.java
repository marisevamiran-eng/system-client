package dev.mark.system.module.combat;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.KeyBindSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.util.AngleUtil;
import dev.mark.system.util.TickHelper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AutoAnchorModule extends Module {
    private int glowstoneSlot;
    private long lastRotationTime;
    private BooleanSetting autoPlace;
    private KeyBindSetting anchorKey;
    private State state;
    private BlockPos targetBlock;
    private final TickHelper tickHelper;
    private Vec3d hitPos;
    private SliderSetting placeDelay;
    private int verifyTicks;
    private ListSetting delayType;
    private SliderSetting rotationSpeed;
    private int obsidianSlot;
    private boolean delayed;
    private int originalSlot;
    private BooleanSetting swapBack;
    private Direction hitDirection;
    private Vec3d aimPos;
    private BooleanSetting rotation;
    private BlockPos obsidianPos;
    private SliderSetting delayMs;

    private enum State { IDLE, SWAP_TO_ANCHOR, PLACE_ANCHOR, VERIFY_ANCHOR, SWAP_TO_GLOWSTONE, CHARGE_ANCHOR, SWAP_TO_EMPTY_SLOT, EXPLODE_ANCHOR, SWAP_BACK, DONE }

    public AutoAnchorModule() {
        super("AutoAnchor", "Automatically places anchors charges and explodes by keybind", Category.COMBAT);
        this.delayType = new ListSetting("Delay Type", "Delay type between actions", Arrays.asList("Tick-based", "Milliseconds"), Collections.singletonList("Tick-based"), false);
        this.placeDelay = new SliderSetting("Place Delay", "Delay after placing anchor", 1.0d, 0.0d, 20.0d, 1.0d);
        this.delayMs = new SliderSetting("Place Delay (ms)", "Delay after placing anchor (ms)", 100.0d, 0.0d, 500.0d, 50.0d);
        this.rotationSpeed = new SliderSetting("Aim Speed", "Camera rotation speed", 5.0d, 1.0d, 15.0d, 0.5d);
        this.autoPlace = new BooleanSetting("Auto on Place", "Auto place and explode anchor when obsidian placed", false);
        this.swapBack = new BooleanSetting("Swap Back", "Return item to hand after explosion", true);
        this.rotation = new BooleanSetting("Rotation", "Rotate camera to block/anchor", true);
        this.anchorKey = new KeyBindSetting("Anchor Key", "Key to start chain", -1);
        this.anchorKey.setCallback(this::onAnchorKeyPress);
        this.tickHelper = new TickHelper();
        this.state = State.IDLE;
        this.obsidianSlot = -1;
        this.originalSlot = -1;
        this.glowstoneSlot = -1;
        this.targetBlock = null;
        this.verifyTicks = 0;
        this.delayed = false;
        this.aimPos = null;
        this.lastRotationTime = 0L;
        this.hitPos = null;
        this.obsidianPos = null;
        this.hitDirection = null;
        addSettings(this.autoPlace, this.delayType, this.placeDelay, this.delayMs, this.rotationSpeed, this.swapBack, this.rotation, this.anchorKey);
    }

    @Override public void onEnable() { reset(); }
    @Override public void onDisable() { reset(); }

    @Override
    public void onEndTick() {
        if (getScreen() != null) return;
        while (this.state != State.IDLE) {
            if (this.delayed && !this.tickHelper.isReady()) { this.tickHelper.tick(); return; }
            State cur = this.state;
            switch (this.state) {
                case SWAP_TO_ANCHOR: swapToAnchor(); break;
                case PLACE_ANCHOR: placeAnchor(); break;
                case VERIFY_ANCHOR: verifyAnchor(); break;
                case SWAP_TO_GLOWSTONE: swapToGlowstone(); break;
                case CHARGE_ANCHOR: chargeAnchor(); break;
                case SWAP_TO_EMPTY_SLOT: swapToEmptySlot(); break;
                case EXPLODE_ANCHOR: explodeAnchor(); break;
                case SWAP_BACK: swapBackSlot(); break;
                case DONE: reset(); break;
            }
            if (this.state == State.IDLE) return;
            if ((this.delayed && !this.tickHelper.isReady()) || this.state == cur) return;
        }
    }

    @Override
    public void onRenderAfterEntities(WorldRenderContext context) {
        if (!isEnabled() || this.state == State.IDLE || this.aimPos == null || !this.rotation.getValue()) return;
        long ct = System.nanoTime();
        double dt = MathHelper.clamp(this.lastRotationTime > 0 ? (ct - this.lastRotationTime) / 1.0E9d : 0.0d, 0.0d, 1.0d);
        this.lastRotationTime = ct;
        Vec3d eye = getPlayer().getEyePos();
        double dx = this.aimPos.x - eye.x, dy = this.aimPos.y - eye.y, dz = this.aimPos.z - eye.z;
        double hd = Math.sqrt(dx*dx + dz*dz);
        float ty = (float)(Math.atan2(dz, dx) * 180.0d / Math.PI) - 90f;
        float tp = (float)(-(Math.atan2(dy, hd) * 180.0d / Math.PI));
        float cy = getPlayer().getYaw(), cp = getPlayer().getPitch();
        double yd = AngleUtil.normalizeAngle(ty - cy), pd = tp - cp;
        if (Math.sqrt(yd*yd + pd*pd) < 0.5d) return;
        double sp = MathHelper.clamp(this.rotationSpeed.getValue() * 0.1d * dt, 0.0d, 1.0d);
        double ys = yd * sp, ps = pd * sp;
        double gcd = AngleUtil.getGCD();
        double ysr = AngleUtil.roundToGCD(ys, gcd), psr = AngleUtil.roundToGCD(ps, gcd);
        if (ysr == 0 && Math.abs(yd) > gcd) ysr = Math.signum(yd) * gcd;
        if (psr == 0 && Math.abs(pd) > gcd) psr = Math.signum(pd) * gcd;
        getPlayer().setYaw(cy + (float)ysr);
        getPlayer().setPitch(MathHelper.clamp(cp + (float)psr, -90f, 90f));
    }

    @Override
    public ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!isEnabled() || !this.autoPlace.getValue() || getScreen() != null || this.state != State.IDLE)
            return ActionResult.PASS;
        if (player.getMainHandStack().getItem() != Items.OBSIDIAN) return ActionResult.PASS;
        BlockPos tp = hitResult.getBlockPos().offset(hitResult.getSide());
        if (!isValidPlacement(tp)) return ActionResult.PASS;
        this.glowstoneSlot = findSlot(Items.GLOWSTONE);
        if (this.glowstoneSlot == -1) return ActionResult.PASS;
        this.obsidianSlot = getInventory().selectedSlot;
        this.originalSlot = -1; this.targetBlock = tp;
        this.hitPos = null; this.obsidianPos = null; this.hitDirection = null;
        this.verifyTicks = 0; this.delayed = false;
        this.aimPos = Vec3d.ofCenter(tp);
        setState(State.VERIFY_ANCHOR, this.placeDelay, this.delayMs);
        return ActionResult.PASS;
    }

    private void onAnchorKeyPress() {
        if (this.state != State.IDLE || getScreen() != null) return;
        if (!(getClient().crosshairTarget instanceof BlockHitResult bhr) || bhr.getType() != HitResult.Type.BLOCK) return;
        BlockPos hp = bhr.getBlockPos();
        if (getWorld().getBlockState(hp).isReplaceable() || getWorld().getBlockState(hp).isAir() ||
            getPlayer().getEyePos().distanceTo(Vec3d.ofCenter(hp)) > 5.0d) return;
        this.glowstoneSlot = findSlot(Items.GLOWSTONE);
        if (this.glowstoneSlot == -1) return;
        Direction side = bhr.getSide();
        BlockPos pp = hp.offset(side);
        if (!isValidPlacement(pp)) return;
        this.obsidianSlot = getInventory().selectedSlot; this.targetBlock = pp;
        this.hitPos = bhr.getPos(); this.obsidianPos = bhr.getBlockPos(); this.hitDirection = side;
        this.verifyTicks = 0; this.delayed = false; this.aimPos = this.hitPos;
        if (getPlayer().getMainHandStack().getItem() == Items.RESPAWN_ANCHOR) { this.state = State.PLACE_ANCHOR; return; }
        this.originalSlot = findSlot(Items.RESPAWN_ANCHOR);
        if (this.originalSlot == -1) reset(); else this.state = State.SWAP_TO_ANCHOR;
    }

    private void swapToAnchor() {
        if (this.originalSlot < 9) getInventory().selectedSlot = this.originalSlot;
        else getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.originalSlot, this.obsidianSlot, SlotActionType.PICKUP, getPlayer());
        setState(State.PLACE_ANCHOR, 1);
    }
    private void placeAnchor() {
        if (this.hitPos == null || this.obsidianPos == null || this.hitDirection == null || this.targetBlock == null) { reset(); return; }
        if (getPlayer().getMainHandStack().getItem() != Items.RESPAWN_ANCHOR) { reset(); return; }
        if (getWorld().getBlockState(this.obsidianPos).isReplaceable() || getWorld().getBlockState(this.obsidianPos).isAir()) { reset(); return; }
        if (!isValidPlacement(this.targetBlock)) { reset(); return; }
        if (getPlayer().getEyePos().distanceTo(Vec3d.ofCenter(this.obsidianPos)) > 5.0d) { reset(); return; }
        this.aimPos = this.hitPos;
        getInteractionManager().interactBlock(getClientPlayer(), Hand.MAIN_HAND, new BlockHitResult(this.hitPos, this.hitDirection, this.obsidianPos, false));
        getPlayer().swingHand(Hand.MAIN_HAND);
        this.hitPos = null; this.obsidianPos = null; this.hitDirection = null; this.verifyTicks = 0;
        setState(State.VERIFY_ANCHOR, this.placeDelay, this.delayMs);
    }
    private void verifyAnchor() {
        if (this.targetBlock == null) { reset(); return; }
        if (getWorld().getBlockState(this.targetBlock).getBlock() == Blocks.RESPAWN_ANCHOR) { setState(State.SWAP_TO_GLOWSTONE, 0); return; }
        if (++this.verifyTicks >= 5) reset(); else setState(State.VERIFY_ANCHOR, 1);
    }
    private void swapToGlowstone() {
        this.glowstoneSlot = findSlot(Items.GLOWSTONE);
        if (this.glowstoneSlot == -1) { reset(); return; }
        if (this.glowstoneSlot < 9) getInventory().selectedSlot = this.glowstoneSlot;
        else getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.glowstoneSlot, getInventory().selectedSlot, SlotActionType.PICKUP, getPlayer());
        setState(State.CHARGE_ANCHOR, 1);
    }
    private void chargeAnchor() {
        if (this.targetBlock == null) { reset(); return; }
        this.aimPos = Vec3d.ofCenter(this.targetBlock);
        if (this.rotation.getValue() && !isAimedAt(Vec3d.ofCenter(this.targetBlock), 5.0d)) { setState(State.CHARGE_ANCHOR, 1); return; }
        getInteractionManager().interactBlock(getClientPlayer(), Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(this.targetBlock), Direction.UP, this.targetBlock, false));
        getPlayer().swingHand(Hand.MAIN_HAND);
        setState(State.SWAP_TO_EMPTY_SLOT, 1);
    }
    private void swapToEmptySlot() { getInventory().selectedSlot = findEmptySlot(); setState(State.EXPLODE_ANCHOR, 1); }
    private void explodeAnchor() {
        if (this.targetBlock == null) { reset(); return; }
        this.aimPos = Vec3d.ofCenter(this.targetBlock);
        if (this.rotation.getValue() && !isAimedAt(Vec3d.ofCenter(this.targetBlock), 5.0d)) { setState(State.EXPLODE_ANCHOR, 1); return; }
        getInteractionManager().interactBlock(getClientPlayer(), Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(this.targetBlock), Direction.UP, this.targetBlock, false));
        getPlayer().swingHand(Hand.MAIN_HAND);
        setState(this.swapBack.getValue() ? State.SWAP_BACK : State.DONE, 1);
    }
    private void swapBackSlot() {
        if (this.originalSlot != -1 && this.originalSlot >= 9)
            getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.originalSlot, this.obsidianSlot, SlotActionType.PICKUP, getPlayer());
        getInventory().selectedSlot = this.obsidianSlot;
        this.aimPos = null; setState(State.DONE, 0);
    }
    private void reset() {
        this.state = State.IDLE; this.tickHelper.reset();
        this.obsidianSlot = -1; this.originalSlot = -1; this.glowstoneSlot = -1;
        this.targetBlock = null; this.verifyTicks = 0; this.delayed = false;
        this.aimPos = null; this.lastRotationTime = 0L;
        this.hitPos = null; this.obsidianPos = null; this.hitDirection = null;
    }

    private int findSlot(net.minecraft.item.Item item) {
        for (int i = 0; i < getPlayer().getInventory().size(); i++)
            if (getPlayer().getInventory().getStack(i).getItem() == item) return i;
        return -1;
    }
    private int findEmptySlot() { for (int i = 0; i < 9; i++) if (getInventory().getStack(i).isEmpty()) return i; return 0; }
    private boolean isValidPlacement(BlockPos pos) { return getWorld().getBlockState(pos).isAir() && !getPlayer().getBoundingBox().intersects(new Box(pos)); }
    private boolean isAimedAt(Vec3d t, double tol) { return Math.toDegrees(Math.acos(MathHelper.clamp(getPlayer().getRotationVector().dotProduct(t.subtract(getPlayer().getEyePos()).normalize()), -1.0d, 1.0d))) <= tol; }
    private boolean isTickMode() { return "Tick-based".equals(this.delayType.getFirst()); }

    private void setState(State ns, int d) {
        this.state = ns;
        if (d <= 0) { this.delayed = false; this.tickHelper.reset(); return; }
        this.delayed = true;
        if (isTickMode()) { this.tickHelper.setTickMode(false); this.tickHelper.setTickDelay(d); }
        else { this.tickHelper.setTickMode(true); this.tickHelper.setMsDelay((long)d * 50); }
    }
    private void setState(State ns, SliderSetting dt, SliderSetting dm) {
        this.state = ns;
        if (isTickMode()) {
            int t = dt.getIntValue();
            if (t <= 0) { this.delayed = false; this.tickHelper.reset(); return; }
            this.delayed = true; this.tickHelper.setTickMode(false); this.tickHelper.setTickDelay(t);
        } else {
            int ms = dm.getIntValue();
            if (ms <= 0) { this.delayed = false; this.tickHelper.reset(); return; }
            this.delayed = true; this.tickHelper.setTickMode(true); this.tickHelper.setMsDelay(ms);
        }
    }
}
