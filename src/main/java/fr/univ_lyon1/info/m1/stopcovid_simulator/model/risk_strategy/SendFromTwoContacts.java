package fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;

import java.util.ArrayList;
import java.util.List;

public class SendFromTwoContacts implements RiskStrategy {

    @Override
    public List<User> getRiskyContacts(final User cu) {
        if (!cu.getStatus().equals(UserStatus.INFECTED)) {
            return new ArrayList<>();
        }
        List<User> contacts = new ArrayList<>();
        cu.getMeets().forEach((u, i) -> {
            if (i >= 2) {
                contacts.add(u);
            }
        });
        return contacts;
        // TODO vérifier que cet algo marche
    }

    @Override
    public String toString() {
        return "Send from 2 contacts";
    }

}
