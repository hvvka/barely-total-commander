package com.hania.gui.filechooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.imgscalr.Scalr;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecord {

    private final Map<String, ImageIcon> imageMap;
    private final JList list;
    private static final int ICON_SIZE = 200;

    public FileRecord(List<String> nameList) {
        imageMap = createImageMap(nameList);

        list = new JList(nameList.toArray());
        list.setCellRenderer(new ImageListRenderer());
    }

    private Map<String, ImageIcon> createImageMap(List<String> nameList) {
        Map<String, ImageIcon> map = new HashMap<>();

        try {
            for (String name : nameList) {
                BufferedImage img = ImageIO.read(new File(name));
//                BufferedImage scaledImg = Scalr.resize(img, ICON_SIZE);
                WeakReference wr = new WeakReference(new ImageIcon(img));
                //todo: check if is null
                //sublist
                //concurrent map
                //czekanie na wszystkie wątki - sygnalizowanie???
                map.put(name, (ImageIcon)wr.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    class ImageListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get(value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;
        }
    }

    public JList getList() {
        return list;
    }
}
