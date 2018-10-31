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
        this.courseID = courseID;
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
