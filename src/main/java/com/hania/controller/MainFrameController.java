package com.hania.controller;

import com.hania.FileRecordLoader;
import com.hania.ImageFileChooser;
import com.hania.PluginType;
import com.hania.view.MainFrame;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrameController {

    private final MainFrame mainFrame;

    private JPanel contentPane;
    private JScrollPane scrollPane;

    private JMenuBar jMenuBar;
    private JMenuItem chooseDirectoryMenuItem;

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
        refreshRecordList();
    }

    private void attachDirectory() {
        SwingUtilities.invokeLater(() -> {
            fileRecordLoader = new FileRecordLoader(imageFileChooser, PluginType.NO_PLUGIN);
            refreshRecordList();
        });
    }

    private void refreshRecordList() {
        fileRecordList = fileRecordLoader.getFileRecordList();
        contentPane.remove(scrollPane);
        scrollPane = new JScrollPane(fileRecordList);
        contentPane.add(scrollPane);
        contentPane.revalidate();
        contentPane.repaint();
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
        JMenu pluginMenu = mainFrame.getPluginMenu();

        saturationPluginMenuItem = mainFrame.getSaturationPluginMenuItem();
        pluginMenu.add(saturationPluginMenuItem);

        grayScalePluginMenuItem = mainFrame.getGrayScalePluginMenuItem();
        pluginMenu.add(grayScalePluginMenuItem);

        blackWhitePluginMenuItem = mainFrame.getBlackWhitePluginMenuItem();
        pluginMenu.add(blackWhitePluginMenuItem);

        negativePluginMenuItem = mainFrame.getNegativePluginMenuItem();
        pluginMenu.add(negativePluginMenuItem);

        noPluginMenuItem = mainFrame.getNoPluginMenuItem();
        pluginMenu.add(noPluginMenuItem);

        jMenuBar.add(pluginMenu);
    }

    private void initMenu() {
        jMenuBar = mainFrame.getJMenuBar();

        JMenu directoryMenu = mainFrame.getDirectoryMenu();
        jMenuBar.add(directoryMenu);

        chooseDirectoryMenuItem = mainFrame.getChooseDirectoryMenuItem();
        directoryMenu.add(chooseDirectoryMenuItem);

        mainFrame.setJMenuBar(jMenuBar);
    }

    private void initContentPane() {
        contentPane = mainFrame.getContentPane();
        mainFrame.setContentPane(contentPane);

        scrollPane = mainFrame.getScrollPane();
        contentPane.add(scrollPane);
    }
}
