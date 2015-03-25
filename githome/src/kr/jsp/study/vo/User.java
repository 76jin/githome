package kr.jsp.study.vo;

/**
 *  MEMBER Table in DB
    MEMBERID    VARCHAR(10) NOT NULL PRIMARY KEY,
    PASSWORD    VARCHAR(10) NOT NULL,
    NAME        VARCHAR(20),
    EMAIL
 * 
 */
public class User {
    private String id;
    private String password;
    private String name;
    private String email;
    
    public User() {}

    public User(String memeberId, String password, String name, String email) {
        this.id = memeberId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String memeberId) {
        this.id = memeberId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "[" + id + "," + password + "," + name + "," + email + "]";
    }
    
    
}
