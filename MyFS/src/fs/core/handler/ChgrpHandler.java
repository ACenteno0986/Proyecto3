package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class ChgrpHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        FSDirectory tempDir;

        if(cmd.length > 4 || cmd.length < 3){
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
                        String userAct = currentDisk.getName();
                        FSGroup newGroup = currentDisk.UserExist(currentDisk.getName()).GroupExist(tempDir.getGroup());

                        if((userAct.equals("root")) || (userAct.equals(tempDir.getOwner()) &&
                                (tempDir.getOwnerAccessLvl() == 2 || tempDir.getOwnerAccessLvl() == 3 ||
                                        tempDir.getOwnerAccessLvl() == 6 || tempDir.getOwnerAccessLvl() == 7))){
                            tempDir.setGroup(group.getName());

                        }else if(((newGroup != null) ||
                                (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(tempDir.getGroup()))
                                        && (tempDir.getGroupAccessLvl() == 2 || tempDir.getGroupAccessLvl() == 3
                                        || tempDir.getGroupAccessLvl() == 6 || tempDir.getGroupAccessLvl() == 7))){
                            tempDir.setGroup(group.getName());

                        }else{
                            System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                        }

                    }else {

                        tempFile = (FSFile) tempUnit;
                        String userAct = currentDisk.getName();
                        FSGroup newGroup = currentDisk.UserExist(currentDisk.getName()).GroupExist(tempFile.getGroup());

                        if((userAct.equals("root")) || (userAct.equals(tempFile.getOwner()) &&
                                (tempFile.getOwnerAccessLvl() == 2 || tempFile.getOwnerAccessLvl() == 3 ||
                                        tempFile.getOwnerAccessLvl() == 6 || tempFile.getOwnerAccessLvl() == 7))){
                            tempFile.setGroup(group.getName());

                        }else if(((newGroup != null) ||
                                (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(tempFile.getGroup()))
                                        && (tempFile.getGroupAccessLvl() == 2 || tempFile.getGroupAccessLvl() == 3
                                        || tempFile.getGroupAccessLvl() == 6 || tempFile.getGroupAccessLvl() == 7))){
                            tempFile.setGroup(group.getName());

                        }else{
                            System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                        }
                    }
                }
            }else {
                System.out.println("El grupo no existe. Intentelo de nuevo con un grupo valido");
            }
        }
        return this.saveState(cmd,currentDisk,root,CurrentDir);
    }
}

