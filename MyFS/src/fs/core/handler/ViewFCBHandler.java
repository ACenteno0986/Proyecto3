package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.sql.SQLOutput;

public class ViewFCBHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        if(cmd.length < 2 ){
            ConsoleIO.printLine("viewFCB requiere al menos un argumento");
        }else {
            String[] searchPath = cmd[1].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (tempUnit == null) {
                ConsoleIO.printLine("No existe el archivo en el directorio actual");
            } else {
                if (tempUnit.getClass() == FSDirectory.class) {
                    ConsoleIO.printLine("El archivo objetivo es un directorio no aplica el comando");
                }else {
                    tempFile = (FSFile) tempUnit;
                    String nombre = tempFile.getName();
                    String owner = tempFile.getOwner();
                    String cDate = tempFile.getDateCreated();
                    boolean open = tempFile.isOpen();
                    int size = tempFile.getSize();
                    String path = tempFile.getPath();

                    System.out.println("Nombre: "+nombre);
                    System.out.println("Dueño: "+owner);
                    System.out.println("Fecha de creacion: "+cDate);
                    if(open){
                        System.out.println("Abierto: Si");
                    }
                    if(!open){
                        System.out.println("Abierto: No");
                    }
                    System.out.println("Tamaño: "+size);
                    System.out.println("Ubicacion: "+ path);
                    System.out.println(tempFile.getGroup());
                    System.out.println(tempFile.getOwnerAccessLvl());
                    System.out.println(tempFile.getGroupAccessLvl());

                }

            }
        }

        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
