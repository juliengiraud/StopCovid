package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public enum UserStatus {

    RISKY("Risky"),
    INFECTED("Infected"),
    NO_RISK("No Risk");

    private String name;

    UserStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
