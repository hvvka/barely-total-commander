package com.hania;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PluginClassLoader extends ClassLoader {

    private Map<String, Class> classes;

    PluginClassLoader() {
        classes = new HashMap<>();
    }

    private byte[] getClassImplFromDataBase(String className) {
        System.out.println("Fetching the implementation of " + className);
        byte[] result;
        try {
            try (FileInputStream fileInputStream = new FileInputStream("store\\" + className + ".impl")) {
                result = new byte[fileInputStream.available()];
                fileInputStream.read(result);
            }
            return result;
        } catch (Exception e) {
            System.err.println("Class wasn't found or it was unreadable by our process!");
        }
        return new byte[0];
    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        return (loadClass(className, true));
    }

    @Override
    public synchronized Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        Class resultClass;
        byte[] classData;
        System.out.println("Load class: " + className);
        /* Check our local cache of classes */
        resultClass = classes.get(className);
        if (resultClass != null) {
            System.out.println("Returning cached resultClass.");
            return resultClass;
        }

        /* Check with the primordial class loader */
        try {
            System.out.println("Returning system class (in CLASSPATH).");
            return super.findSystemClass(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Not a system class.");
        }

        /* Try to load it from our repository */
        classData = getClassImplFromDataBase(className);
        if (classData == new byte[0]) {
            throw new ClassNotFoundException();
        }
        /* Define it (parse the class file) */
        resultClass = defineClass(classData, 0, classData.length);
        if (resultClass == null) {
            throw new ClassFormatError();
        }

        if (resolveIt) {
            resolveClass(resultClass);
        }

        classes.put(className, resultClass);
        System.out.println("Returning newly loaded class.");

        return resultClass;
    }
}
