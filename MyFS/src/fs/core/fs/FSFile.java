package fs.core.fs;


import java.util.Date;
/**
 *
 */
public class FSFile extends FSunit {
    private byte[] content;
    private boolean open = false;

    /**
     *
     */
    public FSFile(String path, String name, Date dateCreated, byte[] content) {
        super(path + name, name, dateCreated);
        this.content = content;
    }

    /**
     *
     */
    public String getContent() {
        String s = new String(content);
        return s;
    }


    @Override
    public int getSize(){
        return content.length;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
