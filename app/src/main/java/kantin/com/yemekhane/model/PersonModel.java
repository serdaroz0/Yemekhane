package kantin.com.yemekhane.model;

import java.io.Serializable;

public class PersonModel implements Serializable {

    private String fullName;
    private String classNumber;
    private String className;
    private String menu;
    private String time;
    private Boolean statusSave;

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public Boolean getStatusSave() {
        return statusSave;
    }

    public void setStatusSave(Boolean statusSave) {
        this.statusSave = statusSave;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public PersonModel(String fullName, String classNumber, String className, String menu, String time, Boolean statusSave) {
        this.fullName = fullName;
        this.classNumber = classNumber;
        this.className = className;
        this.menu = menu;
        this.time = time;
        this.statusSave = statusSave;
    }
}
