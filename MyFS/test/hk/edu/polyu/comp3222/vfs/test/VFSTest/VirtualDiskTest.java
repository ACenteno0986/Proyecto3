
package hk.edu.polyu.comp3222.vfs.test.VFSTest;

import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.VirtualDisk;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Max.
 */
public class VirtualDiskTest {
    private VirtualDisk testDisk;
    private String testName;
    private String path;
    private FSDirectory stfile;
    private FSFile foofile;
    private FSFile nullfile;
    private FSDirectory currentdir;
    private int size;



    @Before
    public void setup(){
        testDisk = new VirtualDisk("test", "test", 13356);
    }

    @Test
    public void testGetName(){
        testName = "test";
        assertEquals(testName, testDisk.getName());
    }

    @Test
    public void testgetItemByPath(){
        FSDirectory stfile = new FSDirectory("root/", "1st", new Date());
        FSFile foofile = new FSFile("root/1st/","foo",new Date(), "1111".getBytes());
        FSFile nullfile = null;
        path = "root/1st/";
        assertEquals(stfile.getName(),testDisk.getROOT_FS().getItemByPath(path,testDisk.getROOT_FS()).getName());
        path="root/1st/foo";
        assertEquals(foofile.getName(),testDisk.getROOT_FS().getItemByPath(path,testDisk.getROOT_FS()).getName());
       path="root/1st/foo/1st";
        assertEquals(nullfile,testDisk.getROOT_FS().getItemByPath(path,testDisk.getROOT_FS()));
    }


    @Test
    public void testgetsize(){
        size = 13356;
        assertEquals(size,testDisk.getSize());
    }

    @Test
    public void testgetCurrentdir(){
        currentdir = testDisk.getCurrentDir();
     assertEquals(currentdir,testDisk.getCurrentDir());

    }

    @Test
    public void testsetCurrentdir(){
        currentdir = new FSDirectory("root/2nd","3rd",new Date());
        testDisk.setCurrentDir(currentdir);
        currentdir= testDisk.getCurrentDir();
        assertEquals(currentdir,testDisk.getCurrentDir());
    }
    @After
    public void tarDown(){
        testDisk = null;
    }

}