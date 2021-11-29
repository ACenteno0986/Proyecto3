package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

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
                    String userAct = currentDisk.getName();
                    FSGroup group = currentDisk.UserExist(currentDisk.getName()).GroupExist(tempFile.getGroup());

                    if((userAct.equals("root")) || (userAct.equals(tempFile.getOwner()) &&
                            (tempFile.getOwnerAccessLvl() == 1 || tempFile.getOwnerAccessLvl() == 3 ||
                                    tempFile.getOwnerAccessLvl() == 5 || tempFile.getOwnerAccessLvl() == 7))){
                        tempFile.setOpen(true);
                        currentDisk.addOpenFile(tempFile);
                        System.out.println("Archivo abierto");

                    }else if(((group != null) ||
                            (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(tempFile.getGroup()))
                                    && (tempFile.getGroupAccessLvl() == 1 || tempFile.getGroupAccessLvl() == 3
                                    || tempFile.getGroupAccessLvl() == 5 || tempFile.getGroupAccessLvl() == 7))){
                        tempFile.setOpen(true);
                        currentDisk.addOpenFile(tempFile);
                        System.out.println("Archivo abierto");

                    }else{
                        System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                    }

                }

            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
