package com.hania.view;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrame extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    private static final String FRAME_TITLE = "Barely Total Commander";

    private JMenuBar menu;
    private JMenu directoryMenu;
    private JMenuItem chooseDirectoryMenuItem;

    private JMenu pluginMenu;
    //Przynajmniej jedna z ładowanych klas powinna być zależna od innej ładowanej klasy (Rozwijanie klas (resolve)).
    private JMenuItem negativePluginMenuItem;
    private JMenuItem blackWhitePluginMenuItem;
    private JMenuItem grayScalePluginMenuItem;
    private JMenuItem pluginFlipHorizontallyMenuItem;

    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JList fileRecordList;

    public MainFrame() {
        setTitle(FRAME_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JMenuBar getMenu() {
        return menu;
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

    public JMenuItem getPluginFlipHorizontallyMenuItem() {
        return pluginFlipHorizontallyMenuItem;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JList getFileRecordList() {
        return fileRecordList;
    }
}
