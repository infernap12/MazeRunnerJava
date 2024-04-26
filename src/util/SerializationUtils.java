package util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SerializationUtils {
    /**
     * Serialize the given object to the file
     *
     * @param obj
     * @param fileName
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        if (fileName.endsWith(".gz")) {
            serialize(obj, fileName, true);
        } else {
            serialize(obj, fileName, false);
        }
    }

    /**
     * Serialize the given object to the file
     */
    public static void serialize(Object obj, String fileName, boolean isGzipCompressed) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(isGzipCompressed ? new GZIPOutputStream(fos) : fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }


    /**
     * Deserialize to an object from the file
     *
     * @param fileName
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        if (fileName.endsWith(".gz")) {
            return deserialize(fileName, true);
        } else {
            return deserialize(fileName, false);
        }
    }

    /**
     * Deserialize to an object from the file
     *
     * @param fileName Filename of file to deserialize
     * @param isGzipCompressed boolean to indicate whether file is compressed
     */
    public static Object deserialize(String fileName, boolean isGzipCompressed) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(isGzipCompressed ? new GZIPInputStream(fis) : fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}