package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.service.UserService;
import fr.univ_lyon1.info.m1.stopcovid_simulator.view.StopCovidView;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final List<StopCovidView> views;
    private final UserService userService;

    /**
     * Controller for the whole application.
     * @param nbUsers number of user
     */
    public Controller(final int nbUsers) {
        views = new ArrayList<>();
        userService = new UserService(nbUsers);
    }

    /**
     * Get all users from UserService.
     *
     * @return users
     */
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * Create new meet between two users from UserService.
     * @param a One user
     * @param b Another user
     */
    public void addMeet(final User a, final User b) {
        userService.addMeet(a, b);
    }

    /**
     * Add a view to the viewList so that the controller can update all the views.
     * @param view The new view
     */
    public void addView(final StopCovidView view) {
        views.add(view);
        view.updateView();
    }

    /**
     * Update all the views by calling the updateView method from StopCovidView.
     * @param original The view that called this function dont need to be called
     */
    public void updateViews(final StopCovidView original) {
        for (StopCovidView view : views) {
            if (view != original) {
                view.updateView();
            }
        }
    }
}
