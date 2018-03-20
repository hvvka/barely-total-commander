package com.hania.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrame extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final String FRAME_TITLE = "Barely Total Commander";

    private static final String MENU_DIRECTORY_LABEL = "File explorer";
    private static final String CHOOSE_DIRECTORY_MENU_ITEM_LABEL = "Choose directory";
    private static final String MENU_PLUGIN_LABEL = "Plugins";
    private static final String SATURATION_PLUGIN_MENU_ITEM_LABEL = "Increase saturation";
    private static final String GRAY_SCALE_PLUGIN_MENU_ITEM_LABEL = "Gray scale";
    private static final String BLACK_WHITE_PLUGIN_MENU_ITEM_LABEL = "Black&white";
    private static final String NEGATIVE_PLUGIN_MENU_ITEM_LABEL = "Negative";
    private static final String NO_PLUGIN_MENU_ITEM_LABEL = "No plugin";

    private JMenu directoryMenu;
    private JMenuBar jMenuBar;
    private JMenuItem chooseDirectoryMenuItem;

    private JMenu pluginMenu;
    private JMenuItem negativePluginMenuItem;
    private JMenuItem blackWhitePluginMenuItem;
    private JMenuItem grayScalePluginMenuItem;
    private JMenuItem saturationPluginMenuItem;
    private JMenuItem noPluginMenuItem;

    private JPanel contentPane;
    private JScrollPane scrollPane;

    public MainFrame() {
        setTitle(FRAME_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initGUI();
    }

    private void initGUI() {
        jMenuBar = new JMenuBar();
        directoryMenu = new JMenu(MENU_DIRECTORY_LABEL);
        chooseDirectoryMenuItem = new JMenuItem(CHOOSE_DIRECTORY_MENU_ITEM_LABEL);
        initPluginMenu();
        contentPane = new JPanel(new GridLayout(1, 1));
        scrollPane = new JScrollPane();
    }

    private void initPluginMenu() {
        pluginMenu = new JMenu(MENU_PLUGIN_LABEL);
        saturationPluginMenuItem = new JMenuItem(SATURATION_PLUGIN_MENU_ITEM_LABEL);
        grayScalePluginMenuItem = new JMenuItem(GRAY_SCALE_PLUGIN_MENU_ITEM_LABEL);
        blackWhitePluginMenuItem = new JMenuItem(BLACK_WHITE_PLUGIN_MENU_ITEM_LABEL);
        negativePluginMenuItem = new JMenuItem(NEGATIVE_PLUGIN_MENU_ITEM_LABEL);
        noPluginMenuItem = new JMenuItem(NO_PLUGIN_MENU_ITEM_LABEL);
    }

    @Override
    public JMenuBar getJMenuBar() {
        return jMenuBar;
    }

    public JMenu getDirectoryMenu() {
        return directoryMenu;
    }

    public JMenuItem getChooseDirectoryMenuItem() {
        return chooseDirectoryMenuItem;
    }

    public JMenu getPluginMenu() {
        return pluginMenu;
    }

    public JMenuItem getNegativePluginMenuItem() {
        return negativePluginMenuItem;
    }

    public JMenuItem getBlackWhitePluginMenuItem() {
        return blackWhitePluginMenuItem;
    }

    public JMenuItem getGrayScalePluginMenuItem() {
        return grayScalePluginMenuItem;
    }

    public JMenuItem getNoPluginMenuItem() {
        return noPluginMenuItem;
    }

    public JMenuItem getSaturationPluginMenuItem() {
        return saturationPluginMenuItem;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
