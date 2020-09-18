package fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;

import java.util.ArrayList;
import java.util.Comparator;
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
        sortedMeets.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (int i = 0; i < LIMIT && i < sortedMeets.size(); i++) {
            User user = sortedMeets.get(i).getKey();
            if (!user.getStatus().equals(UserStatus.INFECTED)) {
                contacts.add(user);
            }
        }

        return contacts;
    }

    @Override
    public String toString() {
        return "Send the 10 most frequent contacts";
    }

}
