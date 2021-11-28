package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.util.Date;

/**
 * Created by Isaac on 1/27/17.
 * touch cmd need to have at least two argument
 * "touch filename filecontent"
 */
public class CreateHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        //ioService.printLine("This is the touch handler.");
        String fileName = null;
        if (cmd.length == 3) {

            fileName = cmd[1];
            FSFile tempFile = new FSFile(CurrentDir.getPath(), fileName, new Date(), cmd[2].getBytes());
            //FSunit tempFileUnit = new File();
            CurrentDir.getDirContent().put(tempFile.getPath(),tempFile);
        } else {
            ConsoleIO.printLine("Wrong Argument for touch command");
        }

        return this.saveState(cmd,  currentDisk,root, CurrentDir);


    }
}
