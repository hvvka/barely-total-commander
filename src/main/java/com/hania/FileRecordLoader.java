package com.hania;

import com.hania.model.CachedImage;
import com.hania.model.FileRecord;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecordLoader {

    private final ImageFileChooser imageFileChooser;

    private FileRecord fileRecord;

    public FileRecordLoader(ImageFileChooser imageFileChooser) {
        this.imageFileChooser = imageFileChooser;

        File file = imageFileChooser.getSelectedFile();
        if(file.isDirectory()) {
            createFileRecord(file);
        }
    }

    public FileRecord getFileRecord() {
        return fileRecord;
    }

    private void createFileRecord(File file) {
        File[] files = file.listFiles((FileFilter) imageFileChooser.getFileFilter());
        if (files != null) {
            fillFileRecord(files);
        }
    }

    private void fillFileRecord(File[] files) {
        fileRecord = new FileRecord();
        for(File file : files) {
            addOneRecord(file);
        }
    }

    private void addOneRecord(File file) {
        try {
            fileRecord.putFileRecord(file.getAbsolutePath(), new CachedImage(file.getName(), new ImageIcon(ImageIO.read(file))));
        } catch (IOException e) {
            System.out.println("Achtung! Loading image exception.");
        }
    }
}
