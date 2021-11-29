package fs.core.fs;

import java.io.Serializable;

public class FSGroup implements Serializable {

    String name;

    public FSGroup(String name){
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
