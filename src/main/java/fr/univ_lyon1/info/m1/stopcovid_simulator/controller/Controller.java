package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendAllContacts;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendFromTwoContacts;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.RiskStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendTenMostFrequentContact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

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

    private void updateRiskyUsers() {
        // Reset risky users
        users.forEach(u -> {
            if (u.getStatus().equals(UserStatus.RISKY)) {
                u.setStatus(UserStatus.NO_RISK);
            }
        });

        // Update risky users
        users.forEach(u -> {
            u.updateRiskyStatus();
        });
    }

    /**
     * Ask the user service to declare a user as risky.
     *
     * @param u
     */
    public void declareInfected(final User u) {
        u.declareInfected();
    }

    /**
     * User A removes user B from contact list.
     * @param a
     * @param b
     */
    public void removeContact(final User a, final User b) {
        a.removeContact(b);
    }

}
