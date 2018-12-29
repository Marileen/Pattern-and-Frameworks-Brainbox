package lernapp.model;

import javax.persistence.*;
import java.util.List;

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
    private String imageURL;

    @Lob
    @Column
    private byte[] picture;

    // Beschreibung
    @Column(nullable = false)
    private String LearningStateDescr;

//    @OneToMany(mappedBy = "linkPk.learningState", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    public List<UserQuestionLS> user_question_ls;

    public LearningState(){

    }

    public LearningState(String stateName, byte[] picture, String LearningStateDescr){
        this.stateName = stateName;
        this.picture = picture;
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
}
