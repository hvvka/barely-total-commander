package com.hania.gui.filechooser;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ImageFileView extends FileView {

    private ImageIcon jpgIcon = Utils.createImageIcon("icons/jpg.icns");
    private ImageIcon gifIcon = Utils.createImageIcon("icons/gif.icns");
    private ImageIcon tiffIcon = Utils.createImageIcon("icons/tiff.icns");
    private ImageIcon pngIcon = Utils.createImageIcon("icons/png.icns");

    @Override
    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }

    @Override
    public String getDescription(File f) {
        return null; //let the L&F FileView figure this out
    }

    @Override
    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }

    @Override
    public String getTypeDescription(File f) {
        String extension = Utils.getExtension(f);
        String type = null;

        if (extension != null) {
            if (extension.equals(Utils.JPEG) ||
                    extension.equals(Utils.JPG)) {
                type = "JPEG Image";
            } else if (extension.equals(Utils.GIF)){
                type = "GIF Image";
            } else if (extension.equals(Utils.TIFF) ||
                    extension.equals(Utils.TIF)) {
                type = "TIFF Image";
            } else if (extension.equals(Utils.PNG)){
                type = "PNG Image";
            }
        }
        return type;
    }

    @Override
    public Icon getIcon(File f) {
        String extension = Utils.getExtension(f);
        Icon icon = null;

        if (extension != null) {
            if (extension.equals(Utils.JPEG) ||
                    extension.equals(Utils.JPG)) {
                icon = jpgIcon;
            } else if (extension.equals(Utils.GIF)) {
                icon = gifIcon;
            } else if (extension.equals(Utils.TIFF) ||
                    extension.equals(Utils.TIF)) {
                icon = tiffIcon;
            } else if (extension.equals(Utils.PNG)) {
                icon = pngIcon;
            }
        }
        return icon;
    }
}
