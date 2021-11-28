package hk.edu.polyu.comp3222.vfs.core.vfs;




import hk.edu.polyu.comp3222.vfs.core.handler.*;

import java.util.Date;

import java.io.Serializable;

/**
 * Created by Isaac on 2/15/17.
 */
public class VirtualDisk implements Serializable{
    private final VFSDirectory ROOT_FS;
    private final String ROOT_PATH;
    private String username, password;
    private int diskSize;
    private ResponseHandler[] cmdArray;
    private VFSDirectory currentDir;

    /**
     * constructor of visual disk
     * @param username username of the user of this visual disk
     * @param password password of the user of this visual disk
     * @param diskSize disk size of this visual disk
     */
    public VirtualDisk(String username, String password, int diskSize) {
        //global variable
        this.username = username;
        this.password = password;
        this.diskSize = diskSize;


        //root directory
        ROOT_FS = new VFSDirectory("", "root", new Date());
        System.out.println(ROOT_FS);
        ROOT_PATH = ROOT_FS.getPath();
        System.out.println(ROOT_PATH);
        currentDir = ROOT_FS;

        //initialization
        this.initializeFileSystem();
    }

    /**
     * initialization of this Visual File System
     */
    public void initializeFileSystem(){
        VFSDirectory firstFolder = new VFSDirectory(ROOT_PATH, "HOME", new Date());
        currentDir.getDirContent().put(firstFolder.getPath(), firstFolder);

    }

    /**
     * get name method
     * @return the name of this visual disk
     */
    public String getName(){
        return this.username;
    }

    /**
     * get size method
     * @return the size of this visual disk
     */
    public int getSize(){
        return this.diskSize;
    }

    /**
     * current directory of this visual disk
     * @return current directory
     */
    public VFSDirectory getCurrentDir(){
        return currentDir;
    }

    /**
     * get root method
     * @return root directory of this visual disk
     */
    public VFSDirectory getROOT_FS(){
        return ROOT_FS;
    }

    /**
     * set current directory method
     * @param currentDir VFS directory to be set as the current directory
     */
    public void setCurrentDir(VFSDirectory currentDir){
        this.currentDir = currentDir;
    }

    /**
     * returnt the password of this visual disk
     * @return password of this visual disk
     */
    public String getPassword(){
        return this.password;
    }

}
