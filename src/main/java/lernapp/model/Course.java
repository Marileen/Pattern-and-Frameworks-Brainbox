package lernapp.model;

import lernapp.model.*;
import javax.persistence.*;

import java.util.Set;

@Entity(name = "course")
public class Course {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int courseID;

    @Column
    private String courseName;

    @OneToMany(mappedBy = "course")
    private Set<Topic> topicSet;

    public Course(){
    }

    public Course(String courseName){
        this.courseName = courseName;
    }

    // Getter and Setter
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public int getCourseID() {
        return courseID;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseName() {
        return courseName;
    }

}
