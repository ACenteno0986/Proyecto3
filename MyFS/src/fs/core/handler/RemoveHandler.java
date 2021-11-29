package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 * Created by Isaac on 2/17/17.
 */
public class RemoveHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir){
        ConsoleIO.printLine("This is the remove command");
        FSFile rmfile;
        FSDirectory rmdir;
        if (cmd.length == 2){

            FSunit tempFile = CurrentDir.getItem(cmd[1].split("/"));
            if(tempFile == null){
                ConsoleIO.printLine("No such file exists");
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
        if(cmd.length == 3){
            if(cmd[1].equals("-R") || cmd[1].equals("-r")){
                System.out.println("argumento: "+cmd[1].split("/"));

            }else{
                System.out.println("Error en el argumento 1");
            }
        }
        else {
            ConsoleIO.printLine("You nned to input an argument");
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);

    }

}
