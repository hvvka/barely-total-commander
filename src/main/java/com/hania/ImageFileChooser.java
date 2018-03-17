package com.hania;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ImageFileChooser extends JFileChooser {

    private static final String DIRECTORY_PATH = "/Volumes/SD/obrazki/Tapety/paintings";

    public ImageFileChooser() {
        super(DIRECTORY_PATH);

        this.addChoosableFileFilter(new FileNameExtensionFilter("JPG", Utils.JPG));
        this.addChoosableFileFilter(new FileNameExtensionFilter("PNG", Utils.PNG));
        this.addChoosableFileFilter(new FileNameExtensionFilter("JPEG", Utils.JPEG));
        this.setAcceptAllFileFilterUsed(false);

        this.setSelectedFile(new File(DIRECTORY_PATH));
    }
}
