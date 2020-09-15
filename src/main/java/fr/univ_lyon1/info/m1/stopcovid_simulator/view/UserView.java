package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;

public class UserView {

    private final VBox gui = new VBox();
    private final VBox contacts = new VBox();
    private final Label status;
    private final User user;
    private final MainView mainView;

    UserView(final MainView mainView, final User user, final VBox usersBox) {
        this.mainView = mainView;
        this.user = user;
        this.status = new Label(user.getStatus().getName());
        initView();
        usersBox.getChildren().add(gui);
    }

    private void initView() {
        final Label l = new Label(user.getName());
        final Button declareBtn = new Button("Declare Infected");
        declareBtn.setOnAction(event -> {
            user.setStatus(UserStatus.INFECTED);
            mainView.updateViews();
        });
        gui.getChildren().addAll(l, new Label("Contacts:"), contacts, declareBtn, status);
        gui.setStyle("-fx-padding: 10; -fx-border-width: 1;"
                + " -fx-border-radius: 5; -fx-border-color: #505050;");
    }

    @Override
    public String toString() {
        return user.getName();
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
            Label contactLabel = new Label(String.format("%s (× %d)", u.getName(), userCount));

            final HBox box = new HBox();
            final Button xButton = new Button("x");
            xButton.setOnMouseClicked(event -> {
                user.removeContact(u);
                updateContacts();
            });

            box.setStyle("-fx-padding: 2;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            box.setAlignment(Pos.BASELINE_CENTER);

            box.getChildren().add(contactLabel);
            box.getChildren().add(xButton);
            contacts.getChildren().add(box);
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
