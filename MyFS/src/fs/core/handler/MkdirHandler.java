package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Isaac on 1/27/17.
 */
public class MkdirHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        ConsoleIO.printLine("This is the mkdir handler.");
        //this.saveState(cmd, currentDisk, root, CurrentDir);
        if(cmd.length != 2){
            ConsoleIO.printLine("mkdir command requires one argument");
        }else {
            String[] sourcePath = cmd[1].split("/");
            if(CurrentDir.getItem(sourcePath) != null){
                ConsoleIO.printLine("directory already exists!");
            }else {
                if(sourcePath.length > 1) {
                    String fileName = sourcePath[sourcePath.length - 1];
                    String[] parentPath = Arrays.copyOfRange(sourcePath, 0, sourcePath.length - 1);
                    FSDirectory tempParentDir = (FSDirectory) CurrentDir.getItem(parentPath);
                    FSDirectory newDir = new FSDirectory(tempParentDir.getPath(), fileName, new Date());
                    tempParentDir.getDirContent().put(newDir.getPath(), newDir);
                }else {
                    FSDirectory newDir = new FSDirectory(CurrentDir.getPath(), sourcePath[0], new Date());
                    CurrentDir.getDirContent().put(newDir.getPath(), newDir);
                }
            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
