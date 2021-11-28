package hk.edu.polyu.comp3222.vfs.core.handler;

import hk.edu.polyu.comp3222.vfs.Util.ConsoleIO;
import hk.edu.polyu.comp3222.vfs.core.vfs.VFSDirectory;
import hk.edu.polyu.comp3222.vfs.core.vfs.VFSunit;
import hk.edu.polyu.comp3222.vfs.core.vfs.VirtualDisk;

/**
 * Created by Isaac on 2/20/17.
 */
public class QuitResponseHandler extends ResponseHandler{
    @Override
    public VFSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, VFSDirectory Root, VFSDirectory CurrentDir){
        ConsoleIO.printLine("quitting the VFS system");
        this.saveState(cmd, currentDisk, Root, CurrentDir);
        return null;
    }
}
