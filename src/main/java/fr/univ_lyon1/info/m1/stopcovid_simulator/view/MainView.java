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

public class MainView extends HBox implements StopCovidView {

    private final List<UserView> usersView = new ArrayList<>(); //
    private final ArrayList<UserView> usersList;
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
    public MainView(final Stage stage, final int width, final int height,
                    final Controller controller) {

        this.stage = stage;
        this.width = width;
        this.height = height;
        this.serverView = new ServerView();
        this.controller = controller;
        this.leftUserComboBox = new ComboBox<>();
        this.rightUserComboBox = new ComboBox<>();
        this.usersList = new ArrayList<>();

        initWindow();
    }

    private void initWindow() {
        System.out.println("a");
        // Name of window
        stage.setTitle(" Simulator");

        final HBox root = this;
        final VBox usersBox = new VBox();

        usersBox.getChildren().add(new Label("Users"));

        for (User u : controller.getUsers()) {
            final UserView uv = new UserView(this, u);
            // C'est marrant parce que les UV ça pique les yeux,
            // un peu comme le code de ce projet au départ
            usersView.add(uv);
            usersBox.getChildren().add(uv.getGui()); // TODO : Put that in user view
            usersList.add(uv);
        }

        this.getChildren().add(usersBox);

        initMeetBox();

        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    // TODO : Put that in ServerView
    private void initMeetBox() {

        final VBox meetBox = new VBox();
        final Label l = new Label("Proximity simulator");

        ObservableList<UserView> usersListObs = FXCollections.observableArrayList(usersList);

        leftUserComboBox.setItems(FXCollections.observableArrayList(usersListObs));
        rightUserComboBox.setItems(FXCollections.observableArrayList(usersListObs));
        final Button meetBtn = new Button("Meet!");
        meetBtn.setOnAction(event -> onMeetBtnClick());

        meetBox.getChildren().addAll(l, new HBox(leftUserComboBox, rightUserComboBox), meetBtn,
                new Separator(), serverView.getGui());

        this.getChildren().addAll(new Separator(), meetBox);
    }

    // TODO : Put that in UserView
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
        updateViews();
    }

    /**
     * Update all views (server and users).
     */
    @Override
    public void updateView() {
        serverView.updateView(usersList);
        for (UserView uv : usersView) {
            uv.updateView();
        }
    }

    /**
     * Update all views :
     * Server, users from this mainView and the other StopCovidViews by the Controller.
     * This function is meant to be used within the view classes.
     */
    public void updateViews() {
        updateView();
        controller.updateViews(this);
    }
}
