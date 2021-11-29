package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class OpenFileHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        if(cmd.length < 2 ){
            ConsoleIO.printLine("openFile requiere al menos un argumento");
        }else {
            String[] searchPath = cmd[1].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (tempUnit == null) {
                ConsoleIO.printLine("No existe el archivo en el directorio actual");
            } else {
                if (tempUnit.getClass() == FSDirectory.class) {
                    ConsoleIO.printLine("El archivo objetivo es un directorio no aplica el comando");
                }else {
                    tempFile = (FSFile) tempUnit;
                    tempFile.setOpen(true);
                    System.out.println("Archivo abierto");
                }

            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
