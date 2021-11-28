package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 * Created by Isaac on 1/27/17.
 */
public class DirectResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){
        //ioService.printLine(String.valueOf(cmd.length));
        this.saveState(cmd, currentDisk, Root, CurrentDir);
        if(cmd.length == 1){
            CurrentDir = Root;
        }else {
            String[] seachPath = cmd[1].split("/");
            FSunit fileSystemUnit = CurrentDir.getItem(seachPath);

            if (fileSystemUnit == null) {
                ConsoleIO.printLine("This directory is not found on this VFS");

            } else {
                if (fileSystemUnit.getClass() == FSFile.class) {
                    ConsoleIO.printLine("The target path is not a directory");
                }else {
                    CurrentDir = (FSDirectory) fileSystemUnit;
                }

            }
        }
        return this.saveState(cmd, currentDisk, Root, CurrentDir);

    }
}
