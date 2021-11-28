package hk.edu.polyu.comp3222.vfs.test.VFSTest;

/**
 * Created by user on 2017/4/7.
 */


import fs.core.fs.FSDirectory;
import fs.core.fs.FSFile;
import fs.core.fs.FSunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import static org.junit.Assert.*;

public class VirualDirectoryTest {
    private FSDirectory mydir;
    private FSDirectory otherdir;
    private FSunit otherunit;
    private FSFile myfile;
    private int size;
    private String name;

    @Before
    public void Setup(){
        mydir = new FSDirectory("root/","1st",new Date());
    }

    @Test
    public void Testlist() throws NullPointerException{

        mydir.list(true);
        mydir.list(false);
        myfile = new FSFile("root/","foo",new Date(),"foo".getBytes());
        mydir.getDirContent().put(myfile.getPath(), myfile);
        mydir.list(true);
    }

    @Test
    public void TestEqual(){
        otherunit = new FSFile("root/", "sth", new Date(), "soemthing".getBytes());
        assertEquals(false,mydir.equals(otherunit));
        otherunit = new FSDirectory("root/", "sth", new Date());
        assertEquals(false,mydir.equals(otherunit));
        Object sth = (FSunit) mydir;
        assertEquals(true,mydir.equals(sth));
    otherdir = mydir;
     assertEquals(true,mydir.equals(otherdir));
     otherunit = mydir;
     assertEquals(true,mydir.equals(otherdir));

    }

    @Test
    public void TestgetItem(){
        myfile = new FSFile("root/","foo",new Date(),"foo".getBytes());
        mydir.getDirContent().put(myfile.getPath(), myfile);
        FSDirectory mydir2 = new FSDirectory("root/", "1st", new Date());
        mydir.getDirContent().put(mydir2.getPath(), mydir2);
        assertEquals(myfile,mydir.getItem(new String[]{"foo"}));
        assertEquals(null,mydir.getItem(new String[]{"foo123"}));
        myfile = new FSFile(mydir2.getPath(),"foo345",new Date(),"foo".getBytes());
        mydir2.getDirContent().put(myfile.getPath(), myfile);
       /* assertEquals(myfile,mydir.getItem(new String[]{"root", "1st","foo345"},myios));*/

    }


    @Test
    public void TestgetSize(){
       size =2 ;
        assertEquals(size,mydir.getSize());
        myfile = new FSFile("root/","foo",new Date(),"foo".getBytes());
        mydir.getDirContent().put(myfile.getPath(), myfile);
        size = 5;
      assertEquals(size,mydir.getSize());
    }

    @Test
    public void Testsetname(){
    name = "abc";
    mydir.setName("abc");
    assertEquals(name,mydir.getName());
    mydir.setName(null);
    assertEquals(".NIL",mydir.getName());
    }



    @After
    public void tardonw(){
        mydir = null;
    }
}
