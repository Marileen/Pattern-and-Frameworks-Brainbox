package lernapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lernapp.model.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "topic")
@Access(value = AccessType.FIELD)           //Field access or Property Access (via getters & setters) z.B. wenn man noch valitaion oder andere business logik machen will
public class Topic {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private long topicID;

    @Column(nullable = false)
    private String topicName;

    @Column
    private String topicDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "topic" )
    private Set<Question> question;

    //various topics belong to one course
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)       // Fremdschlüssel
    private Course course;

    public Topic() {

    }

    public Topic(String topicName, String topicDescription, Course course) {
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.course = course;
    }

    // Getter und Setter

    public void setTopicID(Long topicID) {
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
