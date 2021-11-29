package fs.core.handler;


import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

/**
 *
 */
public class ListResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk disk, FSDirectory Root, FSDirectory CurrentDir){
        if(cmd.length == 1){
            CurrentDir.list(true);
        }
        else if(cmd.length == 2){
            CurrentDir.listR();
        }
        else{
            ConsoleIO.printLine("Error en los argumentos de ls");
        }



        return this.saveState(cmd, disk, Root, CurrentDir);
    }
}
