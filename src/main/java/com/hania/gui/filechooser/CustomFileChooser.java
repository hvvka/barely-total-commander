package com.hania.gui.filechooser;

import com.hania.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class CustomFileChooser extends JPanel implements ActionListener {

    private JTextArea log;
    private JFileChooser fileChooser;
    private static final String DIRECTORY_PATH = "/Volumes/SD/obrazki/Tapety/paintings";

    CustomFileChooser() {
//        super(new BorderLayout());
//
//        //Create the log first, because the action listener
//        //needs to refer to it.
//        log = new JTextArea(5, 20);
//        log.setMargin(new Insets(5, 5, 5, 5));
//        log.setEditable(false);
//        JScrollPane logScrollPane = new JScrollPane(log);
//
//        JButton sendButton = new JButton("Attach...");
//        sendButton.addActionListener(this);
//
//        add(sendButton, BorderLayout.PAGE_START);
//        add(logScrollPane, BorderLayout.CENTER);
        if (fileChooser == null) {
            fileChooser = new JFileChooser(DIRECTORY_PATH);

            //Add a custom file filter and disable the default
            //(Accept All) file filter.
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPG", Utils.JPG));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG", Utils.PNG));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG", Utils.JPEG));
            fileChooser.setAcceptAllFileFilterUsed(false);

            //Add custom icons for file types.
            fileChooser.setFileView(new ImageFileView());

            //Add the preview pane.
            fileChooser.setAccessory(new ImagePreview(fileChooser));
        }

        //Show it.
        int returnVal = fileChooser.showDialog(CustomFileChooser.this, "Attach");

        //Process the results.
        String newline = "\n";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            log.append("Attaching file: " + file.getName() + "." + newline);
        } else {
            log.append("Attachment cancelled by user." + newline);
        }
        log.setCaretPosition(log.getDocument().getLength());

        //Reset the file chooser for the next time it's shown.
        fileChooser.setSelectedFile(new File(DIRECTORY_PATH));
    }

    public void actionPerformed(ActionEvent e) {
        //Set up the file chooser.
    }
}