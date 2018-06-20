package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class PluginGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PluginGenerator.class);

    private static final String NO_PLUGIN_CLASS_NAME = "libs.com.hania.plugins.Plugin";
    private static final String GRAY_SCALE_PLUGIN_CLASS_NAME = "com.hania.plugins.GrayScalePlugin";
    private static final String NEGATIVE_PLUGIN_CLASS_NAME = "com.hania.plugins.NegativePlugin";
    private static final String BLACK_WHITE_PLUGIN_CLASS_NAME = "com.hania.plugins.BlackWhitePlugin";
    private static final String SATURATION_PLUGIN_CLASS_NAME = "com.hania.plugins.SaturationPlugin";

    private PluginClassLoader pluginClassLoader;

    private Class pluginClass;
    private Object pluginObject;

    PluginGenerator() {
        this.pluginClassLoader = new PluginClassLoader();
    }

    Object getPlugin(PluginType pluginType) {
        loadPlugin(pluginType);
        return pluginObject;
    }

    BufferedImage invokeConvertIconMethod(Object pluginObject, String path) {
        Method[] methods = pluginObject.getClass().getMethods();
        int argumentIndex = 0;
        int numberOfArguments = methods[argumentIndex].getParameterCount();
        Object[] arguments = new Object[numberOfArguments];
        arguments[0] = path;

        try {
            return (BufferedImage) methods[argumentIndex].invoke(pluginObject, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("Plugin method invocation exception!", e);
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
            LOG.error("Creating plugin class exception!", e);
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
            case SATURATION:
                return SATURATION_PLUGIN_CLASS_NAME;
            default:
                return "";
        }
    }

    private void createPluginInstance() {
        try {
            pluginObject = pluginClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Creating plugin instance exception!", e);
        }
    }

}
