package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Comparator;
import java.util.Collections;

public class UserView {
    private final VBox gui = new VBox();
    private final VBox contacts = new VBox();
    private final Label status;
    private final User user;

    UserView(final User user) {
        this.user = user;
        this.status = new Label(user.getStatus().getName());
        final Label l = new Label(user.getName());
        gui.setStyle("-fx-padding: 10; -fx-border-width: 1;"
                + " -fx-border-radius: 5; -fx-border-color: #505050;");

        final Button declareBtn = new Button("Declare Infected");
        declareBtn.setOnAction(this.declare);
        gui.getChildren().addAll(l, new Label("Contacts:"), contacts, declareBtn, status);
    }


    private final EventHandler<ActionEvent> declare = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            user.setStatus(UserStatus.INFECTED);
            updateStatus();
            ArrayList<User> riskyUsers = new ArrayList<>();
            LinkedHashSet<User> distinctMeets = new LinkedHashSet<User>(user.getMeets());
            for (final User u : distinctMeets) {
                if (u.getStatus().equals(UserStatus.RISKY)) {
                    riskyUsers.add(u);

                }
            }
            JfxView view = (JfxView) gui.getParent().getParent();
            view.getServerView().updateView(riskyUsers);
            for (UserView userView : view.getUsersView()) {
                userView.updateStatus();
            }
            updateContacts();
        }
    };

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
    public void updateStatus() {
        this.status.setText(user.getStatus().getName());
    }

    /**
     * Update user's contact list.
     */
    public void updateContacts() {
        // We need to override contacts
        contacts.getChildren().clear();

        // Sort meets by name ASC
        user.getMeets().sort(new Comparator<User>() {
            @Override
            public int compare(final User u1, final User u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });

        // Get distinct ordered meets
        LinkedHashSet<User> distinctMeets = new LinkedHashSet<User>(user.getMeets());

        // For each distinct meet, add line into contacts
        for (final User u : distinctMeets) {
            int userCount = Collections.frequency(user.getMeets(), u);
            contacts.getChildren().add(new Label(String.format(
                    "%s (× %d)",
                    u.getName(), userCount
            )));
        }
    }
}
