package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.RiskStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendTenMostFrequentContact;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SendTenMostFrequentContactsTest {

    @Test
    public void getRiskyContactsTest() {
        // Given
        User mainUser = new User("main");
        mainUser.setStatus(UserStatus.INFECTED);

        ArrayList<User> users = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            User user = new User("" + i);

            for(int j = 0; j < i; j++) {
                mainUser.meet(user);
            }

            if(i > 4) {
                users.add(0, user);
            }
        }

        // When
        RiskStrategy riskStrategy = new SendTenMostFrequentContact();

        // Then
        assertThat(riskStrategy.getRiskyContacts(mainUser),
                is(users));
    }
}
