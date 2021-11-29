package fs.core.fs;


import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Serializable;
/**
 * Created by selcuk on 23.01.2017.
 */
public abstract class FSunit implements Serializable{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");


    private String path;
    private String name;
    private Date dateCreated;
    private String owner = null;
    private String group = null;

    /**
     * @param path: the path of this FSunit
     * @param name: the name of this FSunit
     * @param dateCreated
     * This is something
     */
    protected FSunit(String path, String name, Date dateCreated) {
        this.path = path;
        this.name = name;
        this.dateCreated = dateCreated;

    }
/*
    /**
     * abstract list method
     * @param detailed: if this list should be detailed
     * @param ioservice: ioservice for this method

    protected abstract void list(boolean detailed, IOService ioservice);
*/
    /**
     * get size method
     * @return return the size of this FSunit
     */
    protected abstract int getSize();

    /**
     * get path method
     * @return path of this FSunit in String format
     */
    public String getPath(){
        return path;
    }

    /**
     * getName method
     * @return name of this FSunit
     */
    public String getName(){
        if(name == null){
            return ".NIL";
        }
        return name;
    }

    /**
     * set name method
     * @param name name to be set for this VFS unit
     */
    public void setName(String name){
        int nameLength = this.name.length();
        String tempPath = this.path.substring(0, this.getPath().length() - 1 - nameLength);
        this.name = name;
        this.path = tempPath + "/" + name + "/";
    }

    /**
     * get date created method
     * @return data created in string format
     */
    public String getDateCreated() {
        return dateFormat.format(dateCreated);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FSunit)) return false;

        FSunit that = (FSunit) o;

        return dateCreated.equals(that.getDateCreated()) && name.equals(that.getName()) && path.equals(that.getPath());
    }
    @Override
    public String toString(){
        return path;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

