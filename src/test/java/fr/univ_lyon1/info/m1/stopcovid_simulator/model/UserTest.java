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
