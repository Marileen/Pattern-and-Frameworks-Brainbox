package lernapp.model;

import lernapp.model.Topic;
import javax.persistence.*;


@Entity(name = "question")
//@Table(name = "questions")
public class Question {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private int questionID;

//    @Lob annotation is used to map fields/properties of large value to a corresponding database-supported large object type.
//    A Lob may be either a binary or character type.

    @Lob
    @Column(nullable = false )
    private String question;

    @Lob
    @Column(nullable = false )
    public String answer;

    //@Column(nullable = false) // evtl. brauchen wir hier: insertable = false, updatable = false
    //public int topicID;

    //various questions belong to one topic
    @ManyToOne
    // Fremdschlüsselspalte = Bezug zu Topic
    @JoinColumn(nullable = false)
    private Topic topic;

    /* Relationships
       1) n:m LearningState-Question
       2) 1:n Topic-Question -> in die Klasse Topic muss die steuernde Einheit:
          @OneToMany(mappedBy = "") und das Set an Questions, die zu diesem Topic gehören
          für die automatische Vergabe von topicID in Topic dann @GeneratedValue?
       3) QuestionService
    */

    public Question() {
    }

    public Question(String question, String answer,  Topic topic) {
        this.question = question;
        this.answer = answer;
        this.topic = topic;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
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