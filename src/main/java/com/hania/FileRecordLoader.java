package com.hania;

import com.hania.model.CachedImage;
import com.hania.model.FileRecord;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecordLoader {

    private final ImageFileChooser imageFileChooser;

    private FileRecord fileRecord;

    public FileRecordLoader(ImageFileChooser imageFileChooser) {
        this.imageFileChooser = imageFileChooser;

        if(imageFileChooser.getSelectedFile() == null) return;

        File file = imageFileChooser.getSelectedFile();
        if(file.isDirectory()) {
            createFileRecord(file);
        }
    }

    public JList<File> getFileRecordList() {
        JList<File> list = new JList<>(imageFileChooser.getSelectedFile()
                .listFiles((dir, name) -> name.matches("^[^.].*\\.(gif|jpg|jpeg|tiff|png)$")));
        list.setCellRenderer(new ImageListRenderer());
        return list;
    }

    private void createFileRecord(File file) {
        File[] files = file.listFiles((dir, name) -> name.matches("^[^.].*\\.(gif|jpg|jpeg|tiff|png)$"));
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

    class ImageListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(fileRecord.getRecords().get(value).get().getImage());
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;
        }
    }
}
