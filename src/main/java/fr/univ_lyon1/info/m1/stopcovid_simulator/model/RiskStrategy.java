package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public enum RiskStrategy {

    SEND_ALL_CONTACTS("Send all contacts"),
    SEND_FROM_TWO_CONTACTS("Send from 2 contacts"),
    SEND_TEN_MOST_FREQUENT_CONTACTS("Send the 10 most frequent contacts");

    private final String name;

    RiskStrategy(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
