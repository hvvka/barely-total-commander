package com.hania;

import java.io.FileInputStream;
import java.util.Hashtable;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PluginClassLoader extends ClassLoader {

    private Hashtable<String, Class> classes;

    PluginClassLoader() {
        classes = new Hashtable<>();
    }

    private byte[] getClassImplFromDataBase(String className) {
        System.out.println("Fetching the implementation of " + className);
        byte[] result;
        try {
            try (FileInputStream fi = new FileInputStream("store\\" + className + ".impl")) {
                result = new byte[fi.available()];
                fi.read(result);
            }
            return result;
        } catch (Exception e) {
            System.err.println("Class wasn't found or it was unreadable by our process!");
        }
        return null;
    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        return (loadClass(className, true));
    }

    @Override
    public synchronized Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        Class result;
        byte[] classData;
        System.out.println("Load class: " + className);
        /* Check our local cache of classes */
        result = classes.get(className);
        if (result != null) {
            System.out.println("Returning cached result.");
            return result;
        }

        /* Check with the primordial class loader */
        try {
            result = super.findSystemClass(className);
            System.out.println("Returning system class (in CLASSPATH).");
            return result;
        } catch (ClassNotFoundException e) {
            System.out.println("Not a system class.");
        }

        /* Try to load it from our repository */
        classData = getClassImplFromDataBase(className);
        if (classData == null) {
            throw new ClassNotFoundException();
        }
        /* Define it (parse the class file) */
        result = defineClass(classData, 0, classData.length);
        if (result == null) {
            throw new ClassFormatError();
        }

        if (resolveIt) {
            resolveClass(result);
        }

        classes.put(className, result);
        System.out.println("Returning newly loaded class.");

        return result;
    }
}
