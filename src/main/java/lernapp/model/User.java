package lernapp.model;

import javax.persistence.*;
import java.util.List;

// unser User
@Entity(name = "user")
public class User {

    @Column(nullable = false, unique=true)
    public String email; // serves as user name

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private long userID;

    @Column(nullable = false)
    public String firstname;

    public String lastname;

    @Column(nullable = false)
    public String password;

    @Column
    private boolean isAdmin;

    @Transient
    public String jsonWebToken;

//    @OneToMany(mappedBy = "linkPk.user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    public List<UserQuestionLS> user_question_ls;

    public User() {
    }

    public User(String email, String firstname, String lastname,  String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    //Construktor mit Admin Flag
    public User(String email, String firstname, String lastname,  String password, boolean isAdmin) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.isAdmin = isAdmin;
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
}

