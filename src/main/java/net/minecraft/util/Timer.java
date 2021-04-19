package net.minecraft.util;

//TODO: Client
public class Timer
{
    public float renderPartialTicks;
    public float elapsedPartialTicks;
    private long lastSyncSysClock;
    private final float tickLength;
    public static float timerSpeed = 1.0F;

    public Timer(float ticks, long lastSyncSysClock)
    {
        this.tickLength = 1000.0F / ticks;
        this.lastSyncSysClock = lastSyncSysClock;
    }

    public int getPartialTicks(long gameTime)
    {
        this.elapsedPartialTicks = (float)(gameTime - this.lastSyncSysClock) * this.timerSpeed / this.tickLength;
        this.lastSyncSysClock = gameTime;
        this.renderPartialTicks += this.elapsedPartialTicks;
        int i = (int)this.renderPartialTicks;
        this.renderPartialTicks -= (float)i;
        return i;
    }
}
