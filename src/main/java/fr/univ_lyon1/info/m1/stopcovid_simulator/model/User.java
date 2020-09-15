package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static java.util.Collections.frequency;

public class User { // TODO implement observable

    private static int lastId = 0;
    private static RiskStrategy riskStrategy = RiskStrategy.SEND_ALL_CONTACTS;
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

    /**
     * Set the status. If the user become infected, it will check if the users he met get risky.
     *
     * @param status User's status
     */
    public void setStatus(final UserStatus status) {
        this.status = status;
        if (status.equals(UserStatus.INFECTED)) {
            LinkedHashSet<User> distinctMeets = new LinkedHashSet<>(meets);
            for (User u : distinctMeets) {
                u.checkRisky(this);
            }
        }
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
        checkRisky(otherUser);
        otherUser.checkRisky(this);
    }

    /**
     * Remove a contact from the meets list.
     * @param contact User to remove
     */
    public void removeContact(final User contact) {
        while (meets.contains(contact)) {
            meets.remove(contact);
        }
    }

    /**
     * Check if a user is infected and met at least two times the current user.
     * If so, the current user get risky.
     *
     * @param otherUser The other user who potentially get the current user risky
     */
    public void checkRisky(final User otherUser) {
        if (status != UserStatus.NO_RISK || otherUser.getStatus() != UserStatus.INFECTED
                || frequency(meets, otherUser) < riskStrategy.getLimitValue()) {
            return;
        }

        setStatus(UserStatus.RISKY);
        // TODO modifier le comportement de cette fonction, normalement quand il y a une
        //  rencontre il faut demander à chacun des deux utilisateur concernés de regarder s'il
        //  devrait passer en risky mais en aucun cas cette fonction ne devrait prendre le
        //  "otherUser" en entrée
    }

    @Override
    public String toString() {
        return name;
    }
}
