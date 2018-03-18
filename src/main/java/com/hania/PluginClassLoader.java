package com.hania;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PluginClassLoader extends ClassLoader {

    private Hashtable<String, Class> classes;

    public PluginClassLoader() {
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

    public static Method[] getMetods(Class c) {
        return c.getMethods();
    }

    static Object parse(Class<?> cp, String s) {
        if (cp.equals(Integer.TYPE) || cp.equals(Integer.class)) {
            return Integer.parseInt(s);
        } else if (cp.equals(Long.TYPE) || cp.equals(Long.class)) {
            return Float.parseFloat(s);
        } else if (cp.equals(Boolean.TYPE) || cp.equals(Boolean.class)) {
            return Boolean.parseBoolean(s);
        } else if (cp.equals(Double.TYPE) || cp.equals(Double.class)) {
            return Double.parseDouble(s);
        } else if (cp.equals(Float.TYPE) || cp.equals(Float.class)) {
            return Float.parseFloat(s);
        } else if (cp.equals(Short.TYPE) || cp.equals(Short.class)) {
            return Short.parseShort(s);
        } else {
            return s;
        }
    }

    private static void invokeMethods(Object o, Scanner read, Class c) throws IllegalAccessException, InvocationTargetException {
        Method[] methods = c.getMethods();
        for (int i = 1; i <= methods.length; i++) {
            System.out.println(i + ".\t" + methods[i - 1].toString());
        }

        System.out.println("Wybierz metode (1-" + methods.length + ")");
        int wybor = read.nextInt() - 1;
        int liczbaArg = methods[wybor].getParameterCount();
        Object[] argu = new Object[liczbaArg];
        if (liczbaArg > 0)
            System.out.println("Podaj argumenty (" + liczbaArg + ")");

        Class<?>[] cp = methods[wybor].getParameterTypes();

        for (int i = 0; i < argu.length; i++) {
            argu[i] = parse(cp[i], read.next());
        }

        methods[wybor].invoke(o, argu);
    }

    public static void invokeFields(Object o, Scanner read, Class c) throws IllegalAccessException {
        Field[] fields = c.getFields();
        boolean koniec = false;
        do {
            for (int i = 1; i <= fields.length; i++) {
                System.out.println(i + ".\t" + fields[i - 1].toString());
            }
            System.out.println("Wybierz pole (1-" + fields.length + ")");
            int wybor = read.nextInt() - 1;

            Object wartosc = fields[wybor].get(o);
            System.out.println("wartosc: " + wartosc);
            System.out.println("Czy zmienic wartosc?[t/n] Koniec [k]");
            String decyzja = read.next();
            if ("t".equals(decyzja)) {
                System.out.println("Podaj wartosc");
                Class<?> cp = fields[wybor].getType();
                fields[wybor].set(o, parse(cp, read.next()));
            } else if ("k".equals(decyzja))
                koniec = true;
        } while (!koniec);
    }

}
