package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {

    // Attributs \\

    private int id;
    private int client_id;
    private int vehicule_id;
    private LocalDate debut;
    private LocalDate fin;


    // Constructeurs \\


    // Méthodes \\


    // Getters \\

    public int getId() {
        return id;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getVehicule_id() {
        return vehicule_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }


    // Setters \\

    public void setId(int id) {
        this.id = id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setVehicule_id(int vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation n°" + this.id + " : \n" +
                "client_id = " + this.client_id + '\n' +
                "vehicule_id = " + this.vehicule_id + '\n' +
                "debut = " + this.debut + '\n' +
                "debut = " + this.fin;
    }
}
