package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private List<UserView> usersView = new ArrayList<>(); //
    private final ObservableList<UserView> usersList;
    private final ComboBox<UserView> leftUserComboBox;
    private final ComboBox<UserView> rightUserComboBox;
    private final ServerView serverView;
    private final Controller controller;
    private final Stage stage;
    private final int width;
    private final int height;

    /** View for the whole application.
     * @param stage The JavaFX stage where everything will be displayed.
     * @param width width in px
     * @param height height in px
     * @param controller number of users to manage
     */
    public JfxView(final Stage stage, final int width, final int height,
                   final Controller controller) {

        this.stage = stage;
        this.width = width;
        this.height = height;
        this.serverView = new ServerView();
        this.controller = controller;
        this.leftUserComboBox = new ComboBox<>();
        this.rightUserComboBox = new ComboBox<>();
        this.usersList = FXCollections.observableArrayList();

        initWindow();
    }

    private void initWindow() {
        // Name of window
        stage.setTitle(" Simulator");

        final HBox root = this;
        final VBox usersBox = new VBox();

        usersBox.getChildren().add(new Label("Users"));

        for (User u : controller.getUsers()) {
            final UserView uv = new UserView(u);
            // C'est marrant parce que les UV ça pique les yeux,
            // un peu comme le code de ce projet au départ
            usersView.add(uv);
            usersBox.getChildren().add(uv.getGui());
            usersList.add(uv);
        }

        this.getChildren().add(usersBox);

        initMeetBox();

        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    private void initMeetBox() {

        final VBox meetBox = new VBox();
        final Label l = new Label("Proximity simulator");

        leftUserComboBox.setItems(usersList);
        rightUserComboBox.setItems(usersList);
        final Button meetBtn = new Button("Meet!");
        meetBtn.setOnAction(event -> {
            onMeetBtnClick();
        });

        meetBox.getChildren().addAll(l, new HBox(leftUserComboBox, rightUserComboBox), meetBtn,
                new Separator(), serverView.getGui());

        this.getChildren().addAll(new Separator(), meetBox);
    }

    private void onMeetBtnClick() {
        final UserView a = leftUserComboBox.getValue();
        final UserView b = rightUserComboBox.getValue();
        if (a == null || b == null || a.getUser() == b.getUser()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select two different users that will meet");
            alert.showAndWait();
            return;
        }
        controller.addMeet(a.getUser(), b.getUser());
        // TODO créer et appeler la fonction d'update UI générale
    }

    public ServerView getServerView() {
        return serverView;
    }

    public List<UserView> getUsersView() {
        return usersView;
    }
}
