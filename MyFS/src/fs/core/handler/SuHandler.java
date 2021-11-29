package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSUser;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.util.Arrays;
import java.util.Date;

public class SuHandler extends ResponseHandler {
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        if (cmd.length > 2) {
            ConsoleIO.printLine("Comando no encontrado. Ingrese solo el usuario o dejelo en blanco.");
        } else if (cmd.length == 1) {
            FSUser rootUser = currentDisk.UserExist("root");
            String password;
            while (true) {
                if ((password = ConsoleIO.readLine("Ingrese contraseña para root: ")) != null) {
                    if (password.equals(rootUser.getPasswrd())) {
                        currentDisk.setCurrentUser(rootUser.getUsername());
                        return this.saveState(cmd, currentDisk, root, CurrentDir);

                    } else {
                        System.out.println("Contraseña incorrecta");
                        break;
                    }
                }
            }
        }else{
            FSUser rootUser = currentDisk.UserExist(cmd[1]);
            String password;

            if(rootUser == null){
                System.out.println("El usuario no existe.");
                return this.saveState(cmd, currentDisk, root, CurrentDir);
            }

            while (true) {
                if ((password = ConsoleIO.readLine("Ingrese contraseña para "+ cmd[1] +": ")) != null) {
                    if (password.equals(rootUser.getPasswrd())) {
                        currentDisk.setCurrentUser(rootUser.getUsername());
                        return this.saveState(cmd, currentDisk, root, CurrentDir);

                    } else {
                        System.out.println("Contraseña incorrecta");
                        break;
                    }
                }
            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
