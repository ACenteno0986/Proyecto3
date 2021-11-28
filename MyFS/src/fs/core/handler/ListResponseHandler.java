package fs.core.handler;


import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 * Created by Isaac on 1/27/17.
 */
public class ListResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk disk, FSDirectory Root, FSDirectory CurrentDir){
        CurrentDir.list(true);
        return this.saveState(cmd, disk, Root, CurrentDir);
    }
}
