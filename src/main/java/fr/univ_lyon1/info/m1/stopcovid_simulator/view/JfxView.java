package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JfxView extends HBox {

    private List<UserView> usersView = new ArrayList<>();
    private ServerView serverView;
    private Controller controller;

    /** View for the whole application.
     * @param stage The JavaFX stage where everything will be displayed.
     * @param width width in px
     * @param height height in px
     * @param controller number of users to manage
     */
    public JfxView(final Stage stage, final int width, final int height,
                   final Controller controller) {

        this.serverView = new ServerView();
        this.controller = controller;

        // Name of window
        stage.setTitle(" Simulator");

        final HBox root = this;

        final VBox usersBox = new VBox();
        final ObservableList<UserView> usersList = FXCollections.observableArrayList();

        usersBox.getChildren().add(new Label("Users"));

        for (User u : controller.getUsers()) {
            final UserView uv = new UserView(u);
            // C'est marrant parce que les UV ça pique les yeux,
            // un peu comme le code de ce projet au départ
            usersView.add(uv);
            usersBox.getChildren().add(uv.getGui());
            usersList.add(uv);
        }

        root.getChildren().add(usersBox);

        final VBox meetBox = new VBox();
        final Label l = new Label("Proximity simulator");

        final ComboBox<UserView> userA = new ComboBox<>();
        final ComboBox<UserView> userB = new ComboBox<>();
        userA.setItems(usersList);
        userB.setItems(usersList);
        final Button meetBtn = new Button("Meet!");
        meetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                final UserView a = userA.getValue();
                final UserView b = userB.getValue();
                if (a == null || b == null) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please select two users that will meet");
                    alert.showAndWait();
                    return;
                }
                if (a == b) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please select two different users that will meet");
                    alert.showAndWait();
                    return;
                }
                a.getUser().meet(b.getUser());
                a.getUser().updateStatus();
                b.getUser().updateStatus();
                a.updateContacts();
                b.updateContacts();
            }
        });


        meetBox.getChildren().addAll(l, new HBox(userA, userB), meetBtn,
            new Separator(), serverView.getGui());

        root.getChildren().addAll(new Separator(), meetBox);

        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    ServerView getServerView() {
        return serverView;
    }

    List<UserView> getUsersView() {
        return usersView;
    }
}
