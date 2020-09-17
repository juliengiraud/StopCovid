package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.Observer;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class UserView implements Observer {

    private final CheckBox nextMeetCheckBox = new CheckBox("Next Meet ?");
    private final VBox gui = new VBox();
    private final VBox contacts = new VBox();
    private final Label status;
    private final User user;
    private final Controller controller;

    UserView(final Controller controller, final User user, final VBox usersBox) {
        this.controller = controller;
        this.user = user;
        this.status = new Label(user.getStatus().getName());
        initView();
        usersBox.getChildren().add(gui);
        user.attach(this);
    }

    private void initView() {
        final Label l = new Label(user.getName());
        final Button declareBtn = new Button("Declare Infected");
        declareBtn.setOnAction(event -> {
            controller.declareInfected(user);
        });
        gui.getChildren().addAll(l, new Label("Contacts:"), contacts, declareBtn,
                status, nextMeetCheckBox);
        gui.setStyle("-fx-padding: 10; -fx-border-width: 1;"
                + " -fx-border-radius: 5; -fx-border-color: #505050;");
    }

    public User getUser() {
        return user;
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

        // For each contact
        user.getMeets().forEach((u, i) -> {
            Label contactLabel = new Label(String.format("%s: %d ", u.getName(), i));

            final HBox box = new HBox();
            final Button xButton = new Button("x");
            xButton.setOnMouseClicked(event -> {
                controller.removeContact(user, u);
                updateContacts();
            });

            box.setStyle("-fx-padding: 2;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            box.setAlignment(Pos.BASELINE_CENTER);

            box.getChildren().add(contactLabel);
            box.getChildren().add(xButton);
            contacts.getChildren().add(box);
        });
    }

    /**
     * Checks if this user has the next meet checkbox checked.
     * @return True if the checkbox is selected, False otherwise
     */
    public boolean nextMeet() {
        return nextMeetCheckBox.isSelected();
    }

    /**
     * Get all the UserView that has the next meet checkbox checked.
     * @param users The list of all UserView
     * @return The UserViews with the next meet checkbox checked
     */
    public static ArrayList<UserView> getNextMeetUsers(final List<UserView> users) {
        ArrayList<UserView> nextMeetUsers = new ArrayList<>(users);
        for (int i = 0; i < nextMeetUsers.size(); i++) {
            if (!nextMeetUsers.get(i).nextMeet()) {
                nextMeetUsers.remove(i--);
            }
        }
        return nextMeetUsers;
    }

    @Override
    public void update() {
        updateStatus();
        updateContacts();
    }
}
