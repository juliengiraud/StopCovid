package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends HBox implements StopCovidView {

    private final List<UserView> usersView = new ArrayList<>();
    private final ServerView serverView;
    private final Controller controller;
    private final Stage stage;

    /** View for the whole application.
     * @param stage The JavaFX stage where everything will be displayed.
     * @param width width in px
     * @param height height in px
     * @param controller number of users to manage
     */
    public MainView(final Stage stage, final int width, final int height,
                    final Controller controller) {
        this.stage = stage;
        this.controller = controller;
        this.serverView = new ServerView(controller, this);

        initWindow(width, height);
    }

    private void initWindow(final int width, final int height) {
        // Name of window
        stage.setTitle("Simulator");

        this.getChildren().addAll(initAndGetUsersBox(), // Add UsersView to window
                new Separator(),
                serverView); // Add ServerView to window

        stage.setScene(new Scene(this, width, height));
        stage.show();
    }

    private VBox initAndGetUsersBox() {
        final VBox usersBox = new VBox();

        usersBox.getChildren().add(new Label("Users"));

        for (User u : controller.getUsers()) {
            usersView.add(new UserView(controller, u, usersBox)); // Create usersView access
        }
        return usersBox;
    }

    /**
     * Update all views (server and users).
     */
    @Override
    public void updateView() { // TODO virer ce truc
        serverView.updateView();
        for (UserView uv : usersView) {
            // It's funny because UV light burns eyes,
            // exactly like the code of this project at the beginning
            uv.updateView();
        }
    }

    /**
     * Update all views :
     * Server, users from this mainView and the other StopCovidViews by the Controller.
     * This function is meant to be used within the view classes.
     */
    public void updateViews() { // TODO virer ce truc
        updateView();
        controller.updateViews();
    }
}
