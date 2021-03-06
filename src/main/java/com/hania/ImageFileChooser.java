package com.hania;

import javax.swing.*;
import java.io.File;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ImageFileChooser extends JFileChooser {

    private static final String DIRECTORY_PATH = "/";

    public ImageFileChooser() {
        super(DIRECTORY_PATH);
        this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.setSelectedFile(new File(DIRECTORY_PATH));
    }
}
