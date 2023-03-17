package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {

    // Attributs \\

    private int id;
    private Client client;
    private Vehicle vehicule;
    private LocalDate debut;
    private LocalDate fin;


    // Constructeurs \\

    public Reservation() {

    }

    public Reservation(int id, Client client, Vehicle vehicule, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.debut = debut;
        this.fin = fin;
    }

    // Méthodes \\


    // Getters \\

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Vehicle getVehicule() {
        return vehicule;
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

    public void setClient(Client client) {
        this.client = client;
    }

    public void setVehicule(Vehicle vehicule) {
        this.vehicule = vehicule;
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
                "client_id = " + this.client + '\n' +
                "vehicule_id = " + this.vehicule + '\n' +
                "debut = " + this.debut + '\n' +
                "debut = " + this.fin;
    }

    public int compareBeginDate(Reservation reservation)
    {
        if(this.debut.isBefore(reservation.getDebut())) return -1;
        else if (this.debut.isAfter(reservation.getDebut())) return 1;
        return 0;
    }

}
