package com.hania.gui.filechooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
// TODO refactor
public class FileViewer extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    private static final String FRAME_TITLE = "Eksplorator obrazków";
    private static final String MENU_DIRECTORY_LABEL = "Eksplorator katalogów";
    private static final String CHOOSE_DIRECTORY_MENU_ITEM_LABEL = "Wybierz katalog";

    private static final String MENU_PLUGIN_LABEL = "Pluginy";
    private static final String PLUGIN_NEGATIVE_MENU_ITEM_LABEL = "Negatyw";
    private static final String PLUGIN_BLACK_WHITE_MENU_ITEM_LABEL = "Czarnobiały";
    private static final String PLUGIN_FLIP_VERTICALLY_MENU_ITEM_LABEL = "Przerzuć w pionie";
    private static final String PLUGIN_FLIP_HORIZONTALLY_MENU_ITEM_LABEL = "Przerzuć w poziomie";

    private JMenuBar menu;
    private JMenu directoryMenu;
    private JMenuItem chooseDirectoryMenuItem;

    private JMenu pluginMenu;
    //Przynajmniej jedna z ładowanych klas powinna być zależna od innej ładowanej klasy (Rozwijanie klas (resolve)).
    private JMenuItem pluginNegativeMenuItem;
    private JMenuItem pluginBlackWhiteMenuItem;
    private JMenuItem pluginFlipVerticallyMenuItem;
    private JMenuItem pluginFlipHorizontallyMenuItem;

    private final JPanel contentPane;
    private JScrollPane listScrollPane;
    private JList listFromImageList;

    private List<String> pathsToFilesInDir = null;


    public FileViewer() {
        setTitle(FRAME_TITLE);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width / 2 - this.getSize().width / 2,
                dimension.height / 2 - this.getSize().height / 2);

        contentPane = new JPanel(new GridLayout(1, 1));
        setContentPane(contentPane);

        createMenu();
        createMenuListeners();
    }

    private void createMenu() {
        listScrollPane = new JScrollPane();
        contentPane.add(listScrollPane);

        menu = new JMenuBar();
        directoryMenu = new JMenu(MENU_DIRECTORY_LABEL);
        menu.add(directoryMenu);
        chooseDirectoryMenuItem = new JMenuItem(CHOOSE_DIRECTORY_MENU_ITEM_LABEL);
        directoryMenu.add(chooseDirectoryMenuItem);

        pluginMenu = new JMenu(MENU_PLUGIN_LABEL);
        pluginFlipHorizontallyMenuItem = new JMenuItem(PLUGIN_FLIP_HORIZONTALLY_MENU_ITEM_LABEL);
        pluginMenu.add(pluginFlipHorizontallyMenuItem);
        pluginFlipVerticallyMenuItem = new JMenuItem(PLUGIN_FLIP_VERTICALLY_MENU_ITEM_LABEL);
        pluginMenu.add(PLUGIN_FLIP_VERTICALLY_MENU_ITEM_LABEL);
        pluginBlackWhiteMenuItem = new JMenuItem(PLUGIN_BLACK_WHITE_MENU_ITEM_LABEL);
        pluginMenu.add(PLUGIN_BLACK_WHITE_MENU_ITEM_LABEL);
        pluginNegativeMenuItem = new JMenuItem(PLUGIN_NEGATIVE_MENU_ITEM_LABEL);
        pluginMenu.add(PLUGIN_NEGATIVE_MENU_ITEM_LABEL);

        setJMenuBar(menu);
        repaint();
        revalidate();
    }

    private void createMenuListeners() {
        chooseDirectoryMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {


                String path = fileChooser.getSelectedFile().getAbsolutePath();
                setTitle(path);
                pathsToFilesInDir = new ArrayList<>();
                File[] files = new File(path).listFiles((dir, name) -> name.endsWith(".jpg"));
                for (File file : Objects.requireNonNull(files)) {
                    if (file.isFile()) {
                        pathsToFilesInDir.add(path + "\\" + file.getName());
                    }
                }
                createThumbnails();
            }
        });

    }

    private void addListListener() {
        listFromImageList.addMouseListener(new MouseAdapter() {
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

    private void createThumbnails() {
        SwingUtilities.invokeLater(() -> {
            if (pathsToFilesInDir != null && !pathsToFilesInDir.isEmpty()) {

                FileRecord imageList = new FileRecord(pathsToFilesInDir);
                listFromImageList = imageList.getList();
                contentPane.remove(listScrollPane);
                listScrollPane = new JScrollPane(listFromImageList);
                contentPane.add(listScrollPane);
                addListListener();
                repaint();
                revalidate();
            }
        });
    }

}
