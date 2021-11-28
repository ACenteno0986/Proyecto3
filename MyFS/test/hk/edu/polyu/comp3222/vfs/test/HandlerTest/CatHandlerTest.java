package hk.edu.polyu.comp3222.vfs.test.HandlerTest;

/**
 * Created by user on 2017/4/8.
 */


import fs.core.handler.CatHandler;
import fs.core.fs.VirtualDisk;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;





public class CatHandlerTest {
    private VirtualDisk mydisk;
    private CatHandler myhandler;
    private String[] cmd;


    @Before
    public void setup(){
        mydisk = new VirtualDisk("test","test",13224);
        mydisk.initializeFileSystem();

        cmd = new String[]{"cat","file3"};
        myhandler = new CatHandler();

    }

@Test
    public void testcat(){
    myhandler.handlerResponse(cmd,mydisk,mydisk.getROOT_FS(),mydisk.getCurrentDir());
    cmd = new String[]{"cat","1st"};
    myhandler.handlerResponse(cmd,mydisk,mydisk.getROOT_FS(),mydisk.getCurrentDir());
    cmd = new String[]{"cat","1st111"};
    myhandler.handlerResponse(cmd,mydisk,mydisk.getROOT_FS(),mydisk.getCurrentDir());
    cmd = new String[]{"cat"};
    myhandler.handlerResponse(cmd,mydisk,mydisk.getROOT_FS(),mydisk.getCurrentDir());
    }

@After
    public void tardown(){
        myhandler= null;
}

}
