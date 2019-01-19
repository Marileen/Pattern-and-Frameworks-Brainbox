package lernapp.model;

import javax.persistence.*;

@Entity(name = "user")
public class User {

    @Column(nullable = false, unique=true)
    private String email;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private long userID;

    @Column(nullable = false)
    private String firstname;

    @Column
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Column
    private boolean isAdmin;

    @Transient
    private String jsonWebToken;

    public User() {
    }

    public User(String email, String firstname, String lastname,  String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    // Constructor with Admin Flag
    public User(String email, String firstname, String lastname,  String password, boolean isAdmin) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getter and Setter

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getJsonWebToken() {
        return jsonWebToken;
    }

    public void setJsonWebToken(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }
}

