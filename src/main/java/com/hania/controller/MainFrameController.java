package com.hania.controller;

import com.hania.FileRecordLoader;
import com.hania.ImageFileChooser;
import com.hania.PluginType;
import com.hania.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrameController {

    private static final String MENU_DIRECTORY_LABEL = "File explorer";
    private static final String CHOOSE_DIRECTORY_MENU_ITEM_LABEL = "Choose directory";
    private static final String MENU_PLUGIN_LABEL = "Plugins";
    private static final String PLUGIN_FLIP_HORIZONTALLY_MENU_ITEM_LABEL = "Flip horizontally";
    private static final String GRAY_SCALE_PLUGIN_MENU_ITEM_LABEL = "Gray scale";
    private static final String BLACK_WHITE_PLUGIN_MENU_ITEM_LABEL = "Black&white";
    private static final String NEGATIVE_PLUGIN_MENU_ITEM_LABEL = "Negative";

    private final MainFrame mainFrame;

    private JPanel contentPane;
    private JScrollPane scrollPane;

    private JMenuBar menu;
    private JMenu directoryMenu;
    private JMenuItem chooseDirectoryMenuItem;

    private JMenu pluginMenu;
    private JMenuItem negativePluginMenuItem;
    private JMenuItem blackWhitePluginMenuItem;
    private JMenuItem grayScalePluginMenuItem;
    private JMenuItem pluginFlipHorizontallyMenuItem;

    private JList fileRecordList;
    private ImageFileChooser imageFileChooser;
    private FileRecordLoader fileRecordLoader;

    public MainFrameController() {
        mainFrame = new MainFrame();

        initComponents();
        initListeners();
    }

    private void initListeners() {
        addChooseDirectoryListener();
        addPluginListeners();
    }

    private void addPluginListeners() {
        negativePluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.NEGATIVE));
        blackWhitePluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.BLACK_WHITE));
        grayScalePluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.GRAY_SCALE));
        //todo add more plugins
    }

    private void attachPluginToAttachedDirectory(PluginType pluginType) {
        if(fileRecordLoader != null) {
            attachPlugin(pluginType);
        }
    }

    private void attachPlugin(PluginType pluginType) {
        fileRecordLoader.applyPlugin(pluginType);
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    private void attachDirectory() {
        SwingUtilities.invokeLater(() -> {
            fileRecordLoader = new FileRecordLoader(imageFileChooser);
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

        grayScalePluginMenuItem = mainFrame.getGrayScalePluginMenuItem();
        grayScalePluginMenuItem = new JMenuItem(GRAY_SCALE_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(grayScalePluginMenuItem);

        blackWhitePluginMenuItem = mainFrame.getBlackWhitePluginMenuItem();
        blackWhitePluginMenuItem = new JMenuItem(BLACK_WHITE_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(blackWhitePluginMenuItem);

        negativePluginMenuItem = mainFrame.getNegativePluginMenuItem();
        negativePluginMenuItem = new JMenuItem(NEGATIVE_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(negativePluginMenuItem);

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
