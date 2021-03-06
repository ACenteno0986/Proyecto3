package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;


import java.util.Arrays;
import java.util.Date;

/**
 *
 */
public class MoveResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){

        if (cmd.length != 3){
            ConsoleIO.printLine("Move requiere al menos 2 argumentos");
        } else if(cmd[1].equals(cmd[2])){
            ConsoleIO.printLine("los argumentos no pueden ser iguales");
        } else{


            FSunit fileUnit = CurrentDir.getItem(cmd[1].split("/"));
            //System.out.println("dir: "+fileUnit.getPath());
            if(fileUnit == null) {
                ConsoleIO.printLine("No se encontro el archivo o directorio a copiar.");

            }
            if(cmd[2].split("/").length == 1) {
                if(fileUnit.getClass() == FSFile.class){
                    fileUnit.setNameFile(cmd[2].trim());
                }
                else{
                    fileUnit.setNameDir(cmd[2].trim());
                }

            }
            else{
                FSunit tempUnit = Root.getItemByPath((cmd[2]+"/"),Root);
                if(tempUnit == null){
                    ConsoleIO.printLine("No se encontro el directorio destino.");

                }
                else if(fileUnit.getClass() == FSFile.class) {
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
