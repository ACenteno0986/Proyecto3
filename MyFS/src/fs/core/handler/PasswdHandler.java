package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSUser;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class PasswdHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSUser user = currentDisk.UserExist(currentDisk.getName());

        String oldpasswrd = ConsoleIO.readLine("Ingrese la contraseña actual: ");
        if(oldpasswrd.equals(user.getPasswrd())){
            while(true){
                String newpasswrd = ConsoleIO.readLine("Ingrese la nueva contraseña: ");
                String confpasswrd = ConsoleIO.readLine("Confirme la contraseña : ");
                if(newpasswrd.equals(confpasswrd)){
                    user.setPasswrd(newpasswrd);
                    System.out.println("Exito");
                    break;
                }
                else{
                    System.out.println("Las contraseñas no coinciden");
                }
            }

        }
        else{
            System.out.println("Contraseña incorrecta");
        }


        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
