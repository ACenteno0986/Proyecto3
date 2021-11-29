package fs.core.handler;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class ViewFilesOpenHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {
        int oFilesSize = currentDisk.getOpenFiles().size();
        System.out.println("Total de archivos abiertos: "+oFilesSize);
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
