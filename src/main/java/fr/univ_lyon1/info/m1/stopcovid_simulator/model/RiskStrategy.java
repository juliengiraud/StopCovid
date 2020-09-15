package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public enum RiskStrategy {

    SEND_ALL_CONTACTS("Send all contacts", 1),
    SEND_FROM_TWO_CONTACTS("Send from 2 contacts", 2);

    private final String name;
    private final int limitValue;

    RiskStrategy(final String name, final int limitValue) {
        this.name = name;
        this.limitValue = limitValue;
    }

    public int getLimitValue() {
        return limitValue;
    }

    @Override
    public String toString() {
        return name;
    }

}
