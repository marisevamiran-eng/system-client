package dev.mark.system.util;

public class TickHelper {
    private boolean tickMode; // true = ms mode, false = tick mode
    private long tickDelay;
    private long msDelay;
    private long lastTickTime;
    private int tickCount;
    private boolean initialized;

    public TickHelper() {
        this.tickMode = false;
        this.tickDelay = 0;
        this.msDelay = 0;
        this.lastTickTime = 0;
        this.tickCount = 0;
        this.initialized = false;
    }

    public void reset() {
        this.tickCount = 0;
        this.lastTickTime = System.currentTimeMillis();
        this.initialized = true;
    }

    public void setTickMode(boolean msMode) {
        this.tickMode = msMode;
    }

    public void setTickDelay(long ticks) {
        this.tickDelay = ticks;
    }

    public void setMsDelay(long ms) {
        this.msDelay = ms;
    }

    public void tick() {
        if (!this.initialized) {
            this.lastTickTime = System.currentTimeMillis();
            this.initialized = true;
        }
        this.tickCount++;
    }

    public boolean isReady() {
        if (!this.initialized) {
            this.lastTickTime = System.currentTimeMillis();
            this.initialized = true;
            return false;
        }
        if (this.tickMode) {
            return System.currentTimeMillis() - this.lastTickTime >= this.msDelay;
        } else {
            return this.tickCount >= this.tickDelay;
        }
    }
}
