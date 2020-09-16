package fr.univ_lyon1.info.m1.stopcovid_simulator.model.risk_strategy;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.User;

import java.util.List;

public interface RiskStrategy {

    /**
     * Analyse a user contact list and return the risky users according to specific strategy.
     * @param cu the current user
     * @return userList of risky users
     */
    List<User> getRiskyContacts(User cu);

}
