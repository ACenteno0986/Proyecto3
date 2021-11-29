package fs.controller;
import fs.core.fs.VirtualDisk;

import java.io.*;

/**
 *
 */
public class SerializationController {
    private static SerializationController instance;
    private static final String fsSerializedNamePrefix = System.getProperty("user.dir") + System.getProperty("file.separator");
    //private static final String fsSerializedNamePrefix = System.getProperty("user.home");
    //private static final String fsSerializedNamePrefix = "MyFS/db/";

    /**
     *
     */
    public static SerializationController getInstance() {
        if (instance == null)
            instance = new SerializationController();
        return instance;
    }

    /**
     *
     */
    public void serialize(VirtualDisk fileSystem) {
        try {
            FileOutputStream fos;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(fsSerializedNamePrefix + fileSystem.getFsName());
                oos = new ObjectOutputStream(fos);
                oos.writeObject(fileSystem);
            } finally {
                if (oos != null)
                    oos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public VirtualDisk deserialize(String name) {
        try {
            FileInputStream fis;
            ObjectInputStream ois = null;

            File file = new File(fsSerializedNamePrefix + name);

            if (!file.exists() || !file.isFile())
                return null;

            try {
                fis = new FileInputStream(fsSerializedNamePrefix + name);
                ois = new ObjectInputStream(fis);
                return (VirtualDisk) ois.readObject();
            } finally {
                if (ois != null)
                    ois.close();
            }
        } catch (InvalidClassException ignored) {
            System.out.println("\nERROR: Hubo un problema al cargar el File System. Por favor eliminelo antes de iniciar el programa.");
            System.exit(0);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     */
    public boolean deleteVFS(String name){
        try{
            boolean ifDelete;
            File file = new File(fsSerializedNamePrefix + name);
            if (!file.exists() || !file.isFile())
                return false;
            ifDelete = file.delete();
            return ifDelete;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
