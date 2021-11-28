package hk.edu.polyu.comp3222.vfs.test.VFSTest;

/**
 * Created by user on 2017/4/7.
 */
import fs.Util.IOService;

import fs.core.fs.FSFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import static org.junit.Assert.*;





public class FSFileTest {
    private FSFile myfile;
    private String content;
    private IOService myios;
    private String tostring;
@Before
    public void setup(){
    myfile = new FSFile("root/","foo",new Date(),"123".getBytes());
}

@Test
  public void testGetcontent(){
        content = "123";
        assertEquals(content,myfile.getContent());
}



@Test
public void testtoString(){
    tostring = myfile.getName()+" - "+myfile.getPath()+" - "+myfile.getDateCreated();
            assertEquals(tostring,myfile.toString());

}


@After
    public void tardown(){
        myfile = null;
}
}
