package lernapp.service;

import lernapp.model.*;

import javax.persistence.*;

public class UserService extends BasicService<User> {

    public UserService( ) {
        super(User.class);
    }

    public User queryByCredentials (String email, String password) {

        try {
            EntityManager em = EMF.createEntityManager();
            String queryString = "FROM user WHERE email = :mail AND password = :p";
            TypedQuery<User> q = em.createQuery(queryString, User.class);
            q.setParameter("mail", email);
            q.setParameter("p", password);
            User user = q.getSingleResult();
            em.close();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

}

