package com.hania.plugins;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Plugin {

    BufferedImage bufferedImage;

    public BufferedImage convertIcon(String path) {
        readImage(path);
        return bufferedImage;
    }

    void readImage(String path) {
        try {
            File file = new File(path);
            bufferedImage = ImageIO.read(file);
        } catch(IOException e) {
            System.err.println("Reading buffered image exception!");
        }
    }
}
