package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class UserModelTest {
    @Test
    void dummyTestInt() {
        // Given
        Integer i = 0;

        // When
        i++;

        // Then
        assertThat(i, is(1));
    }

    @Test
    void dummyTestIntFailure() {
        // Given
        Integer i = 0;

        // When
        i = i + 42;

        // The
        assertThat(i, is(42));
    }

    @Test
    void differentIdCheck() {
        // Given
        User a = new User("");

        // When
        User b = new User("");

        assertThat(b.getId(), is(a.getId() + 1));
    }

    // TODO test :
    //  Vérifier qu'un utilisateur déclaré INFECTED passe bien à INFECTED
    //  Vérifier qu'un utilisateur NO_RISKY passe en RISKY après 2 contacts déjà INFECTED
    //  Vérifier qu'un utilisateur NO_RISKY passe en RISKY après 2 contacts qui passent en INFECTED
    //  Vérifier qu'un utilisateur INFECTED ne peut pas passer en RISKY
    //  Vérifier que le nombre de contacts INFECTED se met bien à jour
    //  Vérifier que a.meet(b) est équivalent à b.meet(a)

}
