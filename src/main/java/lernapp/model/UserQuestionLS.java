package lernapp.model;

import javax.persistence.*;

import java.io.Serializable;

import lernapp.model.*;
import lernapp.resources.QuestionsResource;

@Entity(name = "userQuestionLs")
@IdClass(UserQuestionLinkId.class)
public class UserQuestionLS {

    //belongs to one user
    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    private User user;

    //one question
    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    private Question question;

    //and one LS
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    private LearningState learningState;

    public UserQuestionLS(){

    }

    public UserQuestionLS(User user, Question question, LearningState learningState){
        this.user = user;
        this.question = question;
        this.learningState = learningState;
    }

}

