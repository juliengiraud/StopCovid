package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<User> users = new ArrayList<>();

    /**
     * Controller for the whole application.
     * @param nbUsers
     */
    public Controller(final int nbUsers) {
        for (int i = 0; i < nbUsers; i++) {
            users.add(new User("User " + i));
        }
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * Return user with matching id, null if not found.
     * @param id
     * @return user
     */
    public User getUserById(final int id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    /**
     * Create new meet between two users.
     * @param a
     * @param b
     */
    public void addMeet(final User a, final User b) {
        a.meet(b);
    }

}
