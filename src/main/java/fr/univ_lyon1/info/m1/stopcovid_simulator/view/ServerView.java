package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.Observer;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.RiskStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ServerView extends VBox implements Observer {

    private final VBox gui = new VBox();
    private final Controller controller;
    private final ComboBox<User> leftUserComboBox = new ComboBox<>();
    private final ComboBox<User> rightUserComboBox = new ComboBox<>();
    private final VBox meetBox = new VBox();
    private final VBox startegyBox = new VBox();
    static final int PADDING = 20;

    /**
     * Create ServerView and initialise meetBox.
     *
     * @param controller
     */
    public ServerView(final Controller controller) {
        this.controller = controller;
        initStrategyBox();
        initMeetBox();
        controller.getUsers().forEach(u -> u.attach(this));
    }

    private void initStrategyBox() {
        ComboBox<RiskStrategy> cb = new ComboBox<>();
        ObservableList<RiskStrategy> list = FXCollections.observableArrayList(
                controller.getStrategies());

        cb.setItems(list);
        cb.getSelectionModel().select(0);
        cb.setOnAction(event -> onStrategyChange(cb.getValue()));
        startegyBox.setPadding(new Insets(PADDING, 0, PADDING, 0));

        startegyBox.getChildren().add(new Label("Select risk strategy:"));
        startegyBox.getChildren().add(cb);

        this.getChildren().add(startegyBox);
    }

    private void onStrategyChange(final RiskStrategy riskStrategy) {
        controller.setRiskStrategy(riskStrategy);
    }

    private void initMeetBox() {
        final Label l = new Label("Proximity simulator");

        ObservableList<User> usersObs = FXCollections.observableArrayList(controller.getUsers());

        leftUserComboBox.setItems(FXCollections.observableArrayList(usersObs));
        rightUserComboBox.setItems(FXCollections.observableArrayList(usersObs));
        final Button meetBtn = new Button("Meet!");
        meetBtn.setOnAction(event -> onMeetBtnClick());

        Label space = new Label(" "); // Sorry

        Separator separator = new Separator();
        separator.setPadding(new Insets(PADDING, 0, PADDING / 2, 0));

        meetBox.getChildren().addAll(l, new HBox(leftUserComboBox, rightUserComboBox), space,
                meetBtn, separator, gui);

        this.getChildren().add(meetBox);
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
    }

    /**
    * Update the server view. Display risky users.
    */
    public void update() {
        gui.getChildren().clear();
        gui.getChildren().add(new Label("Risky users:"));
        for (User u : controller.getUsers()) {
            if (u.getStatus().equals(UserStatus.RISKY)) {
                gui.getChildren().add(new Label(u.getName()));
            }
        }
    }

}
