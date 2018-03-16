package com.hania.gui;

import com.hania.classloader.Credentials;

import javax.swing.*;
import java.awt.event.*;

public class LogIn extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField loginTextField;
    private JTextField passwordField;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private Credentials credentials;

    LogIn() {
        this.credentials = new Credentials();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initListeners();

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    private void initListeners() {
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e ->
                onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void onOK() {
        credentials.setLogin(loginTextField.getText());
        credentials.setPassword(passwordField.getText());
        setVisible(false);
        this.dispose();
    }

    private void onCancel() {
        this.dispose();
    }

    public  Credentials showDialog() {
        this.pack();
        this.setVisible(true);
        return credentials;
    }

}
