package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static java.util.Collections.frequency;

public class User {

    private final String name;
    private UserStatus status;
    private final List<User> meets;

    /**
     * User constructor. Has a "name", a "status" (NO_RISK / RISKY / INFECTED) and a "meets" list.
     * @param name User's name
     */
    public User(final String name) {
        this.name = name;
        this.status = UserStatus.NO_RISK;
        this.meets = new ArrayList<>();
    }

    /**
     * Getter.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter.
     * @return status
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Setter.
     * @param status User's status
     */
    public void setStatus(final UserStatus status) {
        this.status = status;
        if (status.equals(UserStatus.INFECTED)) {
            LinkedHashSet<User> distinctMeets = new LinkedHashSet<User>(meets);
            for (User u : distinctMeets) {
                u.updateStatus();
            }
        }
    }

    /**
     * Getter.
     * @return meets
     */
    public List<User> getMeets() {
        return meets;
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
        System.out.println(String.format("%s a rencontré %s", name, otherUser.getName()));
    }

    /**
     *
     */
    public void updateStatus() {
        if (status.equals(UserStatus.INFECTED)) {
            return;
        }
        LinkedHashSet<User> distinctMeets = new LinkedHashSet<User>(meets);
        for (User u : distinctMeets) {
            if (!u.getStatus().equals(UserStatus.INFECTED)
                    || frequency(meets, u) < 2) {
                continue;
            }

            setStatus(UserStatus.RISKY);
            System.out.println(String.format("%s passe en status %s", name, status.getName()));
            return;
        }
    }

    @Override
    public String toString() {
        return name + " : " + status;
    }
}
