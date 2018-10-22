package lernapp.model;

import javax.persistence.*;

@Entity(name = "question")
public class Question {

    @Column(nullable = false)
    public String question;

    @Column(nullable = false)
    public String answer;

    /*@ManyToOne // various questions belong to one topic
    @JoinColumn(name = "topicNr") Topic topic;
    */
    public int topicNr;

    @Id
    @Column(nullable = false)
    public int questionID;

    /* Relationships
       1) n:m LearningState-Question
       2) 1:n Topic-Question -> in die Klasse Topic muss die steuernde Einheit:
          @OneToMany(mappedBy = "") und das Set an Questions, die zu diesem Topic gehören
          für die automatische Vergabe von topicNr in Topic dann @GeneratedValue?
       3) QuestionService
    */

    public Question () {
    }

    public Question(String question, String answer,int topicNr, int questionID) {
        this.question = question;
        this.answer = answer;
        this.topicID = topicID;
        this.questionID = questionID;
    }

    // getter und setter einfügen


}