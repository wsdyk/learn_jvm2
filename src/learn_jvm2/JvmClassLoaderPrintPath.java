/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learn_jvm2;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 *
 * @author qctc
 */
public class JvmClassLoaderPrintPath {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        URL[] url1 = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : url1) {
            System.out.println("启动类url:"+url1);
        }
        printClassLoader("扩展类加载器：",JvmClassLoaderPrintPath.class.getClassLoader().getParent());
       
        printClassLoader("应用类加载器：",JvmClassLoaderPrintPath.class.getClassLoader().getParent());
    }
    
    public static void printClassLoader(String name , ClassLoader classLoader){
        if (classLoader != null) {
            System.out.println(name + "  Classloader " + classLoader.toString());
            printURLForClassLoader(classLoader);
        }
        
        
    }

    private static void printURLForClassLoader(ClassLoader classLoader) {
        Object ucp = insightField(classLoader,"ucp");
        Object path = insightField(ucp, "path");
        List paths = (List)path;
        for (Object path1 : paths) {
            System.out.println(" ===》"+path1.toString());
        }

    }

    private static Object insightField(Object obj, String ucp) {
        Field f = null;
        try {
            if (obj instanceof  URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(ucp);
            }else{
                f=obj.getClass().getDeclaredField(ucp);
            }
            
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
}
