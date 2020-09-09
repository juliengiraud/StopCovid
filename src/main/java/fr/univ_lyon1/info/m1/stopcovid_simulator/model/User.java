package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private StopCovidUserStatus status;
    private final List<User> meets;

    public User(String name) {
        this.name = name;
        this.status = StopCovidUserStatus.NO_RISK;
        this.meets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StopCovidUserStatus getStatus() {
        return status;
    }

    public void setStatus(StopCovidUserStatus status) {
        this.status = status;
        if (status.equals(StopCovidUserStatus.INFECTED)) {
            meets.forEach( (User u) -> {
                updateStatus();
            });
        }
    }

    public List<User> getMeets() {
        return meets;
    }

    public void addMeet(User m) {
        meets.add(m);
    }

    /**
     * Simulate the meeting of two users. Each user will keep the identifier of
     * the other in memory, and will notify the other if infected.
     *
     * @param otherUser The other user being met.
     */
    public void meet(User otherUser) {
        meets.add(otherUser);
        otherUser.getMeets().add(this);
        System.out.println(String.format("%s a rencontré %s", name, otherUser.getName()));
    }

    public void updateStatus() {
        if (status.equals(StopCovidUserStatus.INFECTED)) {
            return;
        }
        int count = 0;
        for (User u : meets) {
            if (u.getStatus().equals(StopCovidUserStatus.INFECTED)) {
                count++;
            }
        }
        if (count >= 2) {
            status = StopCovidUserStatus.RISKY;
            System.out.println(String.format("%s passe en status %s", name, status.getName()));
        }
    }

}
