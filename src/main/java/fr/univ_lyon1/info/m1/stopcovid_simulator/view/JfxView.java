package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
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
    private List<StopCovidUserView> usersView = new ArrayList<>();
    private StopCovidServerView serverView;
    /** View for the whole application.
     * @param stage The JavaFX stage where everything will be displayed.
     * @param width width in px
     * @param height height in px
     * @param nbUsers number of users to manage
     */
    public JfxView(final Stage stage, final int width,
                   final int height, final int nbUsers) {
        serverView = new StopCovidServerView();

        // Name of window
        stage.setTitle("StopCovid Simulator");

        final HBox root = this;

        final VBox usersBox = new VBox();
        final ObservableList<StopCovidUserView> usersViewList = FXCollections.observableArrayList();
        final List<User> users = new ArrayList<>();

        usersBox.getChildren().add(new Label("Users"));

        for (int i = 0; i < nbUsers; i++) {
            users.add(new User("User " + i));
            final StopCovidUserView u = new StopCovidUserView(users.get(i));
            usersView.add(u);
            usersBox.getChildren().add(u.getGui());
            usersViewList.add(u);
        }

        root.getChildren().add(usersBox);

        final VBox meetBox = new VBox();
        final Label l = new Label("Proximity simulator");

        final ComboBox<StopCovidUserView> userA = new ComboBox<>();
        final ComboBox<StopCovidUserView> userB = new ComboBox<>();
        userA.setItems(usersViewList);
        userB.setItems(usersViewList);
        final Button meetBtn = new Button("Meet!");
        meetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                final StopCovidUserView a = userA.getValue();
                final StopCovidUserView b = userB.getValue();
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
                a.getUser().updateStatus(); // TODO débuger ici
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

    StopCovidServerView getServer() {
        return serverView;
    }

    List<StopCovidUserView> getUsers() {
        return usersView;
    }
}
