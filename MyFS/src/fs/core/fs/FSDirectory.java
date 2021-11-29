package fs.core.fs;

import fs.Util.ConsoleIO;


import java.util.*;

/**
 *
 */
public class FSDirectory extends FSunit {
    private final Map<String, FSunit> dirContent = new LinkedHashMap<>();
    /**
     *
     */
    public FSDirectory(String sourcePath, String name, Date dateCreated){
        super(sourcePath + name + "/", name, dateCreated);
    }

    /**
     *
     */
    public Map<String, FSunit> getDirContent() {
        return dirContent;
    }

    /**
     *
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

    public void chgrpR(String groupName){
        final Iterator<Map.Entry<String, FSunit>> iterator = dirContent.entrySet().iterator();
        FSunit fileSystemUnit;

        while (iterator.hasNext()) {
            fileSystemUnit = iterator.next().getValue();
            if(fileSystemUnit.getClass() == FSDirectory.class){
                fileSystemUnit.setGroup(groupName);

                ((FSDirectory) fileSystemUnit).chgrpR(groupName);
            }else{
                fileSystemUnit.setGroup(groupName);

            }
        }
    }

    public void chownR(String ownName){
        final Iterator<Map.Entry<String, FSunit>> iterator = dirContent.entrySet().iterator();
        FSunit fileSystemUnit;

        while (iterator.hasNext()) {
            fileSystemUnit = iterator.next().getValue();
            if(fileSystemUnit.getClass() == FSDirectory.class){
                fileSystemUnit.setOwner(ownName);

                ((FSDirectory) fileSystemUnit).chownR(ownName);
            }else{
                fileSystemUnit.setOwner(ownName);

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
     *
     */
    public FSunit getItem(String[] itemname){

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
     *
     */
    public FSunit getItemByPath(String path, FSDirectory root) {
        if (path.equals(root.getPath()))
            return root;

        FSunit fileSystemUnit;


        if ((fileSystemUnit = root.getDirContent().get(path)) != null) {
            return fileSystemUnit;
        }


        for (FSunit value : root.getDirContent().values()) {

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
