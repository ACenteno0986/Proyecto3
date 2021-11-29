package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 *
 */
public class DirectResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){

        this.saveState(cmd, currentDisk, Root, CurrentDir);
        if(cmd.length == 1){
            CurrentDir = Root;
        }else {

            if(cmd[1].equals("..")){

                String[] newDirList = CurrentDir.getPath().split("/");
                String newDir = "";

                for(int i =0; i<newDirList.length-1;i++ ){

                    newDir += newDirList[i]+"/";
                }

                String[] fsName = currentDisk.getFsName().split("\\.");

                if(newDir.equals(fsName[0]+"/")){

                    CurrentDir = Root;
                }else{
                    String[] newDirList2 = newDir.split("/");
                    FSunit fsunitto = Root.getItemByPath(newDir,Root);
                    if (fsunitto == null) {
                        ConsoleIO.printLine("Error al ejecutar el comando con ..");

                    }
                    else {

                        CurrentDir = (FSDirectory) fsunitto;
                    }
                }



            }
            else{
                String[] seachPath = cmd[1].split("/");

                FSunit fileSystemUnit = CurrentDir.getItem(seachPath);
                if (fileSystemUnit == null) {
                    ConsoleIO.printLine("This directory is not found on this VFS");

                } else {
                    if (fileSystemUnit.getClass() == FSFile.class) {
                        ConsoleIO.printLine("The target path is not a directory");
                    }else {
                        CurrentDir = (FSDirectory) fileSystemUnit;
                    }

                }

            }

        }
        return this.saveState(cmd, currentDisk, Root, CurrentDir);

    }
}
