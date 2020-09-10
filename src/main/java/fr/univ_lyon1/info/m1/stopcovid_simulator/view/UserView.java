package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;

public class UserView {
    private final VBox gui = new VBox();
    private final VBox contacts = new VBox();
    private final Label status;
    private final User user;

    UserView(final MainView mainView, final User user) {
        this.user = user;
        this.status = new Label(user.getStatus().getName());
        final Label l = new Label(user.getName());
        gui.setStyle("-fx-padding: 10; -fx-border-width: 1;"
                + " -fx-border-radius: 5; -fx-border-color: #505050;");

        final Button declareBtn = new Button("Declare Infected");
        declareBtn.setOnAction(event -> {
            user.setStatus(UserStatus.INFECTED);
            mainView.updateView();
        });
        gui.getChildren().addAll(l, new Label("Contacts:"), contacts, declareBtn, status);
    }


    Node getGui() {
        return gui;
    }

    @Override
    public String toString() {
        return user.getName();
    }

    public User getUser() {
        return user;
    }

    /**
     * Changes the status in the view.
     */
    private void updateStatus() {
        this.status.setText(user.getStatus().getName());
    }

    /**
     * Update user's contact list.
     */
    private void updateContacts() {
        // We need to override contacts
        contacts.getChildren().clear();

        // Sort meets by name ASC
        user.getMeets().sort(Comparator.comparing(User::getName));

        // Get distinct ordered meets
        LinkedHashSet<User> distinctMeets = new LinkedHashSet<>(user.getMeets());

        // For each distinct meet, add line into contacts
        for (final User u : distinctMeets) {
            int userCount = Collections.frequency(user.getMeets(), u);
            contacts.getChildren().add(new Label(String.format(
                    "%s (× %d)",
                    u.getName(), userCount
            )));
        }
    }

    /**
     * Update user view.
     */
    public void updateView() {
        updateStatus();
        updateContacts();
    }
}
