package lernapp.model;

import javax.persistence.*;

public class LearningState {

    @Column(nullable = false)
    public String stateName;

    @Column(nullable = false)
    public String pictureUrl;

    @Column(nullable = false)
    public String LearningStateDescr;
    
    @Id
    @Column(nullable = false)
    public int learningStateID;


}
