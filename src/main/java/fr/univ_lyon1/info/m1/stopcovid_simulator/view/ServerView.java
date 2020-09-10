package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserStatus;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class ServerView {
    private final VBox gui = new VBox();
    /** Get the GUI object corresponding to the server. */
    public Node getGui() {
        return gui;
    }

    /**
    * Update the server view. Display risky users.
    *
    * @param users List of users to get risky users.
    */
    public void updateView(final ArrayList<UserView> users) {
        gui.getChildren().clear();
        gui.getChildren().add(new Label("Risky users:"));
        for (UserView uv : users) {
            User user = uv.getUser();
            if (user.getStatus() == UserStatus.RISKY) {
                gui.getChildren().add(new Label(user.getName()));
            }
        }
    }

}
