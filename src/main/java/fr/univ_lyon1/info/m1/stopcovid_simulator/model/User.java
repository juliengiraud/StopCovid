package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.frequency;

public class User { // TODO implement observable

    private static int lastId = 0;
    private static RiskStrategy riskStrategy = RiskStrategy.values()[0];
    private final int id;
    private final String name;
    private UserStatus status;
    private final List<User> meets;

    /**
     * User constructor. Has a "name", a "status" (NO_RISK / RISKY / INFECTED) and a "meet" list.
     * @param name User's name
     */
    public User(final String name) {
        this.id = lastId++;
        this.name = name;
        this.status = UserStatus.NO_RISK;
        this.meets = new ArrayList<>();
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

    public List<User> getMeets() {
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
        meets.add(otherUser);
        otherUser.getMeets().add(this);
        checkRisky();
        otherUser.checkRisky();
    }

    /**
     * Remove a contact from the meets list.
     * @param contact User to remove
     */
    public void removeContact(final User contact) {
        while (meets.contains(contact)) {
            meets.remove(contact);
        }
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
                for (User u : meets) {
                    if (u.status.equals(UserStatus.INFECTED)) {
                        status = UserStatus.RISKY;
                    }
                }
                break;
            case SEND_FROM_TWO_CONTACTS:
                for (User u : meets) {
                    if (u.status.equals(UserStatus.INFECTED) && frequency(meets, u) >= 2) {
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
        meets.forEach(u -> u.checkRisky());
    }

    @Override
    public String toString() {
        return name;
    }
}
