package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 *
 */
public class HelpHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String [] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){

        ConsoleIO.printHelp();
        return this.saveState(cmd, currentDisk, Root, CurrentDir);
    }
}
