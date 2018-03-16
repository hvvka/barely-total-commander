package com.hania.gui.filechooser;

import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Utils {

    public static final String JPEG = "jpeg";
    public static final String JPG = "jpg";
    public static final String GIF = "gif";
    public static final String TIFF = "tiff";
    public static final String TIF = "tif";
    public static final String PNG = "png";

    private Utils() {
        // util
    }

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        URL imageURL = Utils.class.getClassLoader().getResource(path);
        if (imageURL != null) {
            return new ImageIcon(imageURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
