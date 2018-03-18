package com.hania;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PluginGenerator {

    private static final String NO_PLUGIN_CLASS_NAME = "com.hania.plugins.Plugin";
    private static final String GRAY_SCALE_PLUGIN_CLASS_NAME = "com.hania.plugins.GrayScalePlugin";
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

    public BufferedImage invokeConvertIconMethod(Object pluginObject, String path) {
        Method[] methods = pluginObject.getClass().getMethods();
        int argumentIndex = 0;
        int numberOfArguments = methods[argumentIndex].getParameterCount();
        Object[] arguments = new Object[numberOfArguments];
        arguments[0] = path;

        try {
            return (BufferedImage) methods[argumentIndex].invoke(pluginObject, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Plugin method invocation exception");
        }

        return null;
    }

    private void loadPlugin(PluginType pluginType) {
        if (loadPluginClass(pluginType)) return;
        createPluginInstance();
    }

    private boolean loadPluginClass(PluginType pluginType) {
        try {
            pluginClass = pluginClassLoader.loadClass(getPluginClassName(pluginType));
        } catch (ClassNotFoundException e) {
            System.out.println("Creating plugin class exception!");
            return true;
        }
        return false;
    }

    private String getPluginClassName(PluginType pluginType) {
        switch (pluginType) {
            case NO_PLUGIN:
                return NO_PLUGIN_CLASS_NAME;
            case NEGATIVE:
                return NEGATIVE_PLUGIN_CLASS_NAME;
            case BLACK_WHITE:
                return BLACK_WHITE_PLUGIN_CLASS_NAME;
            case GRAY_SCALE:
                return GRAY_SCALE_PLUGIN_CLASS_NAME;
            default:
                return "";
        }
    }

    private void createPluginInstance() {
        try {
            pluginObject = pluginClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Creating plugin instance exception!");
        }
    }

}
