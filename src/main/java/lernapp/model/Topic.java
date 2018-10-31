package lernapp.model;

import lernapp.model.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "topic")
public class Topic {

    // topicIDs dreeistellig machen? vs. CourseIDs zweistellig?
    @Id
    @Column(name = "topicID", nullable = false)
    public int topicID;

    @Column(nullable = false)
    public String topicName;

    @Column
    public String topicDescription;

   // Column für den Fremdschlüssel anlegen, oder nicht?
    @Column(nullable=false)
    public int courseID;


    @OneToMany(mappedBy = "topic")
    public Set<Question> question;

    public Topic () {

    }

    public Topic(int topicID, String topicName, String topicDescription, int courseID) {
        this.topicID = topicID;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.courseID = courseID;

    }
}
