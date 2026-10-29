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
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

public class AutoCrystalModule extends Module {
    private int crystalSlot;
    private BlockPos targetBlock;
    private BooleanSetting autoPlace;
    private long lastUpdateTime;
    private int placeTicks;
    private BooleanSetting rotation;
    private ListSetting delayType;
    private SliderSetting placeDelay;
    private Direction hitDirection;
    private Vec3d hitPos;
    private SliderSetting rotationSpeed;
    private final TickHelper tickHelper;
    private Vec3d aimPos;
    private SliderSetting maxWaitTicks;
    private KeyBindSetting obsidianKey;
    private BooleanSetting swapBack;
    private int obsidianSlot;
    private boolean delayed;
    private BlockPos obsidianPos;
    private State state;
    private int swapSlot;
    private int slotToRestore;
    private SliderSetting delayMs;

    private enum State { IDLE, SWAP_TO_OBSIDIAN, PLACE_OBSIDIAN, VERIFY_OBSIDIAN, SWAP_TO_CRYSTAL, PLACE_CRYSTAL, WAIT_CRYSTAL, ATTACK_CRYSTAL, SWAP_BACK, DONE }

    public AutoCrystalModule() {
        super("AutoCrystal", "Automatically places obsidian crystal and explodes by keybind", Category.COMBAT);
        this.delayType = new ListSetting("Delay Type", "Delay type between actions", Arrays.asList("Tick-based", "Milliseconds"), Collections.singletonList("Tick-based"), false);
        this.placeDelay = new SliderSetting("Place Delay", "Delay after placing obsidian", 1.0d, 0.0d, 20.0d, 1.0d);
        this.delayMs = new SliderSetting("Place Delay (ms)", "Delay after placing obsidian (ms)", 100.0d, 0.0d, 500.0d, 50.0d);
        this.maxWaitTicks = new SliderSetting("Max Crystal Wait", "Ticks to wait for crystal spawn", 10.0d, 1.0d, 40.0d, 1.0d);
        this.rotationSpeed = new SliderSetting("Aim Speed", "Camera rotation speed", 5.0d, 1.0d, 15.0d, 0.5d);
        this.autoPlace = new BooleanSetting("Auto on Place", "Auto place and explode crystal when obsidian placed", false);
        this.swapBack = new BooleanSetting("Swap Back", "Return item to hand after explosion", true);
        this.rotation = new BooleanSetting("Rotation", "Rotate camera to block/crystal", true);
        this.obsidianKey = new KeyBindSetting("Obsidian Key", "Key to start chain", -1);
        this.obsidianKey.setCallback(this::onObsidianKeyPress);
        this.tickHelper = new TickHelper();
        this.state = State.IDLE; this.obsidianSlot = -1; this.slotToRestore = -1; this.swapSlot = -1;
        this.targetBlock = null; this.placeTicks = 0; this.crystalSlot = 0; this.delayed = false;
        this.aimPos = null; this.lastUpdateTime = 0L; this.hitPos = null; this.obsidianPos = null; this.hitDirection = null;
        addSettings(this.autoPlace, this.delayType, this.placeDelay, this.delayMs, this.maxWaitTicks, this.rotationSpeed, this.swapBack, this.rotation, this.obsidianKey);
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
                case SWAP_TO_OBSIDIAN: swapToObsidian(); break;
                case PLACE_OBSIDIAN: placeObsidian(); break;
                case VERIFY_OBSIDIAN: verifyObsidian(); break;
                case SWAP_TO_CRYSTAL: swapToCrystal(); break;
                case PLACE_CRYSTAL: placeCrystal(); break;
                case WAIT_CRYSTAL: waitForCrystal(); break;
                case ATTACK_CRYSTAL: attackCrystal(); break;
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
        double dt = MathHelper.clamp(this.lastUpdateTime > 0 ? (ct - this.lastUpdateTime) / 1.0E9d : 0.0d, 0.0d, 1.0d);
        this.lastUpdateTime = ct;
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
        this.swapSlot = findSlot(Items.END_CRYSTAL);
        if (this.swapSlot == -1) return ActionResult.PASS;
        this.obsidianSlot = getInventory().selectedSlot; this.slotToRestore = -1; this.targetBlock = tp;
        this.hitPos = null; this.obsidianPos = null; this.hitDirection = null;
        this.placeTicks = 0; this.delayed = false;
        this.aimPos = Vec3d.ofCenter(tp); this.crystalSlot = 0;
        setState(State.VERIFY_OBSIDIAN, this.placeDelay, this.delayMs);
        return ActionResult.PASS;
    }

