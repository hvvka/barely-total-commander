package com.hania;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PluginGenerator {

    private static final String NEGATIVE_PLUGIN_CLASS_NAME = "com.hania.plugins.NegativePlugin";
    private static final String BLACK_WHITE_PLUGIN_CLASS_NAME = "com.hania.plugins.BlackWhitePlugin";

    private PluginClassLoader pluginClassLoader;

    private Class pluginClass;

    private Object pluginObject;

    public PluginGenerator() {
        this.pluginClassLoader = new PluginClassLoader();
    }

    public Object getPlugin(PluginType pluginType) {
        loadPlugin(pluginType);
        return pluginObject;
    }

    public ImageIcon invokeConvertIconMethod(Object pluginObject, String path) {
        Method[] methods = pluginObject.getClass().getMethods();

//        for (int i = 1; i <= methods.length; i++) {
//            System.out.println(i + ".\t" + methods[i - 1].toString());
//        }

//        Scanner read = new Scanner(System.in);
//        System.out.println("Wybierz metode (1-" + methods.length + ")");
//        int wybor = read.nextInt() - 1;
        int wybor = 0;
        int liczbaArg = methods[wybor].getParameterCount();
        Object[] argu = new Object[liczbaArg];
//        if (liczbaArg > 0)
//            System.out.println("Podaj argumenty (" + liczbaArg + ")");
//        Class<?>[] classParameterTypes = methods[wybor].getParameterTypes();
//        for (int i = 0; i < argu.length; i++) {
//            argu[i] = PluginClassLoader.parse(classParameterTypes[i], read.next());
//        }
        argu[0] = path;

        try {
            return (ImageIcon) methods[wybor].invoke(pluginObject, argu);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadPlugin(PluginType pluginType) {
        if (loadPluginClass(pluginType)) return;

        System.out.println("negativePluginClass.getName() = " + pluginClass.getName());
        System.out.println("Modyfikator: " + Modifier.toString(pluginClass.getModifiers()));

        createPluginInstance();
    }

    private boolean loadPluginClass(PluginType pluginType) {
        try {
            pluginClass = pluginClassLoader.loadClass(getPluginClassName(pluginType));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private String getPluginClassName(PluginType pluginType) {
        switch (pluginType) {
            case NEGATIVE:
                return NEGATIVE_PLUGIN_CLASS_NAME;
            case BLACK_WHITE:
                return BLACK_WHITE_PLUGIN_CLASS_NAME;
            default:
                return "";
        }
    }

    private void createPluginInstance() {
        try {
            pluginObject = pluginClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
