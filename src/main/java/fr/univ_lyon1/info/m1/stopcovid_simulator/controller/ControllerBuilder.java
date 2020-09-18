package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserDAO;

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
     * Builder method to get controller with data from the CSV file if asked.
     *
     * @param includeCSV true if we need to load the file
     * @return controller
     */
    public Controller build(final boolean includeCSV) {
        if (includeCSV) {
            for (User u : UserDAO.getInstance().getUsers()) { // Load users from the CSV file
                if (!controller.getUsers().contains(u)) {
                    controller.getUsers().add(u);
                }
            }
        }

        // Save current list of users in the CSV file
        UserDAO.getInstance().saveUsers(controller.getUsers());

        // Sort users by name before to return controller
        controller.getUsers().sort(User::compareTo);
        return controller;
    }

    /**
     * Builder method to get controller.
     *
     * @return controller
     */
    public Controller build() {
        return build(false);
    }

}
