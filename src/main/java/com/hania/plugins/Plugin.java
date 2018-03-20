package com.hania.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Plugin {

    private static final Logger LOG = LoggerFactory.getLogger(Plugin.class);

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
            LOG.error("Reading buffered image exception!", e);
        }
    }
}
