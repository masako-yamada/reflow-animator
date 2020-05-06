package com.shazam.android.widget.text.reflow;

public class ColorUtils {
    public static double[] colorToLAB(int color) {
        final double[] result = new double[3];
        androidx.core.graphics.ColorUtils.colorToLAB(color, result);
        return result;
    }

    public static int labToColor(double[] lab) {
        return androidx.core.graphics.ColorUtils.LABToColor(lab[0], lab[1], lab[2]);
    }

    public static void blendLab(double[] startColor, double[] endColor, float progress, double[] out) {
        androidx.core.graphics.ColorUtils.blendLAB(startColor, endColor, progress, out);
    }
}
