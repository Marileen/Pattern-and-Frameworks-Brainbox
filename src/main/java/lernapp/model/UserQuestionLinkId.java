package lernapp.model;


import javax.persistence.*;

//@Embeddable
//@IdClass(UserQuestionLinkId.class)
public class UserQuestionLinkId implements java.io.Serializable {

    private User user;

    private Question question;
}