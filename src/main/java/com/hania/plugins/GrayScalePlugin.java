package com.hania.plugins;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class GrayScalePlugin extends Plugin {

    private BufferedImage grayScaleBufferedImage;

    @Override
    public BufferedImage convertIcon(String path) {
        this.readImage(path);
        DataBufferByte dataBufferByte = null;
        try {
            dataBufferByte = (DataBufferByte) ImageIO.read(new File(path))
                    .getRaster()
                    .getDataBuffer();
        } catch (IOException e) {
            System.out.println("DataBufferByte strikes again!");
        }

        if(dataBufferByte != null) {
            byte[] byteBufferedImage = dataBufferByte.getData();
            int[] intBufferedImage = new int[byteBufferedImage.length];
            for (int i = 0; i < byteBufferedImage.length; i++) intBufferedImage[i] = byteBufferedImage[i] & 0xff;

            int[] bitMasks = new int[]{0xFF0000, 0xFF00, 0xFF, 0xFF000000};
            SinglePixelPackedSampleModel sm = new SinglePixelPackedSampleModel(
                    DataBuffer.TYPE_INT, bufferedImage.getWidth(), bufferedImage.getHeight(), bitMasks);
            DataBufferInt db = new DataBufferInt(intBufferedImage, intBufferedImage.length);
            WritableRaster wr = Raster.createWritableRaster(sm, db, new Point());
            grayScaleBufferedImage = new BufferedImage(ColorModel.getRGBdefault(), wr, false, null);
//            grayScaleBufferedImage = new BufferedImage();
        }
        return grayScaleBufferedImage; //fixme
    }
}
