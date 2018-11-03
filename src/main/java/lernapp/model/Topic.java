package lernapp.model;

import lernapp.model.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "topic")
public class Topic {

    // topicIDs dreeistellig machen? vs. CourseIDs zweistellig?
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "topicID", nullable = false)
    private int topicID;

    @Column(nullable = false)
    private String topicName;

    @Column
    private String topicDescription;

    @OneToMany(mappedBy = "topic")
    private Set<Question> question;

    //various topics belong to one course
    @ManyToOne
    // Fremdschlüsselspalte
    @JoinColumn(name = "fkCourse", nullable = false)
    private Course course;

    public Topic() {

    }

    public Topic(String topicName, String topicDescription, Course course) {
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.course = course;
    }

    // Getter und Setter

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public Set<Question> getQuestion() {
        return question;
    }

    public void setQuestion(Set<Question> question) {
        this.question = question;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
