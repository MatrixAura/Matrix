package me.matrixaura.matrix.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassUtil {

    public static byte[] readBytes(String className) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream in = ClassUtil.class.getResourceAsStream('/' + className.replace('.', '/') + ".class")) {
            byte[] buffer = new byte[16384];
            for (int l; (l = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, l);
            }
        }
        return out.toByteArray();
    }

    public static List<Class> loadClassByLoader(ClassLoader load) throws Exception{
        Enumeration<URL> urls = load.getResources("");
        List<Class> classes = new ArrayList<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (url.getProtocol().equals("file")) {
                loadClassByPath(null, url.getPath(), classes, load);
            }
        }
        return classes;
    }

    public static void loadClassByPath(String root, String path, List<Class> list, ClassLoader load) {
        File f = new File(path);
        if(root==null) root = f.getPath();
        if (f.isFile() && f.getName().matches("^.*\\.class$")&&f.getPath().contains("base")) {
            try {
                String classPath = f.getPath();
                String className = classPath.substring(root.length()+1,classPath.length()-6).replace('/','.').replace('\\','.');
                list.add(load.loadClass(className));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            File[] fs = f.listFiles();
            if (fs == null) return;
            for (File file : fs) {
                loadClassByPath(root,file.getPath(), list, load);
            }
        }
    }

}
