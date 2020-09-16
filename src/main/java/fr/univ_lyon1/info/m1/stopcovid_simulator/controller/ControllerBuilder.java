package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;

public class ControllerBuilder {

    private Controller controller = new Controller();

    /**
     * Builder method to add user.
     *
     * @param name of the user to add
     */
    public ControllerBuilder addUser(final String name) {
        controller.getUsers().add(new User(name));
        return this;
    }

    /**
     * Builder method to get controller.
     *
     * @return controller
     */
    public Controller build() {
        controller.getUsers().sort(User::compareTo);
        return controller;
    }

}
