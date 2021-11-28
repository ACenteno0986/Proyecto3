package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 * Created by Isaac on 1/27/17.
 */
public class CatHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        //ioService.printLine(cmd[1]);
        FSunit tempUnit;
        FSFile tempFile;
        if(cmd.length < 2 ){
            ConsoleIO.printLine("cat command requires at least one argument");
        }else {
            String[] searchPath = cmd[1].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (tempUnit == null) {
                ConsoleIO.printLine("No such file exists in current working directory");
            } else {
                if (tempUnit.getClass() == FSDirectory.class) {
                    ConsoleIO.printLine("Target file is a directory. cat command not applicable");
                }else {
                    tempFile = (FSFile) tempUnit;
                    ConsoleIO.printLine(tempFile.getContent());
                }

            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);

    }
}
