package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class RemoveHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){

        FSFile rmfile;
        FSDirectory rmdir;
        if (cmd.length == 2){

            FSunit tempFile = CurrentDir.getItem(cmd[1].split("/"));
            if(tempFile == null){
                ConsoleIO.printLine("No existe el archivo");
            }else if(CurrentDir.getDirContent().containsValue(tempFile)) {
                if(tempFile.getClass() == FSDirectory.class){
                    rmdir = (FSDirectory) tempFile;
                    int size1 = -rmdir.getSize();
                    currentDisk.setDiskUsage(size1);
                }
                else{
                    rmfile = (FSFile) tempFile;
                    int size2 = -rmfile.getSize();
                    currentDisk.setDiskUsage(size2);

                }
                CurrentDir.getDirContent().remove(tempFile.getPath(), tempFile);
            }

        }
        else if(cmd.length == 3){
            if(cmd[1].equals("-R") || cmd[1].equals("-r")){

                String[] dirToSearch = cmd[2].split("/");

                cmd[2] +="/";
                FSunit tempUnit = root.getItemByPath(cmd[2],root);
                if(tempUnit == null || tempUnit.getClass() == FSFile.class){
                    ConsoleIO.printLine("No existe el directorio o es un archivo");
                }else{

                    FSDirectory tempDir = (FSDirectory) tempUnit;
                    for (Iterator<Map.Entry<String, FSunit>> it = tempDir.getDirContent().entrySet().iterator(); it.hasNext();) {
                        Map.Entry<String, FSunit> item = it.next();

                        if(item.getValue().getClass() == FSDirectory.class){
                            rmdir = (FSDirectory) item.getValue();
                            int size1 = -rmdir.getSize();
                            currentDisk.setDiskUsage(size1);
                        }
                        else{
                            rmfile = (FSFile) item.getValue();
                            int size2 = -rmfile.getSize();
                            currentDisk.setDiskUsage(size2);

                        }
                        it.remove();
                    }
                }

            }else{
                System.out.println("Error en el argumento 1");
            }
        }
        else {
            ConsoleIO.printLine("Debe ingresar un argumento");
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);

    }

}
