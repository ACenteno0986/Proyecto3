package fs.core.handler;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSunit;
import fs.core.fs.VirtualDisk;

import java.io.IOException;

public class ClearHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")){

                Runtime.getRuntime().exec("cls");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
}
