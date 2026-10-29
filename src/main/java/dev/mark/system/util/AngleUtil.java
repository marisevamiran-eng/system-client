package dev.mark.system.util;

public class AngleUtil {
    public static double normalizeAngle(double angle) {
        double d = angle % 360.0;
        if (d >= 180.0) d -= 360.0;
        if (d < -180.0) d += 360.0;
        return d;
    }

    public static double getGCD() {
        double f = MinecraftClientAccessor.getOptions().getMouseSensitivity().getValue() * 0.6 + 0.2;
        double g = f * f * f * 8.0;
        return g * 0.15;
    }

    public static double roundToGCD(double value, double gcd) {
        return Math.round(value / gcd) * gcd;
    }

    // Accessor helper
    private static class MinecraftClientAccessor {
        static net.minecraft.client.option.GameOptions getOptions() {
            return net.minecraft.client.MinecraftClient.getInstance().options;
        }
    }
}
