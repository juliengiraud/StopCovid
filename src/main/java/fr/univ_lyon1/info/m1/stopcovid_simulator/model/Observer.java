package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public interface Observer {

    /**
     * Called after observable change to inform observer.
     */
    void update();

}
