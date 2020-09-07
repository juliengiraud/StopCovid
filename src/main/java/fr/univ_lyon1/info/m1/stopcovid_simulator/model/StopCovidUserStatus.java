package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public enum StopCovidUserStatus {

    RISKY("Risky"),
    INFECTED("Infected"),
    NO_RISK("No Risk");

    private String status;

    StopCovidUserStatus(String status) {
        this.status = status;
    }

}
