package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;


import java.util.Arrays;
import java.util.Date;

/**
 * Created by Isaac on 1/27/17.
 */
public class MoveResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){
        ConsoleIO.printLine("This is the mv handler.");
        if (cmd.length != 3){
            ConsoleIO.printLine("Move command requires two arguments");
        } else if(cmd[1].equals(cmd[2])){
            ConsoleIO.printLine("two argument shouldn't be the same");
        } else{
            FSunit tempUnit = CurrentDir.getItem(cmd[1].split("/"));
            if(tempUnit == null) {
                ConsoleIO.printLine("No such file found.");
                return this.saveState(cmd, currentDisk, Root, CurrentDir);
            }

            String filename;
            if(cmd[2].split("/").length == 1){
                filename = cmd[2];
                if(tempUnit.getClass() == FSFile.class) {
                    FSFile tempFile = (FSFile) tempUnit;
                    FSFile targetFile = new FSFile(CurrentDir.getPath(), filename, new Date(), tempFile.getContent().getBytes());
                    CurrentDir.getDirContent().put(targetFile.getPath(),targetFile);
                }else{
                    FSDirectory tempDir = (FSDirectory) tempUnit;
                    ConsoleIO.printLine(tempDir.getPath());
                    tempDir.setName(filename);
                    ConsoleIO.printLine(tempDir.getPath());
                    CurrentDir.getDirContent().put(tempDir.getPath(),tempDir);
                    //CurrentDir.getDirContent().remove(tempUnit.getPath());
                }
                CurrentDir.getDirContent().remove(tempUnit.getPath());
            }else {
                String[] subPath = Arrays.copyOfRange(cmd[2].split("/"), 0, cmd[2].split("/").length - 1);
                filename = cmd[2].split("/")[cmd[2].split("/").length - 1];
                FSDirectory targetDir = (FSDirectory) CurrentDir.getItem(subPath);
                if(tempUnit.getClass() == FSFile.class) {
                    FSFile tempFile = (FSFile) tempUnit;
                    FSFile targetFile = new FSFile(targetDir.getPath(), filename, new Date(), tempFile.getContent().getBytes());
                    targetDir.getDirContent().put(targetFile.getPath(),targetFile);
                    CurrentDir.getDirContent().remove(tempUnit.getPath());
                }else{
                    FSDirectory tempDir = (FSDirectory) tempUnit;
                    tempDir.setName(filename);
                    targetDir.getDirContent().put(tempDir.getPath(),tempDir);
                    CurrentDir.getDirContent().remove(tempUnit.getPath());
                }

            }
            //CurrentDir.getDirContent().remove(tempUnit.getPath(), tempUnit);
        }

        return this.saveState(cmd, currentDisk, Root, CurrentDir);
    }
}
