package com.hania.model;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecord {

    private ConcurrentMap<String, WeakReference<CachedImage>> records;

    private final JList<Object> list;

    public FileRecord(List<File> files) {
        records = setRecords(files);

        SortedMap<String, WeakReference<CachedImage>> sortedImageMap = new TreeMap<>(records);
        list = new JList<>(sortedImageMap.keySet().toArray());

        list.setCellRenderer(new ImageListRenderer());
    }

    private ConcurrentMap<String, WeakReference<CachedImage>> setRecords(List<File> files) {
        ConcurrentMap<String, WeakReference<CachedImage>> map = new ConcurrentHashMap<>();
        for (File file : files) {
            WeakReference<CachedImage> weakCachedImage = getCachedImage(file);
            if (weakCachedImage.get() == null) {
                System.err.println("Lack of reference");
            } else {
                addRecordToMap(map, file, weakCachedImage);
            }
        }
        return map;
    }

    private void addRecordToMap(ConcurrentMap<String, WeakReference<CachedImage>> map, File file,
                                WeakReference<CachedImage> weakCachedImage) {
        try {
            map.put(file.getCanonicalPath(), weakCachedImage);
        } catch (IOException e) {
            System.err.println("Couldn't add record to map!");
        }
    }

    public Map<String, WeakReference<CachedImage>> getRecords() {
        return records;
    }

    public void putFileRecord(String path, CachedImage cachedImage) {
        WeakReference<CachedImage> imageWeakReference = new WeakReference<>(cachedImage);
        records.put(path, imageWeakReference);
    }

    public JList<Object> getList() {
        return list;
    }

    private WeakReference<CachedImage> getCachedImage(File file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Achtung! Loading image exception.");
        }
        BufferedImage scaledImg = Scalr.resize(Objects.requireNonNull(img), 250);
        return new WeakReference<>(new CachedImage(file.getName(), new ImageIcon(scaledImg)));
    }

    class ImageListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (records.get(value.toString()).get() == null) {
                System.err.println("Weak reference is null.");
                records.put((String) value, getCachedImage(new File(value.toString())));
            }

            label.setIcon(records.get(value.toString()).get().getImage());
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;
        }
    }
}
