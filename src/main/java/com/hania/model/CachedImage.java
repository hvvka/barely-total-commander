package com.hania.model;

import javax.swing.*;
import java.awt.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CachedImage {

    private final ImageIcon image;

    private final String filename;

    public CachedImage() {
        image = new ImageIcon();
        filename = "";
    }

    public CachedImage(String filename, ImageIcon image) {
        this.filename = filename;
        this.image = image;
    }

    public ImageIcon getImage() {
        return image;
    }
}
