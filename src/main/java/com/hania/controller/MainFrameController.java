package com.hania.controller;

import com.hania.FileRecordLoader;
import com.hania.ImageFileChooser;
import com.hania.PluginType;
import com.hania.view.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrameController {

    private static final String MENU_DIRECTORY_LABEL = "File explorer";
    private static final String CHOOSE_DIRECTORY_MENU_ITEM_LABEL = "Choose directory";
    private static final String MENU_PLUGIN_LABEL = "Plugins";
    private static final String SATURATION_PLUGIN_MENU_ITEM_LABEL = "Increase saturation";
    private static final String GRAY_SCALE_PLUGIN_MENU_ITEM_LABEL = "Gray scale";
    private static final String BLACK_WHITE_PLUGIN_MENU_ITEM_LABEL = "Black&white";
    private static final String NEGATIVE_PLUGIN_MENU_ITEM_LABEL = "Negative";
    private static final String NO_PLUGIN_MENU_ITEM_LABEL = "No plugin";

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
    private JMenuItem saturationPluginMenuItem;
    private JMenuItem noPluginMenuItem;

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
        noPluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.NO_PLUGIN));
        negativePluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.NEGATIVE));
        blackWhitePluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.BLACK_WHITE));
        grayScalePluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.GRAY_SCALE));
        saturationPluginMenuItem.addActionListener(e -> attachPluginToAttachedDirectory(PluginType.SATURATION));
    }

    private void attachPluginToAttachedDirectory(PluginType pluginType) {
        if(fileRecordLoader != null) {
            attachPlugin(pluginType);
        }
    }

    private void attachPlugin(PluginType pluginType) {
        fileRecordList.removeAll();
        fileRecordLoader = new FileRecordLoader(imageFileChooser, pluginType);
        fileRecordList = fileRecordLoader.getFileRecordList();

        contentPane.remove(scrollPane);
        scrollPane = new JScrollPane(fileRecordList);
        contentPane.add(scrollPane);

        mainFrame.repaint();
        mainFrame.revalidate();
    }

    private void attachDirectory() {
        SwingUtilities.invokeLater(() -> {
            fileRecordLoader = new FileRecordLoader(imageFileChooser, PluginType.NO_PLUGIN);
            fileRecordList = fileRecordLoader.getFileRecordList();

            contentPane.remove(scrollPane);
            scrollPane = new JScrollPane(fileRecordList);
            contentPane.add(scrollPane);

            mainFrame.repaint();
            mainFrame.revalidate();
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

        saturationPluginMenuItem = mainFrame.getSaturationPluginMenuItem();
        saturationPluginMenuItem = new JMenuItem(SATURATION_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(saturationPluginMenuItem);

        grayScalePluginMenuItem = mainFrame.getGrayScalePluginMenuItem();
        grayScalePluginMenuItem = new JMenuItem(GRAY_SCALE_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(grayScalePluginMenuItem);

        blackWhitePluginMenuItem = mainFrame.getBlackWhitePluginMenuItem();
        blackWhitePluginMenuItem = new JMenuItem(BLACK_WHITE_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(blackWhitePluginMenuItem);

        negativePluginMenuItem = mainFrame.getNegativePluginMenuItem();
        negativePluginMenuItem = new JMenuItem(NEGATIVE_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(negativePluginMenuItem);

        noPluginMenuItem = mainFrame.getNoPluginMenuItem();
        noPluginMenuItem = new JMenuItem(NO_PLUGIN_MENU_ITEM_LABEL);
        pluginMenu.add(noPluginMenuItem);

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
