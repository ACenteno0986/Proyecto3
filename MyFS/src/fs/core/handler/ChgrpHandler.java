package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class ChgrpHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        FSDirectory tempDir;

        if(cmd.length > 4 || cmd.length < 2){
            ConsoleIO.printLine("chown argumentos invalidos");
        }
        else if(cmd.length == 3){

            FSGroup group = currentDisk.GroupExist(cmd[1]);

            if(group != null){
                String[] searchPath = cmd[2].split("/");
                tempUnit = CurrentDir.getItem(searchPath);
                if (tempUnit == null) {
                    ConsoleIO.printLine("No existe el archivo o directorio");
                } else {
                    if (tempUnit.getClass() == FSDirectory.class) {
                        tempDir = (FSDirectory) tempUnit;
                        tempDir.setOwner(group.getName());
                    }else {
                        tempFile = (FSFile) tempUnit;
                        tempFile.setOwner(group.getName());
                    }
                }
            }else {
                System.out.println("El grupo no existe. Intentelo de nuevo con un grupo valido");
            }
        }
        return this.saveState(cmd,currentDisk,root,CurrentDir);
    }
}

