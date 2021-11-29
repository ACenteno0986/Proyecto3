package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class ChownHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        FSDirectory tempDir;

        if(cmd.length > 4 || cmd.length < 3){
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
                        String userAct = currentDisk.getName();
                        FSGroup group = currentDisk.UserExist(currentDisk.getName()).GroupExist(tempDir.getGroup());

                        if((userAct.equals("root")) || (userAct.equals(tempDir.getOwner()) &&
                                (tempDir.getOwnerAccessLvl() == 2 || tempDir.getOwnerAccessLvl() == 3 ||
                                        tempDir.getOwnerAccessLvl() == 6 || tempDir.getOwnerAccessLvl() == 7))){
                            tempDir.setOwner(user.getUsername());

                        }else if(((group != null) ||
                                (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(tempDir.getGroup()))
                                        && (tempDir.getGroupAccessLvl() == 2 || tempDir.getGroupAccessLvl() == 3
                                        || tempDir.getGroupAccessLvl() == 6 || tempDir.getGroupAccessLvl() == 7))){
                            tempDir.setOwner(user.getUsername());

                        }else{
                            System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                        }


                    }else {
                        tempFile = (FSFile) tempUnit;

                        if(ValidateAccess(tempUnit, currentDisk)){
                            tempFile.setOwner(user.getUsername());

                        }else{
                            System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                        }

                    }
                }
            }else {
                System.out.println("El usuario no existe. Intentelo de nuevo con un usuario valido");
            }
        }
        else if (cmd.length == 4) {
            String[] searchPath = cmd[3].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (cmd[1].equals("-R") || cmd[1].equals("-r")) {
                FSUser user = currentDisk.UserExist(cmd[2]);

                if(user != null){
                    if(ValidateAccess(tempUnit, currentDisk)){
                        CurrentDir.chownR(user.getUsername());
                    }else{
                        System.out.println("El usuario no tiene los permisos necesarios");
                    }
                }else {
                    System.out.println("El grupo no existe");
                }
            }
        }
        return this.saveState(cmd,currentDisk,root,CurrentDir);
    }

    private boolean ValidateAccess(FSunit unit, VirtualDisk currentDisk) {

        String userAct = currentDisk.getName();
        FSGroup newGroup = currentDisk.UserExist(currentDisk.getName()).GroupExist(unit.getGroup());

        if ((userAct.equals("root")) || (userAct.equals(unit.getOwner()) &&
                (unit.getOwnerAccessLvl() == 2 || unit.getOwnerAccessLvl() == 3 ||
                        unit.getOwnerAccessLvl() == 6 || unit.getOwnerAccessLvl() == 7))) {
            return true;

        } else if (((newGroup != null) ||
                (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(unit.getGroup()))
                        && (unit.getGroupAccessLvl() == 2 || unit.getGroupAccessLvl() == 3
                        || unit.getGroupAccessLvl() == 6 || unit.getGroupAccessLvl() == 7))) {
            return true;

        } else {
            return false;
        }

    }
}
