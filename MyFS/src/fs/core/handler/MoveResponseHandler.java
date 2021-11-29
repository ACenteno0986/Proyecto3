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

            FSunit tempUnit = Root.getItemByPath((cmd[2]+"/"),Root);
            FSunit fileUnit = CurrentDir.getItem(cmd[1].split("/"));
            if(tempUnit == null){
                ConsoleIO.printLine("No se encontro el directorio destino.");
                return this.saveState(cmd, currentDisk, Root, CurrentDir);
            }

            if(fileUnit == null) {
                ConsoleIO.printLine("No se encontro el archivo o directorio a copiar.");
                return this.saveState(cmd, currentDisk, Root, CurrentDir);
            }
            if(cmd[2].split("/").length == 1) {

                fileUnit.setNameFile(cmd[2].trim());
            }
            else{

                if(fileUnit.getClass() == FSFile.class) {
                    FSFile tempFile = (FSFile) fileUnit;
                    CurrentDir.getDirContent().remove(tempFile.getPath(),tempFile);
                    tempFile.setPath(tempUnit.getPath()+tempFile.getName());
                    ((FSDirectory)tempUnit).getDirContent().put(tempFile.getPath(),tempFile);
                }
                else{
                    FSDirectory tempDir = (FSDirectory) fileUnit;
                    CurrentDir.getDirContent().remove(tempDir.getPath(),tempDir);
                    tempDir.setPath(tempUnit.getPath()+tempDir.getName());
                    ((FSDirectory)tempUnit).getDirContent().put(tempDir.getPath(),tempDir);
                }
            }




        }

        return this.saveState(cmd, currentDisk, Root, CurrentDir);
    }
}
