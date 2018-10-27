package lernapp;

import lernapp.model.*;
import lernapp.service.QuestionService;
import lernapp.service.UserService;

public class App {

    public static void main(String[] args) {

        /* Inject services */
        UserService userService = new UserService();
        QuestionService questionService = new QuestionService();

        // Create users
        User marileen = new User("marileen.stamer@stud.fh-luebeck.de","Marileen","Stamer", "123");
        userService.save(marileen);

        // Create Questions
        Question ersteFrage = new Question(
                001,
                "Was brauchen wir alles für das Projekt?",
                "Mindestens schon mal JPA, Datenbank, Java-Code, zwei Frontends",
                10
                );
        ersteFrage = questionService.save(ersteFrage);

        Question zweiteFrage = new Question(
                002,
                "Was ist der Unterschied zwischen persist und merge?",
                "Merge = Referenz<br> Persist = Objekt selbst wird gespeichert - wie ein create?",
                10
        );
        zweiteFrage = questionService.save(zweiteFrage);

        // create Topics
        Topic erstesThema = new Topic(
                10,
                "Datenbanken",
                "Alle Fragen dieser Topic drehen sich um Datenbanken",
                8
        );
        erstesThema = questionService.save(erstesThema);

        // create Courses
        Course ersterKurs = new Course(
                8,
                "DB"
        );
        ersterKurs = questionService.save(ersterKurs);


        // create LearningState
        LearningState ersterLS = new LearningState(
                8,
                "DB",
                "URL des Bilds",
                "Beschreibung des LearningStates"
        );
        ersterLS = questionService.save(ersterLS);


        // create User_Question_LS
        User_Question_LS ersterUQLS = new User_Question_LS(
                8,
                9,
                3
        );
        ersterUQLS = questionService.save(ersterUQLS);


        // Close database connection
        // GenericService.EMF.close();
    }
}
