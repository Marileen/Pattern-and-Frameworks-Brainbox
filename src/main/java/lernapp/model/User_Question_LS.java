package lernapp.model;

import javax.persistence.*;

import java.util.Set;

@Entity(name = "user_question_ls")
public class User_Question_LS {

    @Id
    @Column
    public int userID;

    @Column
    public int questionID;

    @Column
    public int learningStateID;


    public User_Question_LS(){

    }

    public User_Question_LS(int userID, int questionID, int learningStateID){
        this.userID = userID;
        this.questionID = questionID;
        this.learningStateID = learningStateID;
    }




}
