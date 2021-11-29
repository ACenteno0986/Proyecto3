package fs.core.handler;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class InfoFSHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        String name = currentDisk.getFsName();
        int totalsize = currentDisk.getSize();
        int usagesize = currentDisk.getDiskUsage();
        int restsize = totalsize-usagesize;

        System.out.println("Nombre del FileSystem: "+name);
        System.out.println("Tamaño: "+totalsize);
        System.out.println("Espacio Utilizado: "+usagesize);
        System.out.println("Disponible: "+restsize);

        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
