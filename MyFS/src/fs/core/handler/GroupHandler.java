package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class GroupHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {
        if(cmd.length != 2){
            ConsoleIO.printLine("groupadd argumentos invalidos");
        }
        else{

            FSGroup newGroup = new FSGroup(cmd[1]);
            currentDisk.addToGroups(newGroup);
            System.out.println("Grupo creado");
            currentDisk.printUsers();
        }
        return this.saveState(cmd,currentDisk,root,CurrentDir);
    }
}
