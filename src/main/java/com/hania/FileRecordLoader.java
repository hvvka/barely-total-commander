package com.hania;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecordLoader {

    private FileRecord fileRecord;

    public FileRecordLoader(ImageFileChooser imageFileChooser, PluginType pluginType) {

        if(imageFileChooser.getSelectedFile() == null) return;

        File file = imageFileChooser.getSelectedFile();
        if(file.isDirectory()) {
            createFileRecord(file, pluginType);
        }
    }

    public JList<Object> getFileRecordList() {
        return fileRecord.getList();
    }

    private void createFileRecord(File file, PluginType pluginType) {
        File[] files = file.listFiles((dir, name) -> name.matches("^[^.].*\\.(gif|jpg|jpeg|tiff|png)$"));
        if (files != null) {
            fileRecord = new FileRecord(Arrays.asList(files), pluginType);
        }
    }
}
