package fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SendTenMostFrequentContactsTest {

    @Test
    void getRiskyTest() {
        // Given
        User a = new User("a");
        User b = new User("b");
        User.setRiskStrategy(new SendTenMostFrequentContact());

        // When
        a.declareInfected();
        a.meet(b);

        // Then
        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    void getRiskyAfterTest() {
        // Given
        User a = new User("a");
        User b = new User("b");
        User.setRiskStrategy(new SendTenMostFrequentContact());

        // When
        a.meet(b);
        a.declareInfected();

        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    public void getRiskyContactsTest() {
        // Given
        RiskStrategy riskStrategy = new SendTenMostFrequentContact();
        User mainUser = new User("main");
        mainUser.setStatus(UserStatus.INFECTED);

        ArrayList<User> expectedUsers = new ArrayList<>();

        // When
        for(int i = 0; i < 15; i++) {
            User user = new User("" + i);

            for(int j = 0; j < i; j++) {
                mainUser.meet(user);
            }

            if(i > 4) {
                expectedUsers.add(0, user);
            }
        }

        /*
        ExpectedUsers : [14, 13, 12, 11, 10, 9, 8, 7, 6, 5]
        No more than 10 users and sorted in meet order with mainUser.
         */

        // Then
        assertThat(riskStrategy.getRiskyContacts(mainUser), is(expectedUsers));
    }

    @Test
    public void getRiskyContactsNoRiskTest() {
        // Given
        User a = new User("a");
        RiskStrategy riskStrategy = new SendTenMostFrequentContact();

        // When
        a.declareInfected();

        // Then
        assertThat(riskStrategy.getRiskyContacts(a), is(new ArrayList<>()));
    }

    @Test
    void infectedNotRiskyTest() {
        // Given
        User a = new User("a");
        User b = new User("b");
        User.setRiskStrategy(new SendTenMostFrequentContact());

        // When
        a.declareInfected();
        b.declareInfected();
        a.meet(b);

        // Then
        assertThat(a.getStatus(), is(UserStatus.INFECTED));
    }
}
