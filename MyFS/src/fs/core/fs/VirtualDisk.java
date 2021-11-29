package fs.core.fs;




import fs.core.handler.*;
import fs.core.handler.ResponseHandler;

import java.util.ArrayList;
import java.util.Date;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Isaac on 2/15/17.
 */
public class VirtualDisk implements Serializable{
    private final FSDirectory ROOT_FS;
    private final String ROOT_PATH;
    private String fsName;
    private String currentUser;
    private List<FSUser> users;
    private List<FSGroup> groups;
    private List<FSFile> openFiles;
    private int diskSize;
    private int blockSize;
    private ResponseHandler[] cmdArray;
    private FSDirectory currentDir;

    /**
     * constructor of visual disk
     * @param username username of the user of this visual disk
     * @param password password of the user of this visual disk
     * @param diskSize disk size of this visual disk
     */
    public VirtualDisk(String fsName, String password, int diskSize, int blockSize) {
        //global variable
        this.fsName = fsName;
        this.currentUser = "root";
        this.diskSize = diskSize;
        this.blockSize = blockSize;
        this.users = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.openFiles = new ArrayList<>();
        FSGroup groupAdmin = new FSGroup("admin");
        FSGroup groupUser = new FSGroup("user");
        this.groups.add(groupAdmin);
        this.groups.add(groupUser);
        FSUser rootUS = new FSUser("root","root",password, groupAdmin);
        users.add(rootUS);


        //root directory
        ROOT_FS = new FSDirectory("", "root", new Date());
        ROOT_PATH = ROOT_FS.getPath();
        currentDir = ROOT_FS;

        //initialization
        this.initializeFileSystem();
    }

    /**
     * initialization of this Visual File System
     */
    public void initializeFileSystem(){
        FSDirectory firstFolder = new FSDirectory(ROOT_PATH, "HOME", new Date());
        currentDir.getDirContent().put(firstFolder.getPath(), firstFolder);

    }

    /**
     * get name method
     * @return the name of this visual disk
     */
    public String getName(){
        return this.currentUser;
    }
    public void setCurrentUser(String currentUser){this.currentUser = currentUser;}

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
    public FSDirectory getCurrentDir(){
        return currentDir;
    }

    /**
     * get root method
     * @return root directory of this visual disk
     */
    public FSDirectory getROOT_FS(){
        return ROOT_FS;
    }

    /**
     * set current directory method
     * @param currentDir VFS directory to be set as the current directory
     */
    public void setCurrentDir(FSDirectory currentDir){
        this.currentDir = currentDir;
    }

    public void addToUsers(FSUser user) {
        this.users.add(user);
    }
    public void printUsers(){
        for (FSUser user: users) {
            System.out.println("Usuario: "+user.getUsername());
            System.out.println("Nombre: "+user.getFullname());
        }

    }
    public List<FSUser> getUsers() {
        return users;
    }

    public FSUser UserExist(String validateUser){
        for (FSUser user: users) {
            if(user.getUsername().equals(validateUser)){
                return user;
            }
        }
        return null;
    }
    public void addToGroups(FSGroup group){this.groups.add(group);}
    public FSGroup GroupExist(String validateGroup){
        for (FSGroup group: groups) {
            if(group.getName().equals(validateGroup)){
                return group;
            }
        }
        return null;
    }

    public List<FSFile> getOpenFiles() {
        return openFiles;
    }
    public FSFile isOpenFile(FSFile file){
        for (FSFile fil: openFiles) {
            if(fil.equals(file)) {
                return fil;
            }
        }
        return null;
    }

    public void addOpenFile(FSFile file) {
        this.openFiles.add(file);
    }
    public void rmOpenFile(FSFile file) {
        this.openFiles.remove(file);
    }
}