    private void onObsidianKeyPress() {
        if (this.state != State.IDLE || getScreen() != null) return;
        if (!(getClient().crosshairTarget instanceof BlockHitResult bhr) || bhr.getType() != HitResult.Type.BLOCK) return;
        BlockPos hp = bhr.getBlockPos();
        if (getWorld().getBlockState(hp).isReplaceable() || getWorld().getBlockState(hp).isAir() ||
            getPlayer().getEyePos().distanceTo(Vec3d.ofCenter(hp)) > 5.0d) return;
        this.swapSlot = findSlot(Items.END_CRYSTAL);
        if (this.swapSlot == -1) return;
        Direction side = bhr.getSide();
        BlockPos pp = hp.offset(side);
        if (!isValidPlacement(pp)) return;
        this.obsidianSlot = getInventory().selectedSlot; this.targetBlock = pp;
        this.hitPos = bhr.getPos(); this.obsidianPos = bhr.getBlockPos(); this.hitDirection = side;
        this.placeTicks = 0; this.delayed = false; this.aimPos = this.hitPos;
        if (getPlayer().getMainHandStack().getItem() == Items.OBSIDIAN) { this.state = State.PLACE_OBSIDIAN; }
        else { this.slotToRestore = findSlot(Items.OBSIDIAN); if (this.slotToRestore == -1) reset(); else this.state = State.SWAP_TO_OBSIDIAN; }
    }

