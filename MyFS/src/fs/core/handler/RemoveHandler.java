package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 * Created by Isaac on 2/17/17.
 */
public class RemoveHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        ConsoleIO.printLine("This is the remove command");

        if (cmd.length == 2){

            FSunit tempFile = CurrentDir.getItem(cmd[1].split("/"));
            if(tempFile == null){
                ConsoleIO.printLine("No such file exists");
            }else if(CurrentDir.getDirContent().containsValue(tempFile)) {
                CurrentDir.getDirContent().remove(tempFile.getPath(), tempFile);
            }

        } else {
            ConsoleIO.printLine("You nned to input an argument");
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);

    }

}
