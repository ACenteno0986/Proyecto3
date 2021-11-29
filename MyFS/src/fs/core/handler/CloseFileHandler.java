package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class CloseFileHandler extends ResponseHandler{

    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {
        FSunit tempUnit;
        FSFile tempFile;
        if(cmd.length < 2 ){
            ConsoleIO.printLine("closeFile requiere al menos un argumento");
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

                    if(ValidateAccess(tempUnit, currentDisk)){
                        tempFile.setOpen(false);
                        currentDisk.rmOpenFile(tempFile);
                        System.out.println("Archivo cerrado");

                    }else{
                        System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                    }

                }

            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }

    private boolean ValidateAccess(FSunit unit, VirtualDisk currentDisk) {

        String userAct = currentDisk.getName();
        FSGroup newGroup = currentDisk.UserExist(currentDisk.getName()).GroupExist(unit.getGroup());

        if ((userAct.equals("root")) || (userAct.equals(unit.getOwner()) &&
                (unit.getOwnerAccessLvl() == 1 || unit.getOwnerAccessLvl() == 3 ||
                        unit.getOwnerAccessLvl() == 5 || unit.getOwnerAccessLvl() == 7))) {
            return true;

        } else if (((newGroup != null) ||
                (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(unit.getGroup()))
                        && (unit.getGroupAccessLvl() == 1 || unit.getGroupAccessLvl() == 3
                        || unit.getGroupAccessLvl() == 5 || unit.getGroupAccessLvl() == 7))) {
            return true;

        } else {
            return false;
        }

    }
}
