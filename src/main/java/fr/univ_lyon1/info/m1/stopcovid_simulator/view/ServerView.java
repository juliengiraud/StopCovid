package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.Observer;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy.RiskStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class ServerView extends VBox implements Observer {

    private final VBox gui = new VBox();
    private final Controller controller;
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

        final Button meetBtn = new Button("Meet!");
        meetBtn.setOnAction(event -> controller.onMeetBtnClick());

        Separator separator = new Separator();
        separator.setPadding(new Insets(PADDING, 0, PADDING / 2, 0));

        meetBox.getChildren().addAll(l, meetBtn, separator, gui);

        this.getChildren().add(meetBox);
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
