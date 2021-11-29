package fs.core.handler;

import fs.Util.ConsoleIO;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.util.Arrays;

/**
 *
 */
public class SearchResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){


        if(cmd.length < 2){
            ConsoleIO.printLine("whereis requiere al menos 1 argumento\n");
        }else{
            searchResult(cmd[1], CurrentDir);

        }
        return this.saveState(cmd, currentDisk, Root, CurrentDir);
    }

    /**
     * search a single file inside
     * @param itemName name of target file
     * @param dir directory to be searched
     */
    public void searchResult(String itemName, FSDirectory dir){
        FSunit fileSystemUnit = dir.getItem(itemName.split("/"));
        if (fileSystemUnit != null) {
            ConsoleIO.printLine(fileSystemUnit.getPath());
        }
        for(FSunit s: dir.getDirContent().values()) {
            if(s.getClass() == FSDirectory.class){
                searchResult(itemName, (FSDirectory) s);
            }
        }
    }
}
