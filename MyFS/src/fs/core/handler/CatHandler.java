package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.*;

/**
 * Created by Isaac on 1/27/17.
 */
public class CatHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){

        FSunit tempUnit;
        FSFile tempFile;
        if(cmd.length < 2 ){
            ConsoleIO.printLine("cat command requires at least one argument");
        }else {
            String[] searchPath = cmd[1].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (tempUnit == null) {
                ConsoleIO.printLine("No such file exists in current working directory");
            } else {
                if (tempUnit.getClass() == FSDirectory.class) {
                    ConsoleIO.printLine("Target file is a directory. cat command not applicable");
                }else {

                    tempFile = (FSFile) tempUnit;

                    if(ValidateAccess(tempUnit, currentDisk)){
                        ConsoleIO.printLine(tempFile.getContent());

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
                (unit.getOwnerAccessLvl() == 4 || unit.getOwnerAccessLvl() == 5 ||
                        unit.getOwnerAccessLvl() == 6 || unit.getOwnerAccessLvl() == 7))) {
            return true;

        } else if (((newGroup != null) ||
                (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(unit.getGroup()))
                        && (unit.getGroupAccessLvl() == 4 || unit.getGroupAccessLvl() == 5
                        || unit.getGroupAccessLvl() == 6 || unit.getGroupAccessLvl() == 7))) {
            return true;

        } else {
            return false;
        }

    }
}
