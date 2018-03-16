package com.hania.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hania.classloader.Credentials;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;

public class MyClassLoader {

    private JTextField textField1;
    private JButton classButton;
    private JPanel contentPane;

    private ClassLoader tempClassLoader;

    private MyClassLoader() {
        classButton.addActionListener(e -> {
            String className = textField1.getText();
            if (validate(className)) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MyClassLoader");
        frame.setContentPane(new MyClassLoader().contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JsonArray convertFileToJsonArray(String fileName) {
        JsonArray jsonArray = new JsonArray();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonArray = jsonElement.getAsJsonArray();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return jsonArray;
    }

    private boolean validate(String name) {
        JsonArray temp = convertFileToJsonArray("credits.json");
        LogIn dialog = new LogIn();
        JsonObject tempObject;
        Credentials credentials = dialog.showDialog();
        for (JsonElement jsonElement : temp) {
            tempObject = (JsonObject)jsonElement;
            if (tempObject.get("login").getAsString().equals(credentials.getLogin())) {
                if (tempObject.get("password").getAsString().equals(credentials.getPassword())) {
                    String asd = tempObject.get("class").toString();
                    for (JsonElement classes : tempObject.get("class").getAsJsonArray()) {
                        if(classes.getAsString().equals(name)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}



