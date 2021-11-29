package fs.core.handler;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class PwdHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        System.out.println(currentDisk.getCurrentDir());

        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
