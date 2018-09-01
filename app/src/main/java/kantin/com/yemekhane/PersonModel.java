package kantin.com.yemekhane;

import java.io.Serializable;

public class PersonModel implements Serializable {

    private String fullName;
    private String classNumber;
    private String className;
    private String menu;
    private String time;

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

    public String getCllassNumber() {
        return classNumber;
    }

    public void setCllassNumber(String cllassNumber) {
        this.classNumber = cllassNumber;
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

    public PersonModel(String fullName,  String classNumber, String className, String menu, String time) {
        this.fullName = fullName;
        this.className = className;
        this.classNumber = classNumber;
        this.menu = menu;
        this.time = time;
    }
}
