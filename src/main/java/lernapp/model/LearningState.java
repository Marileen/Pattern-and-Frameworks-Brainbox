package lernapp.model;

import javax.persistence.*;

@Entity(name = "learningState")
public class LearningState {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private long learningStateID;

    @Column(nullable = false)
    private String stateName;

    // LearningState image from media folder
    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String LearningStateDescr;

    public LearningState(){
    }

    public LearningState(String stateName, String imageFileName, String LearningStateDescr){
        this.stateName = stateName;
        this.imageUrl = imageFileName;
        this.LearningStateDescr = LearningStateDescr;
    }

    // Getter and Setter

    public long getLearningStateID() {
        return learningStateID;
    }

    public void setLearningStateID(long learningStateID) {
        this.learningStateID = learningStateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLearningStateDescr() {
        return LearningStateDescr;
    }

    public void setLearningStateDescr(String learningStateDescr) {
        LearningStateDescr = learningStateDescr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
