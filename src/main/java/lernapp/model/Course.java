package lernapp.model;

import lernapp.model.*;
import javax.persistence.*;

import java.util.Set;

@Entity(name = "course")
public class Course {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    public int courseID;

    @Column
    public String courseName;

    @OneToMany(mappedBy = "course")
    public Set<Topic> topicSet;

    public Course(){
    }

    public Course(String courseName){
        this.courseName = courseName;
    }

    // SETTER
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    //GETTER
    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }
}
