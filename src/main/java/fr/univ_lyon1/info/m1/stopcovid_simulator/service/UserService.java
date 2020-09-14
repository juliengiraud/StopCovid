package fr.univ_lyon1.info.m1.stopcovid_simulator.service;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final List<User> users = new ArrayList<>();

    /**
     * Constructor of the user service class.
     *
     * @param nbUsers
     */
    public UserService(final int nbUsers) {
        for (int i = 0; i < nbUsers; i++) {
            users.add(new User("User " + (i + 1)));
        }
    }

    /**
     * Get all users.
     *
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Create new meet between two users.
     * @param a One user
     * @param b Another user
     */
    public void addMeet(final User a, final User b) {
        a.meet(b);
    }

}
