package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class ChmodHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        if(cmd.length != 3 ){
            ConsoleIO.printLine("chmod requiere al dos un argumento");
        }else {
            String[] searchPath = cmd[2].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (tempUnit == null) {
                ConsoleIO.printLine("No existe el archivo en el directorio actual");
            } else {
                if (tempUnit.getClass() == FSDirectory.class) {
                    ConsoleIO.printLine("El archivo objetivo es un directorio no aplica el comando");
                }else {
                    if(cmd[1].length() == 2){
                        if((Character.getNumericValue(cmd[1].charAt(0)) > 7) || (Character.getNumericValue(cmd[1].charAt(0)) < 0)){
                            System.out.println("Caracter invalido para permiso de owner. Debe ingresar un valor entre 0 y 7.");

                        }else if((Character.getNumericValue(cmd[1].charAt(1)) > 7) || (Character.getNumericValue(cmd[1].charAt(1)) < 0)){
                            System.out.println("Caracter invalido para permiso de group. Debe ingresar un valor entre 0 y 7.");

                        }else {
                            tempFile = (FSFile) tempUnit;
                            tempFile.setOwnerAccessLvl(Character.getNumericValue(cmd[1].charAt(0)));
                            tempFile.setGroupAccessLvl(Character.getNumericValue(cmd[1].charAt(1)));
                        }
                    }else{
                        System.out.println("chmod argumentos invalidos.");
                    }

                }

            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
