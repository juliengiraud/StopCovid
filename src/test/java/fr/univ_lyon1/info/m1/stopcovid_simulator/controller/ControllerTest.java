package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControllerTest {

    private Controller controller;

    @BeforeEach
    void createController() {
        controller = new Controller(2);
    }

    @Test
    void createUsersTest() {
        assertThat(controller.getUsers().size(), is(2));
    }

    @Test
    void addMeetTest() {
        // Given
        User a = controller.getUsers().get(0);
        User b = controller.getUsers().get(1);

        // When
        controller.addMeet(a, b);

        // Then
        assertThat(a.getMeets().get(0), is(b));
    }
}
