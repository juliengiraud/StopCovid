package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<User> users = new ArrayList<>();

    public Controller(final int nbUsers) {
        for (int i = 0; i < nbUsers; i++) {
            users.add(new User("User " + i));
        }
    }

    public List<User> getUsers() {
        return users;
    }

}
