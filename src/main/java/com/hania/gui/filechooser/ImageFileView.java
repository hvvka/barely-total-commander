package com.hania.gui.filechooser;

import java.awt.*;
import java.io.File;
import java.lang.ref.WeakReference;
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
    public String getTypeDescription(File file) {
        String extension = Utils.getExtension(file);
        String typeDescription = null;

        if (extension != null) {
            if (extension.equals(Utils.JPEG) ||
                    extension.equals(Utils.JPG)) {
                typeDescription = "JPEG Image";
            } else if (extension.equals(Utils.GIF)){
                typeDescription = "GIF Image";
            } else if (extension.equals(Utils.TIFF) ||
                    extension.equals(Utils.TIF)) {
                typeDescription = "TIFF Image";
            } else if (extension.equals(Utils.PNG)){
                typeDescription = "PNG Image";
            }
        }
        return typeDescription;
    }

    @Override
    public Icon getIcon(File file) {
//        String extension = Utils.getExtension(file);
//        Icon icon = null;
//
//        if (extension != null) {
//            if (extension.equals(Utils.JPEG) ||
//                    extension.equals(Utils.JPG)) {
//                icon = jpgIcon;
//            } else if (extension.equals(Utils.GIF)) {
//                icon = gifIcon;
//            } else if (extension.equals(Utils.TIFF) ||
//                    extension.equals(Utils.TIF)) {
//                icon = tiffIcon;
//            } else if (extension.equals(Utils.PNG)) {
//                icon = pngIcon;
//            }
//        }
//        return icon;

        if (file.isDirectory()) {
            return null;
        } else {
            return getWeakIconReference(file);
        }
    }

    private Icon getWeakIconReference(File file) {
        WeakReference<ImageIcon> wr = new WeakReference<>(new ImageIcon(file.getPath()));
        ImageIcon icon = wr.get();
//        ImageIcon icon = new ImageIcon(file.getPath());
        if (icon != null) {
            return getScaledIcon(icon);
        }

        return null;
    }

    private Icon getScaledIcon(ImageIcon icon) {
        if (icon.getIconWidth() > 48) {
            return new ImageIcon(icon.getImage().getScaledInstance(48, -1, Image.SCALE_DEFAULT));
        } else { //no need to miniaturize
            return icon;
        }
    }
}
