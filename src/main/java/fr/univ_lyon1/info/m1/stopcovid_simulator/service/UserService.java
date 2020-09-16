package fr.univ_lyon1.info.m1.stopcovid_simulator.service;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final List<User> users = new ArrayList<>();

    /**
     * Constructor of the user service class.
     *
     * @param nbUsers
     */
    public UserService(final int nbUsers) {
        users.add(new User("a"));
        users.add(new User("z"));
        users.add(new User("e"));
        users.add(new User("r"));
        users.add(new User("t"));
        users.sort(User::compareTo);
    }

    /**
     * Get all users.
     *
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Create new meet between two users.
     * @param a One user
     * @param b Another user
     */
    public void addMeet(final User a, final User b) {
        a.meet(b);
    }

    /**
     * Change user status to infected.
     *
     * @param u
     */
    public void declareInfected(final User u) {
        u.declareInfected();
    }

    /**
     * Check for each user if he's risky.
     */
    public void updateRiskyUsers() {
        // users.forEach( u -> u.checkRisky());
        // TODO remettre cette ligne quand la fonction checkRisky sera propre et ajouter les tests
        //  pour vérifier que les risky users sont bien mis à jour
    }

}
