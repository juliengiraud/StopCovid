package fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;

import java.util.ArrayList;
import java.util.List;

public class SendAllContacts implements RiskStrategy {

    @Override
    public List<User> getRiskyContacts(final User cu) {
        if (!cu.getStatus().equals(UserStatus.INFECTED)) {
            return new ArrayList<>();
        }
        List<User> contacts = new ArrayList<>();
        cu.getMeets().forEach((u, i) -> {
            if (!u.getStatus().equals(UserStatus.INFECTED)) {
                contacts.add(u);
            }
        });
        return contacts;
    }

    @Override
    public String toString() {
        return "Send all contacts";
    }

}
