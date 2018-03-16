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
        if (file.isDirectory()) {
            return null;
        } else {
            return getWeakIconReference(file);
        }
    }

    private Icon getWeakIconReference(File file) {
        WeakReference<ImageIcon> wr = new WeakReference<>(new ImageIcon(file.getPath()));
        ImageIcon icon = wr.get();
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
