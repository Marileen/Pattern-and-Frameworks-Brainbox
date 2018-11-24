package lernapp.model;


import javax.persistence.*;

//@Embeddable
//@IdClass(UserQuestionLinkId.class)
public class UserQuestionLinkId implements java.io.Serializable {

    //@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //@JoinColumn
    private User user;

    //@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //@JoinColumn
    private Question question;
}