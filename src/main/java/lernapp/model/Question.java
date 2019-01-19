package lernapp.model;

import javax.persistence.*;
import java.util.List;


@Entity(name = "question")
public class Question {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private long questionID;

    @Lob
    @Column(nullable = false )
    private String question;

    @Lob
    @Column(nullable = false )
    private String answer;

    //various questions belong to one single topic
    //topic is foreign key
    //@JsonIgnore
    @ManyToOne //(fetch = FetchType.LAZY) // EAGER is default, alternative would be LAZY
    @JoinColumn(nullable = false)
    private Topic topic;

    public Question() {
    }

    public Question(String question, String answer,  Topic topic) {
        this.question = question;
        this.answer = answer;
        this.topic = topic;
    }

    // Getter and Setter

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}