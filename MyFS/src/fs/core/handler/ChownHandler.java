package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class ChownHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        FSDirectory tempDir;

        if(cmd.length > 4 || cmd.length < 2){
            ConsoleIO.printLine("chown argumentos invalidos");
        }
        else if(cmd.length == 3){

            FSUser user = currentDisk.UserExist(cmd[1]);

            if(user != null){
                String[] searchPath = cmd[2].split("/");
                tempUnit = CurrentDir.getItem(searchPath);
                if (tempUnit == null) {
                    ConsoleIO.printLine("No existe el archivo o directorio");
                } else {
                    if (tempUnit.getClass() == FSDirectory.class) {
                        tempDir = (FSDirectory) tempUnit;
                        tempDir.setOwner(user.getUsername());
                    }else {
                        tempFile = (FSFile) tempUnit;
                        tempFile.setOwner(user.getUsername());
                    }
                }
            }else {
                System.out.println("El usuario no existe. Intentelo de nuevo con un usuario valido");
            }
        }
        return this.saveState(cmd,currentDisk,root,CurrentDir);
    }
}
