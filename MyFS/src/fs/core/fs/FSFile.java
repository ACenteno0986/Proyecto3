package fs.core.fs;


import java.util.Date;
/**
 * Created by Isaac on 1/23/17.
 */
public class FSFile extends FSunit {
    private byte[] content;
    private boolean open = false;

    /**
     * VFS file
     * @param path path of FSFile's parent directory
     * @param name name of this FSFile
     * @param dateCreated date created of this FSFile
     * @param content content of this FSFile
     */
    public FSFile(String path, String name, Date dateCreated, byte[] content) {
        super(path + name, name, dateCreated);
        this.content = content;
    }

    /**
     * get content of this VFSfile
     * @return return the content in string format
     */
    public String getContent() {
        String s = new String(content);
        return s;
    }


    @Override
    public int getSize(){
        return content.length;
    }

}
