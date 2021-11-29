package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

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
                            String userAct = currentDisk.getName();
                            FSGroup group = currentDisk.UserExist(currentDisk.getName()).GroupExist(tempFile.getGroup());

                            if((userAct.equals("root")) || (userAct.equals(tempFile.getOwner()) &&
                                    (tempFile.getOwnerAccessLvl() == 2 || tempFile.getOwnerAccessLvl() == 3 ||
                                            tempFile.getOwnerAccessLvl() == 6 || tempFile.getOwnerAccessLvl() == 7))){
                                tempFile.setOwnerAccessLvl(Character.getNumericValue(cmd[1].charAt(0)));
                                tempFile.setGroupAccessLvl(Character.getNumericValue(cmd[1].charAt(1)));

                            }else if(((group != null) ||
                                    (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(tempFile.getGroup()))
                                        && (tempFile.getGroupAccessLvl() == 2 || tempFile.getGroupAccessLvl() == 3
                                            || tempFile.getGroupAccessLvl() == 6 || tempFile.getGroupAccessLvl() == 7))){
                                tempFile.setOwnerAccessLvl(Character.getNumericValue(cmd[1].charAt(0)));
                                tempFile.setGroupAccessLvl(Character.getNumericValue(cmd[1].charAt(1)));

                            }else{
                                System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                            }
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
