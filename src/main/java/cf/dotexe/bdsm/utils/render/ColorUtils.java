package cf.dotexe.bdsm.utils.render;

import java.awt.Color;
import java.util.Random;

public class ColorUtils {
	
	public static final int[] RAINBOW_COLORS = new int[] { 16711680, 16728064, 16744192, 16776960, 8453888, 65280, 65535, 33023, 255 };
	  
	private static Random random = new Random();
	  
	public static int generateColor() {
		float hue = random.nextFloat();
		float saturation = random.nextInt(5000) / 10000.0F + 0.5F;
	    float brightness = random.nextInt(5000) / 10000.0F + 0.5F;
	    return Color.HSBtoRGB(hue, saturation, brightness);
	}
	  
	public static Color blend(Color color1, Color color2, float ratio) {
	    if (ratio < 0.0F)
	    	return color2; 
	    if (ratio > 1.0F)
	    	return color1; 
	    float ratio2 = 1.0F - ratio;
	    float[] rgb1 = new float[3];
	    float[] rgb2 = new float[3];
	    color1.getColorComponents(rgb1);
	    color2.getColorComponents(rgb2);
	    Color color3 = new Color(rgb1[0] * ratio + rgb2[0] * ratio2, rgb1[1] * ratio + rgb2[1] * ratio2, rgb1[2] * ratio + rgb2[2] * ratio2);
	    return color3;
	}
	
	public static Color setRainbow(long offset, float fade) {
		final float hue = (System.nanoTime() * -5L + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 0.3f, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade);
	}
	
}
