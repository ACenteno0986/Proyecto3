package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

public class UserHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {
        if(cmd.length > 3 || cmd.length < 2){
            ConsoleIO.printLine("useradd argumentos invalidos");
        }
        else if(cmd.length == 2){

            String fullname = ConsoleIO.readLine("Ingrese el nombre completo: ");

            String passwrd = ConsoleIO.readLine("Ingrese la contraseña: ");

            FSGroup validatedGroup = currentDisk.GroupExist("user");
            FSUser newUser = new FSUser(cmd[1], fullname, passwrd, validatedGroup);
            currentDisk.addToUsers(newUser);
            System.out.println("Usuario creado");
            //currentDisk.printUsers();
            return this.saveState(cmd,currentDisk,root,CurrentDir);

            }else {
            String fullname = ConsoleIO.readLine("Ingrese el nombre completo: ");

            String passwrd = ConsoleIO.readLine("Ingrese la contraseña: ");

            FSGroup validatedGroup = currentDisk.GroupExist(cmd[2]);
            if (validatedGroup != null){
                FSUser newUser = new FSUser(cmd[1], fullname, passwrd, validatedGroup);
                currentDisk.addToUsers(newUser);
                System.out.println("Usuario creado");
                currentDisk.printUsers();
                return this.saveState(cmd, currentDisk, root, CurrentDir);
            }else{
                System.out.println("Grupo no existe. Intente de nuevo con un grupo valido.");
            }

        }
        return this.saveState(cmd,currentDisk,root,CurrentDir);
    }
}
