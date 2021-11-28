package fs.core.handler;


import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.io.Serializable;

/**
 * Created by Isaac on 1/24/17.
 *
 */
public abstract class ResponseHandler implements Serializable{

    private String[] cmd;
    private FSDirectory root;
    private FSDirectory CurrentDir;
    private VirtualDisk currentDisk;

    /**
     * method to handler specific command
     * @param cmd cmd array from user input
     * @param currentDisk current visual disk in use
     * @param root root directory in current visual disk
     * @param CurrentDir current working directory
     * @return current directory after command is handled
     */
    public abstract FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir);

    /**
     * save the handler's state for serialization
     * @param cmd cmd array from user input
     * @param root root directory in current visual disk
     * @param CurrentDir current working directory
     * @param currentDisk current working disk
     * @return current directory after command is handled
     */
    public FSDirectory saveState(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        this.cmd = cmd;
        this.currentDisk = currentDisk;
        this.root = root;
        this.CurrentDir = CurrentDir;

        return CurrentDir;
    }

    /**
     * handler response on server with pre-saved params
     * @return modified vfs directory
     */
    public FSunit handlerOnServer(){
        return handlerResponse(this.cmd,this.currentDisk, this.root, this.CurrentDir);
    }

}


