package fs.core.handler;


import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by Isaac on 2/17/17.
 */
public class ImportResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String [] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir) {

        this.saveState(cmd, currentDisk, Root, CurrentDir);
        if (cmd.length == 3 && cmd[1].equals("-f")) {
            byte[] content = readFile(cmd[2]);
            //ConsoleIO.printLine(new String(content));
            FSFile newFile = new FSFile(CurrentDir.getPath(), cmd[1], new Date(), content);
            CurrentDir.getDirContent().put(newFile.getPath(), newFile);
            return this.saveState(cmd, currentDisk, Root, CurrentDir);
        }else if(cmd.length == 3 && cmd[1].equals("-r")){
            String dirName = readDirectory(cmd[2]);
            FSDirectory tempDirectory = new FSDirectory(CurrentDir.getPath(), dirName, new Date());
            CurrentDir.getDirContent().put(tempDirectory.getPath(), tempDirectory);
            return this.saveState(cmd, currentDisk, Root, CurrentDir);
        }else {
            ConsoleIO.printLine("import command requires two argument:");
            ConsoleIO.printLine("import <-f filename> or <-r directoryname>");
            return this.saveState(cmd, currentDisk, Root, CurrentDir);
        }

    }

    /**
     * read file in host file system and output as byte array
     * @param filePath path of the file to be read
     * @return return the byte array of the content of the file
     */
    public byte[] readFile(String filePath){
        //List<String> records = new ArrayList<String>();
        try
        {
            Path tempPath = Paths.get("host/" + filePath);
            return Files.readAllBytes(tempPath);
        }
        catch (IOException e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * read directory from host file system
     * @param dirPath path of directory on host file system
     * @return return the name of the directory
     */
    public String readDirectory(String dirPath){
            Path dir = Paths.get("host/" + dirPath);
            return dirPath;
    }

}
