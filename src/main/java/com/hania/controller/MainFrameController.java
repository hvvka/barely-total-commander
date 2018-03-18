package com.hania.controller;

import com.hania.FileRecordLoader;
import com.hania.ImageFileChooser;
import com.hania.PluginClassLoader;
import com.hania.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Modifier;
import java.util.Scanner;

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
        addPluginListener();
    }

    private void addPluginListener() {
        PluginClassLoader pluginClassLoader = new PluginClassLoader();
        Class negativePluginClass;
        try {
            negativePluginClass = pluginClassLoader.loadClass("com.hania.plugins.NegativePlugin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("negativePluginClass.getName() = " + negativePluginClass.getName());
        System.out.println("Modyfikator: " + Modifier.toString(negativePluginClass.getModifiers()));
        try {
            Object negativePluginObject = negativePluginClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


//
//        try {
//            Class plugin = classLoader.loadClass("com.hania.plugins.Plugin");
//            Class negativePluginClass = classLoader.loadClass("com.hania.plugins.NegativePlugin");
//            System.out.println("plugin.getName() = " + plugin.getName());
//            System.out.println("negativePluginClass.getName() = " + negativePluginClass.getName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        pluginNegativeMenuItem.addActionListener(e -> {

        });
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
