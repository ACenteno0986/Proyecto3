package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 * Created by Isaac on 2/20/17.
 */
public class QuitResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){
        ConsoleIO.printLine("quitting the VFS system");
        this.saveState(cmd, currentDisk, Root, CurrentDir);
        return null;
    }
}
