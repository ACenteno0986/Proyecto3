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
                if ((password = ConsoleIO.readLine("Ingrese contraseña para usuario: ")) != null) {
                    if (password.equals(rootUser.getPasswrd())) {
                        currentDisk.setCurrentUser(rootUser.getUsername());

                    } else {
                        System.out.println("Contraseña incorrrecta");
                        break;
                    }
                }
            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
