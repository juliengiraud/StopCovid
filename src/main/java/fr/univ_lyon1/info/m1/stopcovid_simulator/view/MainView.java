package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends HBox {

    private final List<UserView> usersView = new ArrayList<>();
    private final ServerView serverView;
    private final Controller controller;
    private final Stage stage;
    private static final int PREFERED_WIDTH = 200;

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
        this.serverView = new ServerView(controller);

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

    private ScrollPane initAndGetUsersBox() {
        final ScrollPane panel = new ScrollPane();
        final VBox usersBox = new VBox();

        usersBox.getChildren().add(new Label("Users"));

        for (User u : controller.getUsers()) {
            usersView.add(new UserView(controller, u, usersBox)); // Create usersView access
        }
        panel.setContent(usersBox);
        panel.setPrefWidth(PREFERED_WIDTH);
        return panel;
    }

}
