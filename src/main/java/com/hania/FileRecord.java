package com.hania;

import com.hania.model.CachedImage;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecord extends SwingWorker<Void, ConcurrentMap.Entry<String, WeakReference<CachedImage>>> {

    private static final int IMAGE_SIZE = 250;
    private static final Logger LOG = LoggerFactory.getLogger(FileRecord.class);

    private ConcurrentMap<String, WeakReference<CachedImage>> records;
    private JList<Object> list;
    private DefaultListModel<Object> model;
    private PluginGenerator pluginGenerator;
    private List<File> files;
    private PluginType pluginType;

    FileRecord(List<File> files, PluginType pluginType) {
        this.files = files;
        pluginGenerator = new PluginGenerator();
        this.pluginType = pluginType;

        records = new ConcurrentHashMap<>();
        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setCellRenderer(new ImageListRenderer());

        execute();
    }

    private static void failIfInterrupted() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Interrupted while searching files");
        }
    }

    private ConcurrentMap.Entry<String, WeakReference<CachedImage>> getRecord(
            File file, WeakReference<CachedImage> weakCachedImage) {
        try {
            return new ConcurrentHashMap.SimpleEntry<>(file.getCanonicalPath(), weakCachedImage);
        } catch (IOException e) {
            LOG.error("Couldn't add record to map!", e);
            return null;
        }
    }

    JList<Object> getList() {
        return list;
    }

    private WeakReference<CachedImage> getCachedImage(File file) {
        BufferedImage scaledImage = getBufferedImage(file);
        return new WeakReference<>(new CachedImage(file.getName(), new ImageIcon(scaledImage)));
    }

    private BufferedImage getBufferedImage(File file) {
        Object customPlugin = pluginGenerator.getPlugin(pluginType);
        BufferedImage bufferedImage = pluginGenerator.invokeConvertIconMethod(customPlugin, file.getAbsolutePath());
        LOG.info("Applying {} plugin to: {}", pluginType, file.getName());
        return Scalr.resize(Objects.requireNonNull(bufferedImage), IMAGE_SIZE);
    }

    private void addRecordsToMap(List<ConcurrentMap.Entry<String, WeakReference<CachedImage>>> entries) {
        for (ConcurrentMap.Entry<String, WeakReference<CachedImage>> entry : entries) {
            records.put(entry.getKey(), entry.getValue());
            model.addElement(entry.getKey());
        }
    }

    @Override
    protected Void doInBackground() throws InterruptedException {
        for (File file : this.files) {
            publish(getRecord(file, getCachedImage(file)));
            failIfInterrupted();
        }
        return null;
    }

    @Override
    protected void process(List<ConcurrentMap.Entry<String, WeakReference<CachedImage>>> chunks) {
        super.process(chunks);
        addRecordsToMap(chunks);
    }

    class ImageListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (records.get(value.toString()).get() == null) {
                LOG.warn("Weak reference is null. Loading an image...");
                records.put((String) value, getCachedImage(new File(value.toString())));
            }

            label.setIcon(records.get(value.toString()).get().getImage());
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;
        }
    }
}
