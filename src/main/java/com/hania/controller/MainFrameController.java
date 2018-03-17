package com.hania.controller;

import com.hania.FileRecordLoader;
import com.hania.ImageFileChooser;
import com.hania.model.FileRecord;
import com.hania.view.MainFrame;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrameController {

    private static final String MENU_DIRECTORY_LABEL = "File explorer";
    private static final String CHOOSE_DIRECTORY_MENU_ITEM_LABEL = "Choose directory";
    private static final String MENU_PLUGIN_LABEL = "Plugins";
    private static final String PLUGIN_FLIP_HORIZONTALLY_MENU_ITEM_LABEL = "Flip horizontally";
    private static final String PLUGIN_FLIP_VERTICALLY_MENU_ITEM_LABEL = "Flip vertically";
    private static final String PLUGIN_BLACK_WHITE_MENU_ITEM_LABEL = "Convert to black&white";
    private static final String PLUGIN_NEGATIVE_MENU_ITEM_LABEL = "Convert to negative";

    private final MainFrame mainFrame;

    private JPanel contentPane;
    private JScrollPane scrollPane;

    private JMenuBar menu;
    private JMenu directoryMenu;
    private JMenuItem chooseDirectoryMenuItem;

    private JMenu pluginMenu;
    private JMenuItem pluginNegativeMenuItem;
    private JMenuItem pluginBlackWhiteMenuItem;
    private JMenuItem pluginFlipVerticallyMenuItem;
    private JMenuItem pluginFlipHorizontallyMenuItem;

    private JList fileRecordList;
    private ImageFileChooser imageFileChooser;

    public MainFrameController() {
        mainFrame = new MainFrame();

        initComponents();
        initListeners();
    }

    private void initListeners() {
        addChooseDirectoryListener();


    }

    private void attachDirectory() {
        SwingUtilities.invokeLater(() -> {
            FileRecordLoader fileRecordLoader = new FileRecordLoader(imageFileChooser);
            fileRecordList = fileRecordLoader.getFileRecordList();

            contentPane.remove(scrollPane);
            scrollPane = new JScrollPane(fileRecordList);
            contentPane.add(scrollPane);
            addListListener();

            mainFrame.repaint();
            mainFrame.revalidate();
        });
    }

    private void addListListener() {
        fileRecordList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
//                    contentPane.removeAll();
//                    File file = files.get(index);
//                    BufferedImage img = null;
//                    try {
//                        img = ImageIO.read(file);
//                    } catch (IOException ee) {
//                        ee.printStackTrace();
//                    }
//                    BufferedImage scaledImg = Scalr.resize(Objects.requireNonNull(img), 700);
//
//                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
//                    contentPane.add(imageLabel);
                    System.out.println(index);
                }
            }
        });
    }

    private void addChooseDirectoryListener() {
        chooseDirectoryMenuItem.addActionListener(e -> {
            imageFileChooser = new ImageFileChooser();
            int returnVal = imageFileChooser.showOpenDialog(imageFileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = imageFileChooser.getSelectedFile().getAbsolutePath();
                mainFrame.setTitle(path);
            }
            attachDirectory();
        });
    }

    private void initComponents() {
        mainFrame.setVisible(true);

        initContentPane();
        initMenu();
        initPluginMenu();
        initList();

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void initList() {
        fileRecordList = new JList();
    }

    private void initPluginMenu() {
        pluginMenu = mainFrame.getPluginMenu();
        pluginMenu = new JMenu(MENU_PLUGIN_LABEL);

        pluginFlipHorizontallyMenuItem = mainFrame.getPluginFlipHorizontallyMenuItem();
        pluginFlipHorizontallyMenuItem = new JMenuItem(PLUGIN_FLIP_HORIZONTALLY_MENU_ITEM_LABEL);
        pluginMenu.add(pluginFlipHorizontallyMenuItem);

        pluginFlipVerticallyMenuItem = mainFrame.getPluginFlipVerticallyMenuItem();
        pluginFlipVerticallyMenuItem = new JMenuItem(PLUGIN_FLIP_VERTICALLY_MENU_ITEM_LABEL);
        pluginMenu.add(pluginFlipVerticallyMenuItem);

        pluginBlackWhiteMenuItem = mainFrame.getPluginBlackWhiteMenuItem();
        pluginBlackWhiteMenuItem = new JMenuItem(PLUGIN_BLACK_WHITE_MENU_ITEM_LABEL);
        pluginMenu.add(pluginBlackWhiteMenuItem);

        pluginNegativeMenuItem = mainFrame.getPluginNegativeMenuItem();
        pluginNegativeMenuItem = new JMenuItem(PLUGIN_NEGATIVE_MENU_ITEM_LABEL);
        pluginMenu.add(pluginNegativeMenuItem);

        menu.add(pluginMenu);
    }

    private void initMenu() {
        menu = mainFrame.getMenu();
        menu = new JMenuBar();

        directoryMenu = mainFrame.getDirectoryMenu();
        directoryMenu = new JMenu(MENU_DIRECTORY_LABEL);
        menu.add(directoryMenu);

        chooseDirectoryMenuItem = mainFrame.getChooseDirectoryMenuItem();
        chooseDirectoryMenuItem = new JMenuItem(CHOOSE_DIRECTORY_MENU_ITEM_LABEL);
        directoryMenu.add(chooseDirectoryMenuItem);

        mainFrame.setJMenuBar(menu);
    }

    private void initContentPane() {
        contentPane = mainFrame.getContentPane();
        contentPane = new JPanel(new GridLayout(1, 1));
        mainFrame.setContentPane(contentPane);

        scrollPane = mainFrame.getScrollPane();
        scrollPane = new JScrollPane();
        contentPane.add(scrollPane);
    }
}
