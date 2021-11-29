package fs.core.fs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FSUser implements Serializable {
    private String username;
    private String fullname;
    private String passwrd;
    private List<String> groups;

    public FSUser(String username, String fullname, String passwrd,String group) {
        this.username = username;
        this.fullname = fullname;
        this.passwrd = passwrd;
        this.groups = new ArrayList<>();
        this.groups.add(group);
    }

    public List<String> getGroups() {
        return groups;
    }

    public void addTo(String group) {
        this.groups.add(group);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }
}
