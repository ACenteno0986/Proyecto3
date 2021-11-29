package fs.core.fs;

import fs.Util.ConsoleIO;


import java.util.*;

/**
 * Created by Isaac on 1/23/17.
 */
public class FSDirectory extends FSunit {
    private final Map<String, FSunit> dirContent = new LinkedHashMap<>();
    /**
     * Default constructor.
     * @param sourcePath sourcePath from parent directory
     * @param name name of this vfs directory
     * @param dateCreated date created of this FSDirectory
     */
    public FSDirectory(String sourcePath, String name, Date dateCreated){
        super(sourcePath + name + "/", name, dateCreated);
    }

    /**
     * get directory content method
     * @return the content of directory
     */
    public Map<String, FSunit> getDirContent() {
        return dirContent;
    }

    /**
     * list all content inside this directory
     * @param detailed if detailed content is shown
     */
    public void list(boolean detailed) {

        final Iterator<Map.Entry<String, FSunit>> iterator = dirContent.entrySet().iterator();
        FSunit fileSystemUnit;
        String tipo = "";
        while (iterator.hasNext()) {
            fileSystemUnit = iterator.next().getValue();
            if(fileSystemUnit.getClass() == FSDirectory.class){
                tipo = " -> Directorio";
            }else{
                tipo = "-> Archivo";
            }
            ConsoleIO.printLine(fileSystemUnit.toString()+tipo);



        }
    }

    public void listR() {

        final Iterator<Map.Entry<String, FSunit>> iterator = dirContent.entrySet().iterator();
        FSunit fileSystemUnit;
        String tipo = "";
        while (iterator.hasNext()) {
            fileSystemUnit = iterator.next().getValue();
            if(fileSystemUnit.getClass() == FSDirectory.class){
                tipo = " -> Directorio";
                ConsoleIO.printLine(fileSystemUnit.toString()+tipo);
                ((FSDirectory) fileSystemUnit).listR();
            }else{
                tipo = "-> Archivo";
                ConsoleIO.printLine(fileSystemUnit.toString()+tipo);
            }

        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;

        if (!(o instanceof FSDirectory)) return false;

        if (!super.equals(o)) return false;

        FSDirectory that = (FSDirectory) o;
        return dirContent.equals(that.getDirContent());
    }

    /**
     * get item from current directory method
     * @param itemname string array of the path & name of target item
     * @return return the target item or null if not found
     */
    public FSunit getItem(String[] itemname){
        //final Iterator<Map.Entry<String, FSunit>> iterator = dirContent.entrySet().iterator();
        FSunit fileSystemUnit;
        int level = 0;
        for (String key:dirContent.keySet()) {

            fileSystemUnit = dirContent.get(key);

            if (fileSystemUnit.getName().equals(itemname[level])) {

                if(itemname.length == level + 1){
                    return fileSystemUnit;
                }else{
                    level++;

                    String[] newItemName = Arrays.copyOfRange(itemname, level, itemname.length);
                    FSDirectory tempDir = (FSDirectory) fileSystemUnit;
                    return tempDir.getItem(newItemName);
                }
            }
            //break;
        }
        return null;
    }

    @Override
    public int getSize(){
        int sum = 2;
        for (FSunit value : dirContent.values()) {
                sum += value.getSize();
        }
        return sum;
    }

    /**
     * retrive item from current directory by path
     * @param path path of the target item
     * @param root to-be-searched directory
     * @return return the search result
     */
    public FSunit getItemByPath(String path, FSDirectory root) {
        if (path.equals(root.getPath()))
            return root;

        FSunit fileSystemUnit;

        // check first whether the object with the specified path exists in source folder
        if ((fileSystemUnit = root.getDirContent().get(path)) != null) {
            return fileSystemUnit;
        }

        // if not, deep check every single entry in the current directory
        for (FSunit value : root.getDirContent().values()) {
            //ConsoleIO.printLine(value.getPath());
            if (value.getClass() == FSDirectory.class) {
                fileSystemUnit = getItemByPath(path, (FSDirectory) value);
            } else {
                fileSystemUnit = value;
            }

            if (fileSystemUnit != null && path.equals(fileSystemUnit.getPath()))
                return fileSystemUnit;
        }
        return null;
    }

}
