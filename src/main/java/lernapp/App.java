package lernapp;

        import lernapp.model.User;
        import lernapp.service.UserService;
        import lernapp.service.GenericService;

public class App {

    public static void main(String[] args) {

        // Inject services
        UserService userService = new UserService();

        // Create users

        User marileen = new User("Marileen","Stamer","marileen.stamer@stud.fh-luebeck.de", "123");

        marileen = userService.save(marileen);

        // Close database connection
        GenericService.EMF.close();
    }
}
