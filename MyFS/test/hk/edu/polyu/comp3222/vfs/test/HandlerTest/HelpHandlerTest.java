package hk.edu.polyu.comp3222.vfs.test.HandlerTest;

/**
 * Created by user on 2017/4/9.
 */

import fs.core.handler.HelpHandler;

import fs.core.fs.VirtualDisk;

import org.junit.Before;
import org.junit.Test;




public class HelpHandlerTest {
    private VirtualDisk mydisk;
    private HelpHandler myhandler;
    private String[] cmd;

    @Before
    public void setup() {
        mydisk = new VirtualDisk("test", "test", 13224);
        mydisk.initializeFileSystem();
        myhandler = new HelpHandler();
    }

    @Test
    public void testhelp(){
        cmd = new String[]{"help"};
        myhandler.handlerResponse(cmd,mydisk,mydisk.getROOT_FS(),mydisk.getCurrentDir());


    }

}
