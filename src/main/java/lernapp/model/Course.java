package lernapp.model;

import lernapp.model.*;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Set;

public class Course {

    @Column
    public String courseName;

    @Column
    public int courseID;

    @OneToMany(mappedBy = "topic")
    Set<Topic> topicSet;

}
