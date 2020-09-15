package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class UserModelTest {

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
        a.setStatus(UserStatus.INFECTED);

        assertThat(a.getStatus(), is(UserStatus.INFECTED));
    }

    @Test
    void getRiskyTest() {
        // Given
        User a = new User("");
        a.setStatus(UserStatus.INFECTED);
        User b = new User("");

        // When
        a.meet(b);
        a.meet(b);

        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    void getRiskyAfterTest() {
        // Given
        User a = new User("");
        User b = new User("");

        // When
        a.meet(b);
        a.meet(b);
        a.setStatus(UserStatus.INFECTED);

        assertThat(b.getStatus(), is(UserStatus.RISKY));
    }

    @Test
    void infectedNotRiskyTest() {
        // Given
        User a = new User("");
        a.setStatus(UserStatus.INFECTED);
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

        assertThat(a.getMeets().size(), is(3));
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
