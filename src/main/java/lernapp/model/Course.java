package lernapp.model;

import lernapp.model.*;
import javax.persistence.*;

import java.util.Set;

@Entity(name = "course")
public class Course {

    @Id
    @Column
    public int courseID;

    @Column
    public String courseName;

    /*
    @OneToMany(mappedBy = "topic")
    Set<Topic> topicSet;
    */

    public Course(){

    }

    public Course(int courseID, String courseName){

    }

}
