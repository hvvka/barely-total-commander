package com.hania;

import java.lang.reflect.Modifier;

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

    private String getPluginClassname(PluginType pluginType) {
        switch (pluginType) {
            case NEGATIVE:
                return NEGATIVE_PLUGIN_CLASS_NAME;
            case BLACK_WHITE:
                return BLACK_WHITE_PLUGIN_CLASS_NAME;
            default:
                return "";
        }
    }

    private void loadPlugin(PluginType pluginType) {
        if (loadPluginClass(pluginType)) return;

        System.out.println("negativePluginClass.getName() = " + pluginClass.getName());
        System.out.println("Modyfikator: " + Modifier.toString(pluginClass.getModifiers()));

        createPluginInstance();
    }

    private void createPluginInstance() {
        try {
            pluginObject = pluginClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean loadPluginClass(PluginType pluginType) {
        try {
            pluginClass = pluginClassLoader.loadClass(getPluginClassname(pluginType));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

}
