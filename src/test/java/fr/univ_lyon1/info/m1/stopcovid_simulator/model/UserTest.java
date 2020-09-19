package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void differentIdTest() {
        // Given
        User a = new User("");

        // When
        User b = new User("");

        assertThat(b.getId(), is(a.getId() + 1));
    }

    @Test
    void infectedTest() {
        // Given
        User a = new User("");

        // When
        a.declareInfected();

        assertThat(a.getStatus(), is(UserStatus.INFECTED));
    }

    @Test
    void getRiskyTest() { // ici
        // Given
        // implicitement ici tu as un `User.setRiskStrategy(new SendAllContacts());`
        User a = new User("");
        a.declareInfected();
        User b = new User("");

        // When
        a.meet(b);
        a.meet(b);

        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    void getRiskyAfterTest() { // TODO normalement les 3 tests en rapport avec le risky devraient
                               //  être fait pour chaque stratégie de test, il faudrait rajouter
                               //  un package risk_strategy comme dans main avec une classe par
                               //  implémentation de stratégie (du coup ça nous ferait 9 tests)
                               //  Si tu es vraiment chaud tu peux aussi rajouter des tests où tu
                               //  changes la stratégie en cours de route genre après avoir supprimé
                               //  des contacts pour voir comment ça réagit, c'est le seul moyen de
                               //  régresser le statut risky donc on peut avoir des résultats
                               //  intéressants
        // Given
        User a = new User("");
        User b = new User("");

        // When
        a.meet(b);
        a.meet(b);
        a.declareInfected();

        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    void infectedNotRiskyTest() { // ici
        // Given
        User a = new User("");
        a.declareInfected();
        User b = new User("");

        // When
        a.meet(b);
        a.meet(b);

        assertThat(a.getStatus(), is(UserStatus.INFECTED));
    }

    @Test
    void contactCountTest() {
        // Given
        User a = new User("");
        User b = new User("");

        // When
        a.meet(b);
        a.meet(b);
        a.meet(b);

        assertThat(a.getMeets().size(), is(1));
    }

    @Test
    void meetReflexiveTest() {
        // Given
        User a = new User("");
        User b = new User("");
        User c = new User("");

        // When
        a.meet(b);
        c.meet(a);

        assertThat(b.getMeets(), is(c.getMeets()));
    }
}
