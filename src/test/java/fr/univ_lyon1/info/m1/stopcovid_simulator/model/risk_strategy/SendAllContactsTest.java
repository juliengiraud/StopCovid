package fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SendAllContactsTest {

    User a, b;
    RiskStrategy riskStrategy;

    @BeforeEach
    public void setUp() {
        // Given
        a = new User("a");
        b = new User("b");
        riskStrategy = new SendAllContacts();
        User.setRiskStrategy(riskStrategy);
    }

    @Test
    void getRiskyTest() {
        // When
        a.declareInfected();
        a.meet(b);

        // Then
        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    void getRiskyAfterTest() {
        // When
        a.meet(b);
        a.declareInfected();

        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    public void getRiskyContactsRiskyTest() {
        // When
        a.declareInfected();
        a.meet(b);

        // Then
        ArrayList<User> assertedList = new ArrayList<>();
        assertedList.add(b);
        assertThat(riskStrategy.getRiskyContacts(a), is(assertedList));
    }

    @Test
    public void getRiskyContactsNoRiskTest() {
        // When
        a.declareInfected();

        // Then
        assertThat(riskStrategy.getRiskyContacts(a), is(new ArrayList<>()));
    }

    @Test
    void infectedNotRiskyTest() {
        // When
        a.declareInfected();
        b.declareInfected();
        a.meet(b);

        // Then
        assertThat(a.getStatus(), is(UserStatus.INFECTED));
    }
}
