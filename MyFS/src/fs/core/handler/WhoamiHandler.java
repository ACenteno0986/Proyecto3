package fs.core.handler;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSUser;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

public class WhoamiHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSUser user = currentDisk.UserExist(currentDisk.getName());
        System.out.println("Username: "+user.getUsername());
        System.out.println("Full name: "+ user.getFullname());
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
