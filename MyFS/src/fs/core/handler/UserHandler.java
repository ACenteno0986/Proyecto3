package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSUser;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class UserHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {
        if(cmd.length != 2){
            ConsoleIO.printLine("useradd argumentos invalidos");
        }
        else{
            //System.out.println(cmd[1]);

            String fullname= ConsoleIO.readLine("Ingrese el nombre completo: ");

            String passwrd = ConsoleIO.readLine("Ingrese la contraseña: ");

            FSUser newUser = new FSUser(cmd[1],fullname,passwrd,"root");
            currentDisk.addToUsers(newUser);
            System.out.println("Usuario creado");
            currentDisk.printUsers();
        }
        return this.saveState(cmd,currentDisk,root,CurrentDir);
    }
}
