package fr.univ_lyon1.info.m1.stopcovid_simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.Controller;
import fr.univ_lyon1.info.m1.stopcovid_simulator.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int NB_USERS = 4;

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     *
     * @param args Command-line arguments
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) {
        Controller c = new Controller(NB_USERS);
        new MainView(stage, WIDTH, HEIGHT, c);
    }

}
