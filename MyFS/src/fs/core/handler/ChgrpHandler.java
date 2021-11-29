package fs.core.handler;


import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class ChgrpHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        FSDirectory tempDir;

        if (cmd.length > 4 || cmd.length < 3) {
            ConsoleIO.printLine("chown argumentos invalidos");
        } else if (cmd.length == 3) {

            FSGroup group = currentDisk.GroupExist(cmd[1]);

            if (group != null) {
                String[] searchPath = cmd[2].split("/");
                tempUnit = CurrentDir.getItem(searchPath);
                if (tempUnit == null) {
                    ConsoleIO.printLine("No existe el archivo o directorio");
                } else {
                    if (tempUnit.getClass() == FSDirectory.class) {

                        tempDir = (FSDirectory) tempUnit;

                        if (ValidateAccess(tempUnit, currentDisk)) {
                            tempDir.setGroup(group.getName());

                        } else {
                            System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                        }

                    } else {

                        tempFile = (FSFile) tempUnit;

                        if (ValidateAccess(tempUnit, currentDisk)) {
                            tempFile.setGroup(group.getName());

                        } else {
                            System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                        }
                    }
                }
            } else {
                System.out.println("El grupo no existe. Intentelo de nuevo con un grupo valido");
            }
        } else if (cmd.length == 4) {
            String[] searchPath = cmd[3].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (cmd[1].equals("-R") || cmd[1].equals("-r")) {
                FSGroup group = currentDisk.GroupExist(cmd[2]);

                if(group != null){
                    if(ValidateAccess(tempUnit, currentDisk)){
                        CurrentDir.chgrpR(group.getName());
                    }else{
                        System.out.println("El usuario no tiene los permisos necesarios");
                    }
                }else {
                    System.out.println("El grupo no existe");
                }
            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
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

