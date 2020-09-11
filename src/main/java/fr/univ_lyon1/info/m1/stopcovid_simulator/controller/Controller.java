package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.view.StopCovidView;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final List<User> users = new ArrayList<>();
    private final List<StopCovidView> views;

    /**
     * Controller for the whole application.
     * @param nbUsers number of user
     */
    public Controller(final int nbUsers) {
        views = new ArrayList<>();
        for (int i = 0; i < nbUsers; i++) {
            users.add(new User("User " + (i + 1)));
        }
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * Return user with matching id, null if not found.
     * @param id User's id
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
     * @param a One user
     * @param b Another user
     */
    public void addMeet(final User a, final User b) {
        a.meet(b);
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
