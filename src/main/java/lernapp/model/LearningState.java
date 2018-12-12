package lernapp.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "learningState")
public class LearningState {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    public long learningStateID;

    // z. B. gut, mittel, noch nicht
    @Column(nullable = false)
    public String stateName;

    // zugehöriges Bildchen
    @Column(nullable = false)
    public String pictureUrl;

    // Beschreibung
    @Column(nullable = false)
    public String LearningStateDescr;

//    @OneToMany(mappedBy = "linkPk.learningState", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    public List<UserQuestionLS> user_question_ls;

    public LearningState(){

    }

    public LearningState(String stateName, String pictureUrl, String LearningStateDescr){
        this.stateName = stateName;
        this.pictureUrl = pictureUrl;
        this.LearningStateDescr = LearningStateDescr;
    }
}
