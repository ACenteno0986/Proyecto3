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
                    String userAct = currentDisk.getName();
                    FSGroup group = currentDisk.UserExist(currentDisk.getName()).GroupExist(tempFile.getGroup());

                    if((userAct.equals("root")) || (userAct.equals(tempFile.getOwner()) &&
                            (tempFile.getOwnerAccessLvl() == 4 || tempFile.getOwnerAccessLvl() == 5 ||
                                    tempFile.getOwnerAccessLvl() == 6 || tempFile.getOwnerAccessLvl() == 7))){
                        ConsoleIO.printLine(tempFile.getContent());

                    }else if(((group != null) ||
                            (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(tempFile.getGroup()))
                                    && (tempFile.getGroupAccessLvl() == 4 || tempFile.getGroupAccessLvl() == 5
                                    || tempFile.getGroupAccessLvl() == 6 || tempFile.getGroupAccessLvl() == 7))){
                        ConsoleIO.printLine(tempFile.getContent());

                    }else{
                        System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                    }

                }

            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);

    }
}
