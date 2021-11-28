package fs.core.handler;


import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 * Created by Isaac on 2/17/17.
 */
public class RenameHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        ConsoleIO.printLine("This is the rename command");
        if(cmd.length == 3 ) {
            FSunit tempFile = CurrentDir.getItem(cmd[1].split("/"));
            if (tempFile == null) {
                ConsoleIO.printLine("No such file exists");
                return this.saveState(cmd, currentDisk, root, CurrentDir);
            }else if(CurrentDir.getDirContent().containsValue(tempFile)) {
                tempFile.setName(cmd[2]);
            }
        } else {
            ConsoleIO.printLine("You need to input an argument");
        }

        return this.saveState(cmd, currentDisk, root, CurrentDir);

    }

}
