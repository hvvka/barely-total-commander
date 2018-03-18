package com.hania.plugins;

import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class NegativePlugin extends Plugin {

    private BufferedImage negativeBufferedImage;

    @Override
    public BufferedImage convertIcon(String path) {
        this.readImage(path);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
//        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
//        op.filter(negativeBufferedImage, negativeBufferedImage);
        negativeBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        return bufferedImage; //fixme
    }
}
