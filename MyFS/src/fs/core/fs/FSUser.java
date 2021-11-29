package fs.core.fs;

import java.io.Serializable;

import java.util.List;

public class FSUser implements Serializable {
    private String username;
    private String fullname;
    private String passwrd;
    private FSGroup primaryGroup;
    private List<FSGroup> secondaryGroups;

    public FSUser(String username, String fullname, String passwrd,FSGroup group) {
        this.username = username;
        this.fullname = fullname;
        this.passwrd = passwrd;
        this.primaryGroup = group;
        this.primaryGroup = group;
    }

    public FSGroup getPrimaryGroups() {
        return primaryGroup;
    }

    public void setPrimaryGroup(FSGroup group) {
        this.primaryGroup = group;
    }

    public List<FSGroup> getSecondaryGroups(){
        return this.secondaryGroups;
    }

    public void addSecondaryGroup(FSGroup group){
        this.secondaryGroups.add(group);
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

    public FSGroup GroupExist(String validateGroup){
        if(secondaryGroups == null)
            return null;

        for (FSGroup group: secondaryGroups) {
            if(group.getName().equals(validateGroup)){
                return group;
            }
        }
        return null;
    }
}
