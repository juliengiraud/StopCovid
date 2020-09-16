package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendAllContacts;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.RiskStrategy;

import java.util.Map;
import java.util.TreeMap;

public class User implements Comparable<User> { // TODO implement observable

    private static int lastId = 0;
    private static RiskStrategy riskStrategy = new SendAllContacts();
    private final int id;
    private final String name;
    private UserStatus status;
    private final Map<User, Integer> meets = new TreeMap<>();

    /**
     * User constructor. Has a "name", a "status" (NO_RISK / RISKY / INFECTED) and a "meet" list.
     * @param name User's name
     */
    public User(final String name) {
        this.id = lastId++;
        this.name = name;
        this.status = UserStatus.NO_RISK;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public Map<User, Integer> getMeets() {
        return meets;
    }

    public static void setRiskStrategy(final RiskStrategy riskStrategy) {
        User.riskStrategy = riskStrategy;
    }

    /**
     * Simulate the meeting of two users. Each user will keep the identifier of
     * the other in memory, and will notify the other if infected.
     *
     * @param otherUser The other user being met.
     */
    public void meet(final User otherUser) {
        addMeet(this, otherUser);
        addMeet(otherUser, this);
        checkRisky();
        otherUser.checkRisky();
    }

    private void addMeet(final User a, final User b) {
        // Add the meet to the map of user a
        if (!a.getMeets().containsKey(b)) {
            a.getMeets().put(b, 1);
        } else {
            a.getMeets().put(b, a.getMeets().get(b) + 1);
        }
    }

    /**
     * Remove a contact from the meets list.
     * @param contact User to remove
     */
    public void removeContact(final User contact) {
        meets.remove(contact);
    }

    /**
     * Check if a contact of the current user should be risky, according to the risky strategy.
     * If so, he gets risky.
     */
    public void checkRisky() {
        riskStrategy.getRiskyContacts(this).forEach(u -> u.status = UserStatus.RISKY);
    }

    /**
     * Set user status to infected and notify contacts.
     */
    public void declareInfected() {
        status = UserStatus.INFECTED;
        checkRisky();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final User user) {
        return name.compareTo(user.name);
    }
}
