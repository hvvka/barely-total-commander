package com.hania.model;

import com.hania.PluginGenerator;
import com.hania.PluginType;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecord extends SwingWorker<Integer, String> {

    private static final int IMAGE_SIZE = 250;

    private ConcurrentMap<String, WeakReference<CachedImage>> records;

    private final JList<Object> list;

    private PluginGenerator pluginGenerator;

    private List<File> files;

    private PluginType pluginType;

    public FileRecord(List<File> files) {
        this.files = files;
        pluginType = PluginType.NO_PLUGIN;
        records = setRecords();

        SortedMap<String, WeakReference<CachedImage>> sortedImageMap = new TreeMap<>(records);
        list = new JList<>(sortedImageMap.keySet().toArray());

        list.setCellRenderer(new ImageListRenderer());
    }

    private ConcurrentMap<String, WeakReference<CachedImage>> setRecords() {
        ConcurrentMap<String, WeakReference<CachedImage>> map = new ConcurrentHashMap<>();
        for (File file : this.files) {
            WeakReference<CachedImage> weakCachedImage;
            weakCachedImage = getCachedImage(file);
            addRecordToMap(map, file, weakCachedImage);
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

    public void applyPlugin(PluginType pluginType) {
        pluginGenerator = new PluginGenerator();
        this.pluginType = pluginType;
        setRecords();
    }

    public JList<Object> getList() {
        return list;
    }

    private WeakReference<CachedImage> getCachedImage(File file) {
        BufferedImage scaledImage = getBufferedImage(file);
        return new WeakReference<>(new CachedImage(file.getName(), new ImageIcon(scaledImage)));
    }

    private BufferedImage getBufferedImage(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Achtung! Loading image exception.");
        }

        bufferedImage = convertImageWithPlugin(file, bufferedImage);
        return Scalr.resize(Objects.requireNonNull(bufferedImage), IMAGE_SIZE);
    }

    private BufferedImage convertImageWithPlugin(File file, BufferedImage bufferedImage) {
        if (pluginType != PluginType.NO_PLUGIN) {
            Object customPlugin = pluginGenerator.getPlugin(pluginType);
            bufferedImage = pluginGenerator.invokeConvertIconMethod(customPlugin, file.getAbsolutePath());
            System.out.println("Applying " + pluginType + " plugin to: " + file.getName());
        }
        return bufferedImage;
    }

    @Override
    protected Integer doInBackground() {
        // Start
        publish("Start");
        setProgress(1);

        // More work was done
        publish("More work was done");
        setProgress(10);

        // Complete
        publish("Complete");
        setProgress(100);
        return 1;
    }

    @Override
    protected void process(List<String> chunks) {
        // Messages received from the doInBackground() (when invoking the publish() method)
    }

    class ImageListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (records.get(value.toString()).get() == null) {
                System.err.println("Weak reference is null. Loading an image...");
                records.put((String) value, getCachedImage(new File(value.toString())));
            }

            label.setIcon(records.get(value.toString()).get().getImage());
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;
        }
    }
}
