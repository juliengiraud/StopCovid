package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ServerView {

    private final VBox gui = new VBox();
    private final Controller controller;
    private final ComboBox<User> leftUserComboBox = new ComboBox<>();
    private final ComboBox<User> rightUserComboBox = new ComboBox<>();
    private final VBox meetBox = new VBox();
    private final MainView mainView;

    /**
     * Create ServerView and initialise meetBox.
     *
     * @param controller
     * @param mainView
     */
    public ServerView(final Controller controller, final MainView mainView) {
        this.controller = controller;
        this.mainView = mainView;
        initMeetBox();
    }

    private void initMeetBox() {
        final Label l = new Label("Proximity simulator");

        ObservableList<User> usersObs = FXCollections.observableArrayList(controller.getUsers());

        leftUserComboBox.setItems(FXCollections.observableArrayList(usersObs));
        rightUserComboBox.setItems(FXCollections.observableArrayList(usersObs));
        final Button meetBtn = new Button("Meet!");
        meetBtn.setOnAction(event -> onMeetBtnClick());

        meetBox.getChildren().addAll(l, new HBox(leftUserComboBox, rightUserComboBox), meetBtn,
                new Separator(), gui);
    }

    private void onMeetBtnClick() {
        final User a = leftUserComboBox.getValue();
        final User b = rightUserComboBox.getValue();
        if (a == null || b == null || a == b) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select two different users that will meet");
            alert.showAndWait();
            return;
        }
        controller.addMeet(a, b);
        mainView.updateViews();
    }

    public VBox getMeetBox() {
        return meetBox;
    }

    /**
    * Update the server view. Display risky users.
    */
    public void updateView() {
        gui.getChildren().clear();
        gui.getChildren().add(new Label("Risky users:"));
        for (User u : controller.getUsers()) {
            if (u.getStatus() == UserStatus.RISKY) {
                gui.getChildren().add(new Label(u.getName()));
            }
        }
    }

}
