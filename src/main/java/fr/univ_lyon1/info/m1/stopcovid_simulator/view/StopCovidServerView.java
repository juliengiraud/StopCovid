package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.StopCovidUserStatus;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StopCovidServerView {
    private final VBox gui = new VBox();
    /** Get the GUI object corresponding to the server. */
    public Node getGui() {
        return gui;
    }

    /**
    * Declare this user as risky, i.e. having been in contact with an infected person.
    *
    * @param user  Name of the user to declare risky.
    */
    public void declareRisky(final User user) {
        for (Node c : gui.getChildren()) {
            if (((Label) c).getText().equals(user.getName())) {
                return;
            }
        }
        gui.getChildren().add(new Label("Risky users:"));
        gui.getChildren().add(new Label(user.getName()));
        for (StopCovidUserView u : ((JfxView) gui.getParent().getParent()).getUsers()) {
            if (u.getUser().getName().equals(user.getName())) {
                u.setStatus(StopCovidUserStatus.RISKY);
            }
        }
    }

}
