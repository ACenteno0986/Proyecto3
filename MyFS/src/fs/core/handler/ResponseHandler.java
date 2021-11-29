package fs.core.handler;


import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.io.Serializable;

/**
 *
 *
 */
public abstract class ResponseHandler implements Serializable{

    private String[] cmd;
    private FSDirectory root;
    private FSDirectory CurrentDir;
    private VirtualDisk currentDisk;

    /**
     *
     */
    public abstract FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir);

    /**
     *
     */
    public FSDirectory saveState(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        this.cmd = cmd;
        this.currentDisk = currentDisk;
        this.root = root;
        this.CurrentDir = CurrentDir;

        return CurrentDir;
    }

    /**
     *
     */
    public FSunit handlerOnServer(){
        return handlerResponse(this.cmd,this.currentDisk, this.root, this.CurrentDir);
    }

}


