package fs.core.fs;


import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Serializable;
/**
 *
 */
public abstract class FSunit implements Serializable{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");


    private String path;
    private String name;
    private Date dateCreated;
    private String owner = null;
    private int ownerAccessLvl = 7;
    private String group = null;
    private int groupAccessLvl = 7;

    /**
     *
     */
    protected FSunit(String path, String name, Date dateCreated) {
        this.path = path;
        this.name = name;
        this.dateCreated = dateCreated;

    }

    /**
     *
     */
    protected abstract int getSize();

    /**
     *
     */
    public String getPath(){
        return path;
    }

    /**
     *
     */
    public String getName(){
        if(name == null){
            return "noname";
        }
        return name;
    }

    /**
     *
     */
    public void setName(String name){
        int nameLength = this.name.length();
        String tempPath = this.path.substring(0, this.getPath().length() - 1 - nameLength);
        this.name = name;
        this.path = tempPath + "/" + name + "/";
    }
    public void setNameFile(String name){

        int nameLength = this.name.length();
        String tempPath = this.path.substring(0, this.getPath().length() - 1 - nameLength);
        this.name = name;
        this.path = tempPath + "/" + name ;

    }

    /**
     *
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

    public int getOwnerAccessLvl() {
        return ownerAccessLvl;
    }

    public void setOwnerAccessLvl(int ownerAccessLvl) {
        this.ownerAccessLvl = ownerAccessLvl;
    }

    public int getGroupAccessLvl() {
        return groupAccessLvl;
    }

    public void setGroupAccessLvl(int groupAccessLvl) {
        this.groupAccessLvl = groupAccessLvl;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

