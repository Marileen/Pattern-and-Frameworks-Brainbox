package lernapp;

        import lernapp.model.Question;
        import lernapp.model.User;
        import lernapp.service.QuestionService;
        import lernapp.service.UserService;

public class App {

    public static void main(String[] args) {

        /* Inject services */
        UserService userService = new UserService();
        QuestionService questionService = new QuestionService();

        // Create users
        User marileen = new User("Marileen","Stamer","marileen.stamer@stud.fh-luebeck.de", "123");
        userService.save(marileen);

        // Create Questions
        Question ersteFrage = new Question(
                "Was brauchen wir alles für das Projekt?",
                "Mindestens schon mal JPA, Datenbank, Java-Code, zwei Frontends",
                10,
                001
                );
        ersteFrage = questionService.save(ersteFrage);


        // Close database connection
        // GenericService.EMF.close();
    }
}
