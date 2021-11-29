package fs.core.fs;




import fs.core.handler.*;
import fs.core.handler.ResponseHandler;

import java.util.ArrayList;
import java.util.Date;

import java.io.Serializable;
import java.util.List;

/**
 *
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
    private int diskUsage;
    private int blockSize;
    private ResponseHandler[] cmdArray;
    private FSDirectory currentDir;

    /**
     *
     */
    public VirtualDisk(String fsName, String password, int diskSize, int blockSize) {
        //global variable
        this.fsName = fsName;
        this.currentUser = "root";
        this.diskSize = diskSize;
        this.diskUsage = 0;
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
     *
     */
    public void initializeFileSystem(){
        FSDirectory firstFolder = new FSDirectory(ROOT_PATH, "HOME", new Date());
        currentDir.getDirContent().put(firstFolder.getPath(), firstFolder);

    }

    /**
     *
     */
    public String getName(){
        return this.currentUser;
    }
    public void setCurrentUser(String currentUser){this.currentUser = currentUser;}

    /**
     *
     */
    public int getSize(){
        return this.diskSize;
    }

    /**
     *
     */
    public FSDirectory getCurrentDir(){
        return currentDir;
    }

    /**
     *
     */
    public FSDirectory getROOT_FS(){
        return ROOT_FS;
    }

    /**
     *
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

    public String getFsName() {
        return fsName;
    }

    public int getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(int diskUsage) {
        this.diskUsage += diskUsage;
    }
}
