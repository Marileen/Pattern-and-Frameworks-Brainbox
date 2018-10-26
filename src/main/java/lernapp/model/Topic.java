package lernapp.model;

import lernapp.model.*;

import javax.persistence.*;
import java.util.Set;

public class Topic {

    @Column(nullable = false)
    public String topicName;

    @Column
    public String topicDescription;

    @Column(nullable=false)
    public int courseID;

    @Id
    @Column(nullable = false)
    public int topicID;

    @OneToMany(mappedBy = "topic")
    Set<Question> questionSet;

    @ManyToOne @JoinColumn(name = "courseID")
    Course course;

}
