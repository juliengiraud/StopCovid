package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class StopCovidServerView {
    private final VBox gui = new VBox();
    /** Get the GUI object corresponding to the server. */
    public Node getGui() {
        return gui;
    }

    /**
    * Update the server view. Display risky users.
    *
    * @param riskyUsers List of risky users.
    */
    public void updateView(final ArrayList<User> riskyUsers) {
        gui.getChildren().clear();
        gui.getChildren().add(new Label("Risky users:"));
        for (User u : riskyUsers) {
            gui.getChildren().add(new Label(u.getName()));
        }
    }

}
