package fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SendTenMostFrequentContact implements RiskStrategy {

    private static final int LIMIT = 10;

    @Override
    public List<User> getRiskyContacts(final User cu) {
        if (!cu.getStatus().equals(UserStatus.INFECTED)) {
            return new ArrayList<>();
        }
        List<User> contacts = new ArrayList<>();
        ArrayList<Map.Entry<User, Integer>> sortedMeets = new ArrayList<>(cu.getMeets().entrySet());
        sortedMeets.sort(Map.Entry.comparingByValue());
        // TODO vérifier que la liste de contacts est bien triée par fréquence de rencontre (@test)

        int i = 0;
        sortedMeets.forEach(s -> { // s for set
            if (!s.getKey().equals(UserStatus.INFECTED) && i < LIMIT) {
                contacts.add(s.getKey());
            }
        });
        // TODO vérifier qu'on ne dépasse pas une taille de 10 (@test)

        return contacts;
    }

    @Override
    public String toString() {
        return "Send the 10 most frequent contacts";
    }

}
