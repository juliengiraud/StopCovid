package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendAllContacts;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendFromTwoContacts;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.RiskStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendTenMostFrequentContact;
import fr.univ_lyon1.info.m1.stopcovid_simulator.view.StopCovidView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    private final List<StopCovidView> views = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    /**
     * Get all users from UserService.
     *
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Create new meet between two users from UserService.
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
     */
    public void updateViews() {
        for (StopCovidView view : views) {
            view.updateView();
        }
    }

    public List<RiskStrategy> getStrategies() {
        return Arrays.asList(new SendAllContacts(), new SendFromTwoContacts(),
                new SendTenMostFrequentContact());
    }

    /**
     * Set the strategy we use to check if a user is risky.
     *
     * @param strategy
     */
    public void setRiskStrategy(final RiskStrategy strategy) {
        User.setRiskStrategy(strategy);
        updateRiskyUsers();
    }

    /**
     * Check for each user if he's risky.
     */
    public void updateRiskyUsers() {
        // Reset risky users
        users.forEach(u -> {
            if (u.getStatus().equals(UserStatus.RISKY)) {
                u.setStatus(UserStatus.NO_RISK);
            }
        });

        // Update risky users
        users.forEach(u -> u.checkRisky());
    }

    /**
     * Ask the user service to declare a user as risky.
     *
     * @param u
     */
    public void declareInfected(final User u) {
        u.declareInfected();
        updateViews();
    }

    /**
     * User A removes user B from contact list.
     * @param a
     * @param b
     */
    public void removeContact(final User a, final User b) {
        a.removeContact(b);
        updateViews();
    }

}
