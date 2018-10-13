package lernapp.model;

import javax.persistence.*;

@Entity(name = "user")
public class User {

    @Column(nullable = false)
    public String firstname;

    public String lastname;

    @Column(nullable = false)
    public String password;

    @Id
    @Column(nullable = false)
    public String email;

//    @ManyToMany(mappedBy = "user")
//    public List<Learningstate> orders;


    public User() {
    }

    public User(String firstname, String lastname, String email,  String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
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

}

