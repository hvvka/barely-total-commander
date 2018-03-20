package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PluginClassLoader extends ClassLoader {

    private static final Logger LOG = LoggerFactory.getLogger(PluginClassLoader.class);

    private Map<String, Class> classes;

    PluginClassLoader() {
        classes = new HashMap<>();
    }

    private byte[] getClassImplFromDataBase(String className) {
        LOG.info("Fetching the implementation of {}", className);
        byte[] result;
        try {
            try (FileInputStream fileInputStream = new FileInputStream("store\\" + className + ".impl")) {
                result = new byte[fileInputStream.available()];
                fileInputStream.read(result);
            }
            return result;
        } catch (Exception e) {
            LOG.error("Class wasn't found or it was unreadable by our process!", e);
        }
        return new byte[0];
    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        return (loadClass(className, true));
    }

    @Override
    public synchronized Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        LOG.info("Load class: {}", className);

        Class resultClass = getCachedClass(className);
        if (resultClass != null) return resultClass;

        /* Check with the primordial class loader */
        try {
            LOG.info("Returning system class.");
            return super.findSystemClass(className);
        } catch (ClassNotFoundException e) {
            LOG.warn("Not a system class!", e);
        }

        /* Try to load it from our repository */
        return loadFromLocalRepository(className, resolveIt);
    }

    private Class getCachedClass(String className) {
        Class resultClass = classes.get(className);
        if (resultClass != null) {
            LOG.info("Returning cached resultClass.");
            return resultClass;
        }
        return null;
    }

    private Class loadFromLocalRepository(String className, boolean resolveIt) throws ClassNotFoundException {
        byte[] classData = getClassImplFromDataBase(className);
        if (classData == new byte[0]) {
            throw new ClassNotFoundException();
        }
        /* Define it (parse the class file) */
        Class resultClass = defineClass(classData, 0, classData.length);
        if (resultClass == null) {
            throw new ClassFormatError();
        }

        if (resolveIt) {
            resolveClass(resultClass);
        }

        classes.put(className, resultClass);
        LOG.info("Returning newly loaded class.");
        return resultClass;
    }
}
