package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.io.File;
import java.io.IOException;

/**
 * Created by Isaac on 2/17/17.
 */
public class ExportResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String [] cmd, VirtualDisk disk, FSDirectory Root, FSDirectory CurrentDir){
        ConsoleIO.printLine("This is the export handler");
        if(cmd.length != 2){
            ConsoleIO.printLine("export command expects one argument");
            return null;
        }

        FSunit tempFile = CurrentDir.getItem(cmd[1].split("/"));

 //       ConsoleIO.printLine(tempFile.getClass().toString());
        if(tempFile == null){
            ConsoleIO.printLine("no such file found!");
        }else {
            if (tempFile.getClass() == FSDirectory.class) {
                createDirectory((FSDirectory) tempFile);
            } else {
                createFile((FSFile) tempFile);
            }
        }

        return this.saveState(cmd, disk, Root, CurrentDir);
    }

    /**
     * create file in host file system
     * @param outFile Virtual file to be exported
     */
    public void createFile(FSFile outFile){
        String tempPath = "host/" + outFile.getPath();
        File tempFile = new File(tempPath);
        try {
            tempFile.getParentFile().mkdirs();
            tempFile.createNewFile();
        }catch (IOException e){
            ConsoleIO.printLine("create file failed, action abort.");
        }
    }

    /**
     * create directory in host file system
     * @param outDir virtual directory to be exported
     */
    public void createDirectory(FSDirectory outDir){
        String tempPath = "host/" + outDir.getPath();
        File tempDir = new File(tempPath);
        if (!tempDir.exists()) {
            ConsoleIO.printLine("creating directory in host/: " + tempDir.getName());
            boolean result = false;

            try{
                tempDir.mkdirs();
                result = true;
            }
            catch(SecurityException se){
                //handle it
                ConsoleIO.printLine("create directory failed");
            }
            if(result) {
                ConsoleIO.printLine("DIR created");
            }
        }else{
            ConsoleIO.printLine("directory already exists. Operation abort");
        }
    }
}
