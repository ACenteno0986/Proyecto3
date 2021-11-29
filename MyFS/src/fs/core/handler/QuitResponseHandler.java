package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import static java.lang.System.exit;

/**
 *
 */
public class QuitResponseHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory Root, FSDirectory CurrentDir){
        ConsoleIO.printLine("Saliendo del sistema de archivos");
        this.saveState(cmd, currentDisk, Root, CurrentDir);

        return null;
    }
}
