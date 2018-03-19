package com.hania.plugins;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class GrayScalePlugin extends Plugin {

    @Override
    public BufferedImage convertIcon(String path) {
        this.readImage(path);

        WritableRaster raster = bufferedImage.getRaster();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                pixels[0] = pixels[1] = pixels[2] = (pixels[0] + pixels[1] + pixels[2]) / 3;
                raster.setPixel(x, y, pixels);
            }
        }

        return this.bufferedImage;

    }
}
