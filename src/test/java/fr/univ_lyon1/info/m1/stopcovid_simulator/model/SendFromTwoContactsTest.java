package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.RiskStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.SendFromTwoContacts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SendFromTwoContactsTest {

    User a, b;
    RiskStrategy riskStrategy;

    @BeforeEach
    public void setUp() {
        // Given
        a = new User("a");
        b = new User("b");

        // When
        riskStrategy = new SendFromTwoContacts();
        a.setStatus(UserStatus.INFECTED);
        a.meet(b);
    }

    @Test
    public void getRiskyContactsRiskyTest() {
        // And
        a.meet(b);

        // Then
        ArrayList<User> assertedList = new ArrayList<>();
        assertedList.add(b);
        assertThat(riskStrategy.getRiskyContacts(a), is(assertedList));
    }

    @Test
    public void getRiskyContactsNoRiskTest() {
        // Then
        assertThat(riskStrategy.getRiskyContacts(a), is(new ArrayList<>()));
    }
}