    private void swapToObsidian() {
        if (this.slotToRestore < 9) getInventory().selectedSlot = this.slotToRestore;
        else getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.slotToRestore, this.obsidianSlot, SlotActionType.PICKUP, getPlayer());
        setState(State.PLACE_OBSIDIAN, 1);
    }
    private void placeObsidian() {
        if (this.hitPos == null || this.obsidianPos == null || this.hitDirection == null || this.targetBlock == null) { reset(); return; }
        if (getPlayer().getMainHandStack().getItem() != Items.OBSIDIAN) { reset(); return; }
        if (getWorld().getBlockState(this.obsidianPos).isReplaceable() || getWorld().getBlockState(this.obsidianPos).isAir()) { reset(); return; }
        if (!isValidPlacement(this.targetBlock)) { reset(); return; }
        if (getPlayer().getEyePos().distanceTo(Vec3d.ofCenter(this.obsidianPos)) > 5.0d) { reset(); return; }
        this.aimPos = this.hitPos;
        getInteractionManager().interactBlock(getClientPlayer(), Hand.MAIN_HAND, new BlockHitResult(this.hitPos, this.hitDirection, this.obsidianPos, false));
        getPlayer().swingHand(Hand.MAIN_HAND);
        this.hitPos = null; this.obsidianPos = null; this.hitDirection = null; this.placeTicks = 0;
        setState(State.VERIFY_OBSIDIAN, this.placeDelay, this.delayMs);
    }
    private void verifyObsidian() {
        if (this.targetBlock == null) { reset(); return; }
        if (getWorld().getBlockState(this.targetBlock).getBlock() == Blocks.OBSIDIAN) { setState(State.SWAP_TO_CRYSTAL, 0); return; }
        if (++this.placeTicks >= 5) reset(); else setState(State.VERIFY_OBSIDIAN, 1);
    }
    private void swapToCrystal() {
        this.swapSlot = findSlot(Items.END_CRYSTAL);
        if (this.swapSlot == -1) { reset(); return; }
        if (this.swapSlot < 9) getInventory().selectedSlot = this.swapSlot;
        else getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.swapSlot, getInventory().selectedSlot, SlotActionType.PICKUP, getPlayer());
        setState(State.PLACE_CRYSTAL, 1);
    }
    private void placeCrystal() {
        if (this.targetBlock == null) { reset(); return; }
        BlockPos op = findObsidian(this.targetBlock);
        if (op == null) { reset(); return; }
        if (getPlayer().getMainHandStack().getItem() != Items.END_CRYSTAL) { reset(); return; }
        BlockPos cp = op.up();
        if (!getWorld().getBlockState(cp).isAir() || !getWorld().getBlockState(cp.up()).isAir()) { reset(); return; }
        Vec3d pp = Vec3d.ofCenter(op).add(0.0d, 0.5d, 0.0d);
        this.aimPos = pp;
        if (this.rotation.getValue() && !isAimedAt(pp, 5.0d)) { setState(State.PLACE_CRYSTAL, 1); return; }
        getInteractionManager().interactBlock(getClientPlayer(), Hand.MAIN_HAND, new BlockHitResult(pp, Direction.UP, op, false));
        getPlayer().swingHand(Hand.MAIN_HAND);
        this.placeTicks = 0; setState(State.WAIT_CRYSTAL, 1);
    }
    private void waitForCrystal() {
        if (this.targetBlock == null) { reset(); return; }
        EndCrystalEntity crystal = findCrystal(this.targetBlock);
        if (crystal != null) { this.aimPos = crystal.getPos(); setState(State.ATTACK_CRYSTAL, 0); return; }
        if (++this.placeTicks >= this.maxWaitTicks.getIntValue()) reset(); else setState(State.WAIT_CRYSTAL, 1);
    }
    private void attackCrystal() {
        if (this.targetBlock == null) { reset(); return; }
        EndCrystalEntity crystal = findCrystal(this.targetBlock);
        if (crystal == null) { reset(); return; }
        this.aimPos = crystal.getPos();
        if (this.rotation.getValue() && !isAimedAt(crystal.getPos(), 5.0d)) { setState(State.ATTACK_CRYSTAL, 1); return; }
        getInteractionManager().attackEntity(getPlayer(), crystal);
        getPlayer().swingHand(Hand.MAIN_HAND);
        setState(this.swapBack.getValue() ? State.SWAP_BACK : State.DONE, 1);
    }
    private void swapBackSlot() {
        if (this.slotToRestore != -1 && this.slotToRestore >= 9)
            getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.slotToRestore, getInventory().selectedSlot, SlotActionType.PICKUP, getPlayer());
        getInventory().selectedSlot = this.obsidianSlot;
        this.aimPos = null; setState(State.DONE, 0);
    }
    private void reset() {
        this.state = State.IDLE; this.tickHelper.reset();
        this.obsidianSlot = -1; this.slotToRestore = -1; this.swapSlot = -1;
        this.targetBlock = null; this.placeTicks = 0; this.crystalSlot = 0;
        this.delayed = false; this.aimPos = null; this.lastUpdateTime = 0L;
        this.hitPos = null; this.obsidianPos = null; this.hitDirection = null;
    }

    private int findSlot(Item item) { for (int i = 0; i < getPlayer().getInventory().size(); i++) if (getPlayer().getInventory().getStack(i).getItem() == item) return i; return -1; }
    private BlockPos findObsidian(BlockPos pos) { if (getWorld().getBlockState(pos).getBlock() == Blocks.OBSIDIAN) return pos; if (getWorld().getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) return pos.down(); return null; }
    private EndCrystalEntity findCrystal(BlockPos pos) { for (EndCrystalEntity c : getWorld().getEntitiesByClass(EndCrystalEntity.class, new Box(pos).expand(2.0), e -> true)) { if (!c.isRemoved() && c.isAlive()) { BlockPos cp = c.getBlockPos(); if (cp.equals(pos.up()) || cp.down().equals(pos)) return c; } } return null; }
    private boolean isValidPlacement(BlockPos pos) { return getWorld().getBlockState(pos).isAir() && !getPlayer().getBoundingBox().intersects(new Box(pos)); }
    private boolean isAimedAt(Vec3d t, double tol) { return Math.toDegrees(Math.acos(MathHelper.clamp(getPlayer().getRotationVector().dotProduct(t.subtract(getPlayer().getEyePos()).normalize()), -1.0d, 1.0d))) <= tol; }
    private boolean isTickMode() { return "Tick-based".equals(this.delayType.getFirst()); }

    private void setState(State ns, int d) { this.state = ns; if (d <= 0) { this.delayed = false; this.tickHelper.reset(); return; } this.delayed = true; if (isTickMode()) { this.tickHelper.setTickMode(false); this.tickHelper.setTickDelay(d); } else { this.tickHelper.setTickMode(true); this.tickHelper.setMsDelay((long)d * 50); } }
    private void setState(State ns, SliderSetting dt, SliderSetting dm) { this.state = ns; if (isTickMode()) { int t = dt.getIntValue(); if (t <= 0) { this.delayed = false; this.tickHelper.reset(); return; } this.delayed = true; this.tickHelper.setTickMode(false); this.tickHelper.setTickDelay(t); } else { int ms = dm.getIntValue(); if (ms <= 0) { this.delayed = false; this.tickHelper.reset(); return; } this.delayed = true; this.tickHelper.setTickMode(true); this.tickHelper.setMsDelay(ms); } }
}
