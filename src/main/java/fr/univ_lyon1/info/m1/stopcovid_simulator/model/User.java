package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.HashMap;
import java.util.Map;

public class User { // TODO implement observable

    private static int lastId = 0;
    private static RiskStrategy riskStrategy = RiskStrategy.values()[0];
    private final int id;
    private final String name;
    private UserStatus status;
    private final Map<User, Integer> meets = new HashMap<>();

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
        checkRisky();
        contact.checkRisky();
    }

    /**
     * Check if a user should be risky, according to the risky strategy.
     * If so, the current user get risky.
     */
    public void checkRisky() {
        if (status == UserStatus.INFECTED) {
            return;
        }
        status = UserStatus.NO_RISK;
        switch (riskStrategy) {
            case SEND_ALL_CONTACTS:
                for (User u : meets.keySet()) {
                    if (u.status.equals(UserStatus.INFECTED)) {
                        status = UserStatus.RISKY;
                    }
                }
                break;
            case SEND_FROM_TWO_CONTACTS:
                for (User u : meets.keySet()) {
                    if (u.status.equals(UserStatus.INFECTED) && meets.get(u) >= 2) {
                        status = UserStatus.RISKY;
                    }
                }
                break;
            case SEND_TEN_MOST_FREQUENT_CONTACTS:
                // TODO
                // List tenMostFrequentContacts = new ArrayList<User>();
                // Map<User>, Integer> frequencyMap = ;
                // Stream.of(meets).collect(Collectors.groupingBy(Function.identity(),
                // Collectors.counting()));
                // LinkedHashSet<User> distinctMeets = new LinkedHashSet<>(meets);
                break;
            default:
                break;
        }
    }

    /**
     * Set user status to infected and notify contacts.
     */
    public void declareInfected() {
        status = UserStatus.INFECTED;
        meets.keySet().forEach(u -> u.checkRisky());
    }

    @Override
    public String toString() {
        return name;
    }
}
