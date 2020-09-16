package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
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

public class ServerView extends VBox { // TODO implement observer

    private final VBox gui = new VBox();
    private final Controller controller;
    private final ComboBox<User> leftUserComboBox = new ComboBox<>();
    private final ComboBox<User> rightUserComboBox = new ComboBox<>();
    private final VBox meetBox = new VBox();
    private final VBox startegyBox = new VBox();
    private final MainView mainView;
    static final int STRATEGY_BOX_PADDING_Y = 20;

    /**
     * Create ServerView and initialise meetBox.
     *
     * @param controller
     * @param mainView
     */
    public ServerView(final Controller controller, final MainView mainView) {
        this.controller = controller;
        this.mainView = mainView;
        initStrategyBox();
        initMeetBox();
    }

    private void initStrategyBox() {
        ComboBox<RiskStrategy> cb = new ComboBox<>();
        ObservableList<RiskStrategy> list = FXCollections.observableArrayList(
                controller.getStrategies());

        cb.setItems(list);
        cb.getSelectionModel().select(0);
        cb.setOnAction(event -> onStrategyChange(cb.getValue()));
        startegyBox.setPadding(new Insets(STRATEGY_BOX_PADDING_Y, 0, STRATEGY_BOX_PADDING_Y, 0));

        startegyBox.getChildren().add(new Label("Select risk strategy:"));
        startegyBox.getChildren().add(cb);

        this.getChildren().add(startegyBox);
    }

    private void onStrategyChange(final RiskStrategy riskStrategy) {
        controller.setRiskStrategy(riskStrategy);
        mainView.updateViews();
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
        mainView.updateViews();
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
