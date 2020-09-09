package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.StopCovidUserStatus;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.*;

public class StopCovidUserView {
    private final VBox gui = new VBox();
    private final VBox contacts = new VBox();
    private final Label status;
    private final User user;
    private final EventHandler<ActionEvent> declare = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            user.setStatus(StopCovidUserStatus.INFECTED);
            setStatus(user.getStatus());
            final StopCovidServerView server =
                    ((JfxView) (gui.getParent().getParent())).getServer();
            for (final User u : user.getMeets()) {
                server.declareRisky(u);
            }
        }
    };

    Node getGui() {
        return gui;
    }

    StopCovidUserView(final User user) {
        this.user = user;
        this.status = new Label(user.getStatus().getName());
        final Label l = new Label(user.getName());
        gui.setStyle("-fx-padding: 10; -fx-border-width: 1;"
                + " -fx-border-radius: 5; -fx-border-color: #505050;");

        final Button declareBtn = new Button("Declare Infected");
        declareBtn.setOnAction(this.declare);
        gui.getChildren().addAll(l, new Label("Contacts:"), contacts, declareBtn, status);
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
     * @param status the new status.
     */
    public void setStatus(final StopCovidUserStatus status) {
        this.status.setText(status.getName());
    }

    public void updateContacts() {
        // We need to override contacts
        contacts.getChildren().clear();

        // Sort meets by name ASC
        user.getMeets().sort(new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
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
