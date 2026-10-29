package dev.mark.system.module.combat;

import dev.mark.system.enums.Category;
import dev.mark.system.module.Module;
import dev.mark.system.setting.BooleanSetting;
import dev.mark.system.setting.KeyBindSetting;
import dev.mark.system.setting.ListSetting;
import dev.mark.system.setting.SliderSetting;
import dev.mark.system.util.AngleUtil;
import java.util.Arrays;
import java.util.Collections;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class WebPlacerModule extends Module {
    private Vec3d targetPos;
    private int placeCount;
    private SliderSetting rotationSpeed;
    private BlockPos targetBlock;
    private BooleanSetting rotation;
    private KeyBindSetting bindKey;
    private ListSetting amount;
    private SliderSetting delay;
    private int delayTicks;
    private int tempSlot;
    private State state;
    private int webSlot;
    private long lastRotationTime;
    private int webInventorySlot;

    private enum State { IDLE, SWAP_SLOT, PLACE, SWAP_BACK }

    public WebPlacerModule() {
        super("WebPlacer", "Automatically places cobwebs by keybind", Category.COMBAT);
        this.bindKey = new KeyBindSetting("Key", "Press to place cobwebs", -1);
        this.bindKey.setCallback(this::startPlacement);
        this.amount = new ListSetting("Amount", "How many webs to place", Arrays.asList("One", "Two"), Collections.singletonList("One"), false);
        this.rotation = new BooleanSetting("Rotation", "Rotate camera to block", true);
        this.rotationSpeed = new SliderSetting("Aim Speed", "Camera rotation speed", 5.0d, 1.0d, 15.0d, 0.5d);
        this.delay = new SliderSetting("Delay", "Delay between stages (ticks)", 1.0d, 0.0d, 10.0d, 1.0d);
        this.state = State.IDLE; this.delayTicks = 0; this.tempSlot = -1; this.webSlot = -1;
        this.webInventorySlot = -1; this.lastRotationTime = 0L; this.placeCount = 0;
        addSettings(this.bindKey, this.amount, this.rotation, this.rotationSpeed, this.delay);
    }

    @Override public void onEnable() { resetState(); }
    @Override public void onDisable() { resetState(); }

    @Override
    public void onEndTick() {
        if (!hasPlayerAndWorld() || this.state == State.IDLE) return;
        if (this.delayTicks > 0) { this.delayTicks--; return; }
        String sel = this.amount.getFirst();
        switch (this.state) {
            case SWAP_SLOT:
                int wh = findWebInHotbar();
                if (wh != -1) { this.webSlot = wh; getInventory().selectedSlot = wh; }
                else {
                    this.webInventorySlot = findWebInInventory();
                    if (this.webInventorySlot == -1) { resetState(); break; }
                    this.webSlot = (this.tempSlot + 1) % 9;
                    if (this.webSlot == this.tempSlot) this.webSlot = (this.tempSlot + 2) % 9;
                    getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.webInventorySlot, this.webSlot, SlotActionType.PICKUP, getPlayer());
                    getInventory().selectedSlot = this.webSlot;
                }
                setState(State.PLACE); break;
            case PLACE:
                if (this.targetBlock == null || !getWorld().getBlockState(this.targetBlock).isAir()) { resetState(); }
                else if (this.rotation.getValue() && !isAimedAt(this.targetPos, 5.0d)) { this.delayTicks = 1; }
                else {
                    getInteractionManager().interactBlock(getClientPlayer(), Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(this.targetBlock), Direction.UP, this.targetBlock.down(), false));
                    getPlayer().swingHand(Hand.MAIN_HAND);
                    setState(State.SWAP_BACK);
                } break;
            case SWAP_BACK:
                if (this.webInventorySlot != -1)
                    getInteractionManager().clickSlot(getPlayer().currentScreenHandler.syncId, this.webInventorySlot, this.webSlot, SlotActionType.PICKUP, getPlayer());
                getInventory().selectedSlot = this.tempSlot;
                this.placeCount++;
                if ("Two".equals(sel) && this.placeCount < 2 && hasWebs()) {
                    BlockPos nb = getTargetBlock();
                    if (nb != null) {
                        this.targetBlock = nb; this.targetPos = Vec3d.ofCenter(this.targetBlock).add(0.0d, -0.5d, 0.0d);
                        this.tempSlot = getInventory().selectedSlot; this.webSlot = -1; this.webInventorySlot = -1;
                        setState(State.SWAP_SLOT); break;
                    }
                }
                resetState(); break;
        }
    }

    @Override
    public void onRenderAfterEntities(WorldRenderContext context) {
        if (this.state == State.IDLE || this.targetPos == null || !this.rotation.getValue() || !hasPlayerAndWorld()) return;
        long cn = System.nanoTime();
        double dt = MathHelper.clamp(this.lastRotationTime > 0 ? (cn - this.lastRotationTime) / 1.0E9d : 0.0d, 0.0d, 1.0d);
        this.lastRotationTime = cn;
        Vec3d eye = getPlayer().getEyePos();
        double dx = this.targetPos.x - eye.x, dy = this.targetPos.y - eye.y, dz = this.targetPos.z - eye.z;
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

    private void startPlacement() {
        if (this.state == State.IDLE && hasPlayerAndWorld()) {
            this.targetBlock = getTargetBlock();
            if (this.targetBlock != null && hasWebs()) {
                this.placeCount = 0; this.targetPos = Vec3d.ofCenter(this.targetBlock).add(0.0d, -0.5d, 0.0d);
                this.tempSlot = getInventory().selectedSlot; setState(State.SWAP_SLOT);
            }
        }
    }
    private BlockPos getTargetBlock() {
        if (getClient().crosshairTarget instanceof BlockHitResult hit) return hit.getBlockPos().up();
        return null;
    }
    private int findWebInHotbar() { for (int i = 0; i < 9; i++) if (getInventory().getStack(i).getItem() == Items.COBWEB) return i; return -1; }
    private int findWebInInventory() { for (int i = 9; i < getInventory().size(); i++) if (getInventory().getStack(i).getItem() == Items.COBWEB) return i; return -1; }
    private boolean hasWebs() { return findWebInHotbar() != -1 || findWebInInventory() != -1; }
    private void resetState() { this.state = State.IDLE; this.delayTicks = 0; this.tempSlot = -1; this.webSlot = -1; this.webInventorySlot = -1; this.targetBlock = null; this.targetPos = null; this.lastRotationTime = 0L; this.placeCount = 0; }
    private void setState(State ns) { this.state = ns; this.delayTicks = (int)this.delay.getValue(); }
    private boolean isAimedAt(Vec3d t, double tol) { return Math.toDegrees(Math.acos(MathHelper.clamp(getPlayer().getRotationVector().dotProduct(t.subtract(getPlayer().getEyePos()).normalize()), -1.0d, 1.0d))) <= tol; }
}
