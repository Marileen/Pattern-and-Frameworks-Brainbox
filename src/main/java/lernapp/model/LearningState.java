package lernapp.model;

import javax.persistence.*;

@Entity(name = "learningState")
public class LearningState {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private long learningStateID;

    // z. B. gut, mittel, noch nicht
    @Column(nullable = false)
    private String stateName;

    // zugehöriges Bildchen
    // wird aus einem media verzeichnis ausgeliefert (weil man Bilder nicht in der DB speichert)
    @Column
    private String imageUrl;

    // Beschreibung
    @Column(nullable = false)
    private String LearningStateDescr;

//    @OneToMany(mappedBy = "linkPk.learningState", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    public List<UserQuestionLS> user_question_ls;

    public LearningState(){

    }

    public LearningState(String stateName, String imageFileName, String LearningStateDescr){
        this.stateName = stateName;
        this.imageUrl = imageUrl;
        this.LearningStateDescr = LearningStateDescr;
    }


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
