package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;

import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;


/**
 * Created by Isaac on 1/27/17.
 * query cmd dont need any command
 *
 */
public class QueryHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        String fileName = null;
        this.saveState(cmd, currentDisk, root, CurrentDir);
        if (cmd.length != 1) {
            ConsoleIO.printLine("Query command requires no arguments since it only query the whole disk");
            return CurrentDir;
        }

        ConsoleIO.printLine(String.valueOf(root.getSize()) + "/" +String.valueOf(currentDisk.getSize()));
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
