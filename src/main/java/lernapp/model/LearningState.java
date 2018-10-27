package lernapp.model;

import javax.persistence.*;

@Entity(name = "learningState")
public class LearningState {

    @Id
    @Column(nullable = false)
    public int learningStateID;

    // z. B. gut, mittel, gar nicht
    @Column(nullable = false)
    public String stateName;

    // zugehöriges Bildchen
    @Column(nullable = false)
    public String pictureUrl;

    // was wollten wir hiermit?
    @Column(nullable = false)
    public String LearningStateDescr;



    public LearningState(){

    }

    public LearningState(int learningStateID, String stateName, String pictureUrl, String LearningStateDescr){
        this.learningStateID = learningStateID;
        this.stateName = stateName;
        this.pictureUrl = pictureUrl;
        this.LearningStateDescr = LearningStateDescr;
    }
}
