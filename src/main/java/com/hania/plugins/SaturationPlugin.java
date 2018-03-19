package com.hania.plugins;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class SaturationPlugin extends Plugin {

    @Override
    public BufferedImage convertIcon(String path) {
        this.readImage(path);
        WritableRaster raster = bufferedImage.getRaster();
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                float[] hsb = Color.RGBtoHSB(pixels[0], pixels[1], pixels[2], null);

                hsb[1] = hsb[1] + 0.511f > 1f ? 1f : hsb[1] + 0.511f;
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]); //hue, saturation, brightness

                pixels[0] = (rgb >> 16) & 0xFF;
                pixels[1] = (rgb >> 8) & 0xFF;
                pixels[2] = rgb & 0xFF;
                raster.setPixel(x, y, pixels);
            }
        }
        return this.bufferedImage;
    }
}

