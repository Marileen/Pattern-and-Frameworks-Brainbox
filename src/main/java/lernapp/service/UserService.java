package lernapp.service;

import lernapp.model.User;

public class UserService extends GenericService<User> {

    public UserService() {
        super(User.class);
    }
}