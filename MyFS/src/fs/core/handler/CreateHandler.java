package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.util.Date;

/**
 * "
 */
public class CreateHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){

        String fileName = null;
        if (cmd.length > 2) {

            fileName = cmd[1];

            FSFile tempFile = new FSFile(CurrentDir.getPath(), fileName, new Date(), cmd[2].getBytes());
            tempFile.setOwner(currentDisk.getName());
            tempFile.setGroup(currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName());
            currentDisk.setDiskUsage(tempFile.getSize());
            CurrentDir.getDirContent().put(tempFile.getPath(),tempFile);
        }
        else if(cmd.length == 2){

            fileName = cmd[1];

            FSFile tempFile = new FSFile(CurrentDir.getPath(), fileName, new Date(), "".getBytes());
            tempFile.setOwner(currentDisk.getName());
            tempFile.setGroup(currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName());
            currentDisk.setDiskUsage(tempFile.getSize());
            CurrentDir.getDirContent().put(tempFile.getPath(),tempFile);

        }
        else {
            ConsoleIO.printLine("Agumentos erroneos para touch");
        }

        return this.saveState(cmd,  currentDisk,root, CurrentDir);


    }
}
